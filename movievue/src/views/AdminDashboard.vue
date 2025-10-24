<template>
  <div class="workspace">
    <header class="workspace-header admin">
      <div>
        <h1>管理员控制台</h1>
        <p>电影库维护 → 内容审核（评论/举报）→ 广告审核 → 违规处置 → 报表</p>
      </div>
      <div class="workspace-actions">
        <button type="button" class="btn" @click="loadAll">刷新面板</button>
        <button type="button" class="btn btn-secondary" @click="backToLogin">返回登录</button>
      </div>
    </header>

    <section class="workspace-section">
      <h2>全局概览</h2>
      <div v-if="dashboard" class="workspace-dashboard">
        <div class="workspace-dashboard-card">
          <h3>影片总数</h3>
          <p>{{ dashboard.totalMovies }}</p>
        </div>
        <div class="workspace-dashboard-card">
          <h3>待审核影评</h3>
          <p>{{ dashboard.pendingReviews }}</p>
        </div>
        <div class="workspace-dashboard-card">
          <h3>待处理举报</h3>
          <p>{{ dashboard.pendingReports }}</p>
        </div>
        <div class="workspace-dashboard-card">
          <h3>待审核素材</h3>
          <p>{{ dashboard.pendingCreatives }}</p>
        </div>
        <div class="workspace-dashboard-card">
          <h3>活跃计划</h3>
          <p>{{ dashboard.activeCampaigns }}</p>
        </div>
        <div class="workspace-dashboard-card">
          <h3>注册用户</h3>
          <p>{{ dashboard.totalUsers }}</p>
        </div>
      </div>
    </section>

    <section class="workspace-section">
      <h2>电影库维护</h2>
      <form class="workspace-form" @submit.prevent="createOrUpdateMovie">
        <label>
          影片标题
          <input v-model="movieForm.title" type="text" required />
        </label>
        <label>
          上映年份
          <input v-model.number="movieForm.releaseYear" type="number" />
        </label>
        <label>
          地区
          <input v-model="movieForm.region" type="text" />
        </label>
        <label>
          语言
          <input v-model="movieForm.language" type="text" />
        </label>
        <label>
          类型（逗号分隔）
          <input v-model="movieForm.genres" type="text" placeholder="Drama,Action" />
        </label>
        <label>
          导演
          <input v-model="movieForm.directors" type="text" placeholder="Zhang Yimou" />
        </label>
        <label>
          演员
          <input v-model="movieForm.casts" type="text" placeholder="Actor1,Actor2" />
        </label>
        <label>
          上映日期
          <input v-model="movieForm.releaseDate" type="date" />
        </label>
        <label>
          海报地址
          <input v-model="movieForm.posterUrl" type="url" />
        </label>
        <label class="grow">
          简介
          <textarea v-model="movieForm.synopsis" rows="3"></textarea>
        </label>
        <button type="submit" class="btn">{{ movieForm.id ? '保存修改' : '新增影片' }}</button>
        <button v-if="movieForm.id" type="button" class="btn btn-secondary" @click="resetMovieForm">重置</button>
      </form>
      <div class="workspace-subsection">
        <h3>影片列表（最近）</h3>
        <table class="workspace-table" v-if="movies.length">
          <thead>
            <tr>
              <th>标题</th>
              <th>年份</th>
              <th>地区</th>
              <th>评分</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="movie in movies" :key="movie.id">
              <td>{{ movie.title }}</td>
              <td>{{ movie.releaseYear || '—' }}</td>
              <td>{{ movie.region || '—' }}</td>
              <td>{{ movie.averageRating ?? '0.0' }}</td>
              <td class="workspace-table-actions">
                <button class="btn btn-secondary" @click="editMovie(movie)">编辑</button>
                <button class="btn btn-danger" @click="removeMovie(movie.id)">删除</button>
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else class="workspace-empty">暂无影片数据，请先新增。</p>
      </div>
    </section>

    <section class="workspace-section">
      <h2>内容审核 · 影评</h2>
      <ul class="workspace-review-list">
        <li v-for="item in pendingReviews" :key="item.id" class="workspace-review-item">
          <div>
            <h4>影评 #{{ item.id }} · 评分 {{ item.rating }}</h4>
            <p class="workspace-meta">用户：{{ item.nickname }} · 影片 ID：{{ item.movieId }}</p>
            <p>{{ item.content }}</p>
          </div>
          <div class="workspace-review-actions">
            <button class="btn" @click="moderateReview(item.id, 'approve')">通过</button>
            <button class="btn btn-danger" @click="moderateReview(item.id, 'reject')">驳回</button>
          </div>
        </li>
      </ul>
      <p v-if="!pendingReviews.length" class="workspace-empty">暂无待审核影评。</p>
    </section>

    <section class="workspace-section">
      <h2>举报处理</h2>
      <table class="workspace-table" v-if="pendingReports.length">
        <thead>
          <tr>
            <th>ID</th>
            <th>类型</th>
            <th>对象</th>
            <th>理由</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="report in pendingReports" :key="report.id">
            <td>{{ report.id }}</td>
            <td>{{ report.targetType }}</td>
            <td>{{ report.targetId }}</td>
            <td>{{ report.reason }}</td>
            <td class="workspace-table-actions">
              <button class="btn" @click="resolveReport(report.id, 'RESOLVED')">标记处理</button>
              <button class="btn btn-secondary" @click="resolveReport(report.id, 'REJECTED')">忽略</button>
            </td>
          </tr>
        </tbody>
      </table>
      <p v-else class="workspace-empty">暂无待处理举报。</p>
    </section>

    <section class="workspace-section">
      <h2>广告审核与账户管理</h2>
      <div class="workspace-subsection">
        <h3>待审核素材</h3>
        <table class="workspace-table" v-if="pendingCreatives.length">
          <thead>
            <tr>
              <th>ID</th>
              <th>计划</th>
              <th>类型</th>
              <th>状态</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="creative in pendingCreatives" :key="creative.id">
              <td>{{ creative.id }}</td>
              <td>{{ creative.campaignId }}</td>
              <td>{{ creative.type }}</td>
              <td>{{ creative.status }}</td>
              <td class="workspace-table-actions">
                <button class="btn" @click="reviewCreative(creative.id, 'APPROVED')">通过</button>
                <button class="btn btn-danger" @click="reviewCreative(creative.id, 'REJECTED')">驳回</button>
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else class="workspace-empty">暂无待审核素材。</p>
      </div>
      <div class="workspace-subsection">
        <h3>广告账户列表</h3>
        <table class="workspace-table" v-if="adAccounts.length">
          <thead>
            <tr>
              <th>ID</th>
              <th>公司</th>
              <th>状态</th>
              <th>余额（元）</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="account in adAccounts" :key="account.id">
              <td>{{ account.id }}</td>
              <td>{{ account.companyName }}</td>
              <td>{{ account.status }}</td>
              <td>{{ (account.balanceCents / 100).toFixed(2) }}</td>
              <td class="workspace-table-actions">
                <button class="btn" @click="updateAccountStatus(account.id, 'APPROVED')">批准</button>
                <button class="btn btn-secondary" @click="updateAccountStatus(account.id, 'SUSPENDED')">暂停</button>
              </td>
            </tr>
          </tbody>
        </table>
        <p v-else class="workspace-empty">暂无广告账户数据。</p>
      </div>
    </section>

    <p v-if="message" class="workspace-hint">{{ message }}</p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { useRouter } from 'vue-router';
import {
  adminDashboard,
  adminCreateMovie,
  adminUpdateMovie,
  adminDeleteMovie,
  fetchMovies,
  adminPendingReviews,
  adminModerateReview,
  adminPendingReports,
  adminResolveReport,
  adminPendingCreatives,
  adminReviewCreative,
  adminAdAccounts,
  adminUpdateAccountStatus
} from '../services/api';

const router = useRouter();
const dashboard = ref(null);
const movies = ref([]);
const pendingReviews = ref([]);
const pendingReports = ref([]);
const pendingCreatives = ref([]);
const adAccounts = ref([]);
const message = ref('');
const movieForm = reactive({
  id: null,
  title: '',
  releaseYear: '',
  region: '',
  language: '',
  genres: '',
  directors: '',
  casts: '',
  synopsis: '',
  posterUrl: '',
  releaseDate: ''
});

function backToLogin() {
  router.push('/login');
}

async function loadDashboard() {
  dashboard.value = await adminDashboard();
}

async function loadMovies() {
  const data = await fetchMovies({ page: 0, size: 10 });
  movies.value = data.items || [];
}

async function loadPendingReviews() {
  pendingReviews.value = await adminPendingReviews();
}

async function loadPendingReports() {
  pendingReports.value = await adminPendingReports();
}

async function loadPendingCreatives() {
  pendingCreatives.value = await adminPendingCreatives();
}

async function loadAdAccounts() {
  adAccounts.value = await adminAdAccounts();
}

async function loadAll() {
  try {
    await Promise.all([
      loadDashboard(),
      loadMovies(),
      loadPendingReviews(),
      loadPendingReports(),
      loadPendingCreatives(),
      loadAdAccounts()
    ]);
  } catch (error) {
    message.value = error.message || '加载数据失败';
  }
}

function parseList(value) {
  if (!value) return [];
  return value.split(',').map((item) => item.trim()).filter(Boolean);
}

async function createOrUpdateMovie() {
  try {
    const payload = {
      title: movieForm.title,
      releaseYear: movieForm.releaseYear || null,
      region: movieForm.region || null,
      language: movieForm.language || null,
      genres: parseList(movieForm.genres),
      directors: parseList(movieForm.directors),
      casts: parseList(movieForm.casts),
      synopsis: movieForm.synopsis || null,
      posterUrl: movieForm.posterUrl || null,
      releaseDate: movieForm.releaseDate || null
    };
    if (movieForm.id) {
      await adminUpdateMovie(movieForm.id, payload);
      message.value = '影片已更新。';
    } else {
      await adminCreateMovie(payload);
      message.value = '影片已创建。';
    }
    resetMovieForm();
    await loadMovies();
  } catch (error) {
    message.value = error.message || '保存影片失败';
  }
}

function editMovie(movie) {
  movieForm.id = movie.id;
  movieForm.title = movie.title;
  movieForm.releaseYear = movie.releaseYear || '';
  movieForm.region = movie.region || '';
  movieForm.language = movie.language || '';
  movieForm.genres = (movie.genres || []).join(',');
  movieForm.directors = (movie.directors || []).join(',');
  movieForm.casts = (movie.casts || []).join(',');
  movieForm.synopsis = movie.synopsis || '';
  movieForm.posterUrl = movie.posterUrl || '';
  movieForm.releaseDate = movie.releaseDate || '';
}

function resetMovieForm() {
  movieForm.id = null;
  movieForm.title = '';
  movieForm.releaseYear = '';
  movieForm.region = '';
  movieForm.language = '';
  movieForm.genres = '';
  movieForm.directors = '';
  movieForm.casts = '';
  movieForm.synopsis = '';
  movieForm.posterUrl = '';
  movieForm.releaseDate = '';
}

async function removeMovie(movieId) {
  if (!window.confirm('确认删除该影片？')) {
    return;
  }
  try {
    await adminDeleteMovie(movieId);
    message.value = '影片已删除。';
    await loadMovies();
  } catch (error) {
    message.value = error.message || '删除失败';
  }
}

async function moderateReview(reviewId, action) {
  try {
    await adminModerateReview(reviewId, action, { adminId: 1 });
    message.value = '已更新影评状态。';
    await loadPendingReviews();
  } catch (error) {
    message.value = error.message || '操作失败';
  }
}

async function resolveReport(reportId, status) {
  try {
    await adminResolveReport(reportId, { adminId: 1, status });
    message.value = '举报已处理。';
    await loadPendingReports();
  } catch (error) {
    message.value = error.message || '处理失败';
  }
}

async function reviewCreative(creativeId, status) {
  try {
    await adminReviewCreative(creativeId, { adminId: 1, status });
    message.value = '素材审核完成。';
    await loadPendingCreatives();
  } catch (error) {
    message.value = error.message || '审核失败';
  }
}

async function updateAccountStatus(accountId, status) {
  try {
    await adminUpdateAccountStatus(accountId, { adminId: 1, status });
    message.value = '账户状态已更新。';
    await loadAdAccounts();
  } catch (error) {
    message.value = error.message || '更新失败';
  }
}

loadAll();
</script>
