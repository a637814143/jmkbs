<template>
  <div class="workspace">
    <header class="workspace-header">
      <div class="workspace-user">
        <img v-if="profile?.avatarUrl" :src="profile.avatarUrl" alt="avatar" class="workspace-avatar" />
        <div>
          <h1>{{ welcomeMessage }}</h1>
          <p>当前角色：{{ profile?.role || currentUser?.role }}</p>
          <p v-if="profile?.status !== 'ACTIVE'" class="workspace-warning">账号状态：{{ profile?.status }}</p>
        </div>
      </div>
      <div class="workspace-actions">
        <button type="button" class="btn" @click="handleExport">导出个人数据</button>
        <button type="button" class="btn" @click="loadUserReviews">刷新我的影评</button>
        <button type="button" class="btn btn-danger" @click="handleDelete">注销账号</button>
        <button type="button" class="btn btn-secondary" @click="handleLogout">退出登录</button>
      </div>
    </header>

    <section class="workspace-section">
      <h2>浏览 / 搜索影片</h2>
      <form class="workspace-form" @submit.prevent="searchMovies">
        <label>
          关键词
          <input v-model="searchForm.keyword" type="text" placeholder="影片名 / 关键词" />
        </label>
        <label>
          地区
          <input v-model="searchForm.region" type="text" placeholder="中国大陆" />
        </label>
        <label>
          语言
          <input v-model="searchForm.language" type="text" placeholder="中文" />
        </label>
        <button type="submit" class="btn" :disabled="movieLoading">{{ movieLoading ? '搜索中...' : '搜索' }}</button>
      </form>
      <div v-if="movies.length" class="workspace-grid">
        <article
          v-for="movie in movies"
          :key="movie.id"
          class="workspace-card"
          :class="{ active: movie.id === selectedMovie?.id }"
          @click="selectMovie(movie.id)"
        >
          <img v-if="movie.posterUrl" :src="movie.posterUrl" alt="poster" class="workspace-card-cover" />
          <div class="workspace-card-body">
            <h3>{{ movie.title }}</h3>
            <p class="workspace-meta">{{ movie.releaseYear || '年份未知' }} · {{ movie.region || '地区未知' }} · {{ movie.language || '语言未知' }}</p>
            <p class="workspace-meta">类型：{{ movie.genres?.join(' / ') || '未分类' }}</p>
            <p class="workspace-rating">平均评分：{{ movie.averageRating?.toFixed?.(2) ?? movie.averageRating ?? '0.0' }} （{{ movie.ratingsCount }} 人）</p>
          </div>
        </article>
      </div>
      <p v-else class="workspace-empty">暂无影片结果，尝试调整搜索条件。</p>
    </section>

    <section v-if="selectedMovie" class="workspace-section">
      <div class="workspace-detail">
        <div class="workspace-detail-info">
          <h2>{{ selectedMovie.title }}</h2>
          <p>{{ selectedMovie.synopsis || '该影片暂无简介' }}</p>
          <p class="workspace-meta">导演：{{ selectedMovie.directors?.join(' / ') || '未知' }}</p>
          <p class="workspace-meta">主演：{{ selectedMovie.casts?.join(' / ') || '未知' }}</p>
          <p class="workspace-meta">上映日期：{{ selectedMovie.releaseDate || '未知' }}</p>
        </div>
        <aside class="workspace-detail-side">
          <h3>发表影评</h3>
          <form class="workspace-form vertical" @submit.prevent="submitReview">
            <label>
              评分（1-10）
              <input v-model.number="reviewForm.rating" type="number" min="1" max="10" required />
            </label>
            <label>
              标题
              <input v-model="reviewForm.title" type="text" maxlength="200" placeholder="一句话概括" />
            </label>
            <label>
              正文
              <textarea v-model="reviewForm.content" rows="4" maxlength="2000" required></textarea>
            </label>
            <label class="workspace-checkbox">
              <input v-model="reviewForm.spoiler" type="checkbox" />
              <span>含剧透</span>
            </label>
            <button type="submit" class="btn" :disabled="reviewSubmitting">{{ reviewSubmitting ? '提交中...' : '提交影评' }}</button>
          </form>
          <p v-if="reviewMessage" class="workspace-hint">{{ reviewMessage }}</p>
        </aside>
      </div>

      <div class="workspace-subsection">
        <h3>社区影评（审核通过）</h3>
        <ul class="workspace-review-list">
          <li v-for="review in movieReviews" :key="review.id" class="workspace-review-item">
            <div>
              <h4>{{ review.title || '无标题影评' }} · {{ review.rating }} 分</h4>
              <p class="workspace-meta">{{ review.nickname }} · {{ formatDate(review.createdAt) }}</p>
              <p>{{ review.content }}</p>
              <p v-if="review.spoiler" class="workspace-warning">提示：包含剧透内容</p>
            </div>
            <div class="workspace-review-actions">
              <button class="btn btn-secondary" @click="toggleLike(review)">👍 {{ review.likeCount }}</button>
              <button class="btn btn-secondary" @click="reportReview(review)">举报</button>
            </div>
          </li>
        </ul>
        <p v-if="!movieReviews.length" class="workspace-empty">等待第一条影评被审核通过。</p>
      </div>
    </section>

    <section class="workspace-section">
      <h2>个人中心 · 我的影评</h2>
      <ul class="workspace-review-list">
        <li v-for="review in userReviews" :key="review.id" class="workspace-review-item">
          <div>
            <h4>《{{ reviewMovieTitle(review.movieId) }}》 · {{ review.rating }} 分</h4>
            <p class="workspace-meta">状态：{{ review.status }} · {{ formatDate(review.createdAt) }}</p>
            <p>{{ review.content }}</p>
          </div>
        </li>
      </ul>
      <p v-if="!userReviews.length" class="workspace-empty">暂未发表影评，快去分享观影体验吧！</p>
    </section>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import {
  fetchMovies,
  fetchMovieDetail,
  fetchMovieReviews,
  createReview,
  likeReview,
  unlikeReview,
  reportTarget,
  fetchUserProfile,
  fetchUserReviews,
  exportUserData,
  deleteUser
} from '../services/api';

const router = useRouter();
const stored = localStorage.getItem('jmk_movie_user');
const currentUser = stored ? JSON.parse(stored) : null;

const profile = ref(null);
const welcomeMessage = computed(() => {
  if (profile.value?.nickname) {
    return `欢迎回来，${profile.value.nickname}`;
  }
  if (currentUser?.nickname) {
    return `欢迎回来，${currentUser.nickname}`;
  }
  return '欢迎来到极目观影中心';
});

const searchForm = reactive({
  keyword: '',
  region: '',
  language: '',
  page: 0,
  size: 8
});

const movies = ref([]);
const movieLoading = ref(false);
const selectedMovie = ref(null);
const movieReviews = ref([]);
const reviewForm = reactive({ rating: 8, title: '', content: '', spoiler: false });
const reviewSubmitting = ref(false);
const reviewMessage = ref('');

const userReviews = ref([]);
const movieCache = reactive(new Map());

function ensureLoggedIn() {
  if (!currentUser) {
    router.push('/login');
    throw new Error('未登录');
  }
}

async function loadProfile() {
  ensureLoggedIn();
  try {
    profile.value = await fetchUserProfile(currentUser.id);
  } catch (error) {
    console.error(error);
  }
}

async function searchMovies() {
  movieLoading.value = true;
  try {
    const data = await fetchMovies({
      keyword: searchForm.keyword,
      region: searchForm.region,
      language: searchForm.language,
      page: searchForm.page,
      size: searchForm.size
    });
    movies.value = data.items || [];
  } catch (error) {
    window.alert(error.message || '搜索影片失败');
  } finally {
    movieLoading.value = false;
  }
}

async function selectMovie(movieId) {
  try {
    const detail = await fetchMovieDetail(movieId);
    selectedMovie.value = detail;
    movieCache.set(detail.id, detail.title);
    await loadMovieReviews(movieId);
  } catch (error) {
    window.alert(error.message || '加载影片详情失败');
  }
}

async function loadMovieReviews(movieId) {
  try {
    movieReviews.value = await fetchMovieReviews(movieId, 'APPROVED');
  } catch (error) {
    console.error(error);
  }
}

async function submitReview() {
  ensureLoggedIn();
  if (!selectedMovie.value) {
    return;
  }
  reviewSubmitting.value = true;
  reviewMessage.value = '';
  try {
    await createReview(selectedMovie.value.id, {
      userId: currentUser.id,
      rating: reviewForm.rating,
      title: reviewForm.title,
      content: reviewForm.content,
      spoiler: reviewForm.spoiler
    });
    reviewMessage.value = '影评提交成功，待管理员审核。';
    reviewForm.title = '';
    reviewForm.content = '';
    reviewForm.spoiler = false;
    await loadUserReviews();
  } catch (error) {
    reviewMessage.value = error.message || '提交失败';
  } finally {
    reviewSubmitting.value = false;
  }
}

async function toggleLike(review) {
  ensureLoggedIn();
  try {
    await likeReview(review.id, currentUser.id);
    await loadMovieReviews(selectedMovie.value.id);
  } catch (error) {
    if (error.message?.includes('已经点赞')) {
      await unlikeReview(review.id, currentUser.id);
      await loadMovieReviews(selectedMovie.value.id);
    } else {
      window.alert(error.message || '操作失败');
    }
  }
}

async function reportReview(review) {
  ensureLoggedIn();
  const reason = window.prompt('请输入举报理由', '涉嫌违规内容');
  if (!reason) {
    return;
  }
  try {
    await reportTarget({
      reporterId: currentUser.id,
      targetType: 'REVIEW',
      targetId: review.id,
      reason
    });
    window.alert('已提交举报，我们会尽快处理。');
  } catch (error) {
    window.alert(error.message || '举报失败');
  }
}

async function loadUserReviews() {
  if (!currentUser) {
    return;
  }
  try {
    userReviews.value = await fetchUserReviews(currentUser.id);
    userReviews.value.forEach((item) => {
      if (!movieCache.has(item.movieId) && item.movieId) {
        movieCache.set(item.movieId, `电影 #${item.movieId}`);
      }
    });
  } catch (error) {
    console.error(error);
  }
}

function reviewMovieTitle(movieId) {
  return movieCache.get(movieId) || `电影 #${movieId}`;
}

async function handleExport() {
  if (!currentUser) {
    return;
  }
  try {
    const data = await exportUserData(currentUser.id);
    const blob = new Blob([JSON.stringify(data, null, 2)], { type: 'application/json' });
    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `jmk_movie_user_${currentUser.id}.json`;
    a.click();
    URL.revokeObjectURL(url);
  } catch (error) {
    window.alert(error.message || '导出失败');
  }
}

async function handleDelete() {
  if (!currentUser) {
    return;
  }
  if (!window.confirm('确认注销账号？操作不可撤销。')) {
    return;
  }
  try {
    await deleteUser(currentUser.id);
    window.alert('账号已标记为注销，感谢使用。');
    handleLogout();
  } catch (error) {
    window.alert(error.message || '注销失败');
  }
}

function handleLogout() {
  localStorage.removeItem('jmk_movie_user');
  router.push('/login');
}

function formatDate(value) {
  if (!value) return '';
  return new Date(value).toLocaleString();
}

onMounted(async () => {
  try {
    await Promise.all([loadProfile(), searchMovies(), loadUserReviews()]);
  } catch (error) {
    console.error(error);
  }
});
</script>
