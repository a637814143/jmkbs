// 模拟电影数据（实际项目中需通过AJAX请求后端接口获取）
const mockMovies = [
  {
    id: 1,
    title: '疯狂的麦克斯：狂暴之路',
    poster: 'https://picsum.photos/id/237/400/600',
    genre: 'action',
    genreName: '动作',
    year: 2015,
    rating: 8.7,
    desc: '未来世界，水资源短缺引发了连绵的战争。人们相互厮杀，争夺有限的资源，地球变成了血腥十足的杀戮战场。'
  },
  {
    id: 2,
    title: '喜剧之王',
    poster: 'https://picsum.photos/id/238/400/600',
    genre: 'comedy',
    genreName: '喜剧',
    year: 1999,
    rating: 8.8,
    desc: '尹天仇一直醉心戏剧，想成为一名演员，平时除了做跑龙套以外，还会在街坊福利会里开设演员训练班。'
  },
  {
    id: 3,
    title: '肖申克的救赎',
    poster: 'https://picsum.photos/id/239/400/600',
    genre: 'drama',
    genreName: '剧情',
    year: 1994,
    rating: 9.7,
    desc: '银行家安迪被误判为枪杀妻子及其情人的罪名入狱后，不动声色、步步为营地谋划自我拯救并最终成功越狱。'
  },
  {
    id: 4,
    title: '星际穿越',
    poster: 'https://picsum.photos/id/240/400/600',
    genre: 'sci-fi',
    genreName: '科幻',
    year: 2014,
    rating: 9.4,
    desc: '近未来的地球黄沙遍野，小麦、秋葵等基础农作物相继因枯萎病灭绝，人类不再像从前那样仰望星空，放纵想象力和灵感的迸发。'
  },
  {
    id: 5,
    title: '闪灵',
    poster: 'https://picsum.photos/id/241/400/600',
    genre: 'horror',
    genreName: '恐怖',
    year: 1980,
    rating: 8.3,
    desc: '杰克是一个作家，为了寻找灵感，他带着妻子温迪和儿子丹尼搬到了偏远的山间酒店担任冬季 caretaker。'
  },
  {
    id: 6,
    title: '泰坦尼克号',
    poster: 'https://picsum.photos/id/242/400/600',
    genre: 'romance',
    genreName: '爱情',
    year: 1997,
    rating: 9.5,
    desc: '1912年4月10日，号称 "世界工业史上的奇迹" 的豪华客轮泰坦尼克号开始了自己的处女航，从英国的南安普顿出发驶往美国纽约。'
  },
  {
    id: 7,
    title: '千与千寻',
    poster: 'https://picsum.photos/id/243/400/600',
    genre: 'animation',
    genreName: '动画',
    year: 2001,
    rating: 9.4,
    desc: '千寻和爸爸妈妈一同驱车前往新家，在郊外的小路上不慎进入了神秘的隧道——他们去到了另外一个诡异世界。'
  },
  {
    id: 8,
    title: '复仇者联盟4：终局之战',
    poster: 'https://picsum.photos/id/244/400/600',
    genre: 'action',
    genreName: '动作',
    year: 2019,
    rating: 8.5,
    desc: '一声响指，宇宙间半数生命灰飞烟灭。几近绝望的复仇者们在剩余盟友的帮助下再一次集结，逆转灭霸的所作所为。'
  },
  {
    id: 9,
    title: '夏洛特烦恼',
    poster: 'https://picsum.photos/id/245/400/600',
    genre: 'comedy',
    genreName: '喜剧',
    year: 2015,
    rating: 7.8,
    desc: '在学生时代的初恋秋雅的婚礼上，毕业后吃软饭靠老婆养的夏洛假充大款，出尽其丑，中间还被老婆马冬梅戳穿暴捶。'
  },
  {
    id: 10,
    title: '这个杀手不太冷',
    poster: 'https://picsum.photos/id/246/400/600',
    genre: 'drama',
    genreName: '剧情',
    year: 1994,
    rating: 9.4,
    desc: '里昂是一名职业杀手，一天，邻居家小姑娘玛蒂尔达敲开了他的房门，要求在他那里暂避杀身之祸。'
  },
  {
    id: 11,
    title: '银翼杀手2049',
    poster: 'https://picsum.photos/id/247/400/600',
    genre: 'sci-fi',
    genreName: '科幻',
    year: 2017,
    rating: 8.3,
    desc: '故事发生在2049年，复制人K是一名银翼杀手，他的工作是猎杀旧型号的复制人。在一次执行任务的过程中，K发现了一个惊人的秘密。'
  },
  {
    id: 12,
    title: '寄生虫',
    poster: 'https://picsum.photos/id/248/400/600',
    genre: 'drama',
    genreName: '剧情',
    year: 2019,
    rating: 8.8,
    desc: '基宇出生在一个贫穷的家庭之中，和妹妹基婷以及父母在狭窄的半地下室里过着相依为命的日子。'
  }
];

// 分页参数
let currentPage = 1;
const pageSize = 8; // 每页显示8部电影
let filteredMovies = [...mockMovies]; // 筛选后的电影数据

// DOM元素
const movieGrid = document.getElementById('movieGrid');
const genreFilter = document.getElementById('genreFilter');
const yearFilter = document.getElementById('yearFilter');
const sortFilter = document.getElementById('sortFilter');
const loadMoreBtn = document.getElementById('loadMoreBtn');

// 初始化页面
document.addEventListener('DOMContentLoaded', () => {
  renderMovies(getPageMovies()); // 渲染第一页电影
  bindFilterEvents(); // 绑定筛选事件
  bindLoadMoreEvent(); // 绑定加载更多事件
});

/**
 * 获取当前页要显示的电影数据
 */
function getPageMovies() {
  const startIndex = (currentPage - 1) * pageSize;
  const endIndex = startIndex + pageSize;
  return filteredMovies.slice(startIndex, endIndex);
}

/**
 * 渲染电影列表
 * @param {Array} movies - 要渲染的电影数据
 */
function renderMovies(movies) {
  movies.forEach(movie => {
    const movieCard = document.createElement('div');
    movieCard.className = 'movie-card';
    movieCard.dataset.id = movie.id;

    // 拼接电影卡片HTML
    movieCard.innerHTML = `
      <img src="${movie.poster}" alt="${movie.title}" class="movie-poster">
      <div class="movie-rating">${movie.rating}</div>
      <div class="movie-genre-tag">${movie.genreName}</div>
      <div class="movie-info">
        <h3 class="movie-title">${movie.title}</h3>
        <div class="movie-meta">
          <span>${movie.year}</span>
          <span>${movie.genreName}</span>
        </div>
        <p class="movie-desc">${movie.desc}</p>
        <div class="movie-actions">
          <button class="movie-btn"><i class="fas fa-play"></i> 播放</button>
          <button class="movie-btn"><i class="fas fa-heart"></i> 收藏</button>
        </div>
      </div>
    `;

    movieGrid.appendChild(movieCard);
  });

  // 如果没有更多电影，隐藏加载更多按钮
  if (currentPage * pageSize >= filteredMovies.length) {
    loadMoreBtn.disabled = true;
    loadMoreBtn.textContent = '没有更多电影了';
    loadMoreBtn.style.backgroundColor = '#475569';
    loadMoreBtn.style.cursor = 'not-allowed';
  }
}

/**
 * 绑定筛选事件（类型、年份、排序）
 */
function bindFilterEvents() {
  // 类型筛选
  bindFilterGroupEvent(genreFilter, 'genre');
  // 年份筛选
  bindFilterGroupEvent(yearFilter, 'year');
  // 排序筛选
  bindFilterGroupEvent(sortFilter, 'sort');
}

/**
 * 绑定单个筛选组的事件（通用方法）
 * @param {HTMLElement} filterElement - 筛选组容器
 * @param {String} filterType - 筛选类型（genre/year/sort）
 */
function bindFilterGroupEvent(filterElement, filterType) {
  const filterBtns = filterElement.querySelectorAll('.tag-btn');
  
  filterBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      // 更新按钮激活状态
      filterBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');

      // 执行筛选逻辑
      filterMovies();

      // 重置分页，重新渲染第一页
      currentPage = 1;
      movieGrid.innerHTML = '';
      renderMovies(getPageMovies());

      // 重置加载更多按钮状态
      loadMoreBtn.disabled = false;
      loadMoreBtn.textContent = '加载更多电影';
      loadMoreBtn.style.backgroundColor = '#165dff';
      loadMoreBtn.style.cursor = 'pointer';
    });
  });
}

/**
 * 根据所有筛选条件过滤电影
 */
function filterMovies() {
  // 获取当前选中的筛选条件
  const selectedGenre = genreFilter.querySelector('.tag-btn.active').dataset.genre;
  const selectedYear = yearFilter.querySelector('.tag-btn.active').dataset.year;
  const selectedSort = sortFilter.querySelector('.tag-btn.active').dataset.sort;

  // 1. 类型筛选
  let result = [...mockMovies];
  if (selectedGenre !== 'all') {
    result = result.filter(movie => movie.genre === selectedGenre);
  }

  // 2. 年份筛选
  if (selectedYear !== 'all') {
    switch (selectedYear) {
      case '2025':
        result = result.filter(movie => movie.year === 2025);
        break;
      case '2024':
        result = result.filter(movie => movie.year === 2024);
        break;
      case '2023':
        result = result.filter(movie => movie.year === 2023);
        break;
      case '2020-2022':
        result = result.filter(movie => movie.year >= 2020 && movie.year <= 2022);
        break;
      case 'before2020':
        result = result.filter(movie => movie.year < 2020);
        break;
    }
  }

  // 3. 排序筛选
  switch (selectedSort) {
    case 'popular':
      // 热门优先（模拟：评分*1000 + 年份，数值越高越热门）
      result.sort((a, b) => (b.rating * 1000 + b.year) - (a.rating * 1000 + a.year));
      break;
    case 'newest':
      // 最新上映（按年份降序）
      result.sort((a, b) => b.year - a.year);
      break;
    case 'rating':
      // 评分从高到低
      result.sort((a, b) => b.rating - a.rating);
      break;
  }

  // 更新筛选后的电影数据
  filteredMovies = result;
}

/**
 * 绑定加载更多事件
 */
function bindLoadMoreEvent() {
  loadMoreBtn.addEventListener('click', () => {
    currentPage++;
    const nextPageMovies = getPageMovies();
    renderMovies(nextPageMovies);
  });
}