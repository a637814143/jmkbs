// 模拟热门评论数据（实际项目中需通过AJAX请求后端接口获取）
const mockComments = [
  {
    id: 1,
    userId: 101,
    userName: '电影发烧友',
    userAvatar: 'https://picsum.photos/id/64/100/100',
    movieType: 'drama',
    movieName: '肖申克的救赎',
    content: '《肖申克的救赎》绝对是影史最伟大的电影之一！安迪的隐忍和坚持，在绝望中寻找希望的精神，每次看都能被深深打动。尤其是最后雨中张开双臂的镜头，堪称电影史上的经典瞬间，自由的气息扑面而来。',
    createTime: '2025-11-22 18:30:00',
    likeCount: 128,
    replyCount: 36,
    isLiked: false,
    replies: [
      {
        id: 101,
        userId: 201,
        userName: '影剧达人',
        userAvatar: 'https://picsum.photos/id/65/100/100',
        content: '完全同意！摩根·弗里曼的旁白也是点睛之笔，温柔又有力量，把整个故事的氛围烘托得恰到好处。',
        createTime: '2025-11-22 19:15:00'
      },
      {
        id: 102,
        userId: 202,
        userName: '胶片记忆',
        userAvatar: 'https://picsum.photos/id/66/100/100',
        content: '安迪用二十年时间挖隧道，这种坚持真的太震撼了。生活中我们也需要这种不放弃的精神，即使面对困境也要心怀希望。',
        createTime: '2025-11-22 20:05:00'
      }
    ]
  },
  {
    id: 2,
    userId: 102,
    userName: '喜剧爱好者',
    userAvatar: 'https://picsum.photos/id/67/100/100',
    movieType: 'comedy',
    movieName: '夏洛特烦恼',
    content: '《夏洛特烦恼》真的承包了我所有的笑点！沈腾的演技太绝了，每个表情、每句台词都自带笑点，尤其是"马冬梅"那段对话，至今还是经典梗。看似搞笑的剧情，其实也藏着对爱情和生活的思考，珍惜眼前人太重要了。',
    createTime: '2025-11-21 15:40:00',
    likeCount: 96,
    replyCount: 24,
    isLiked: false,
    replies: [
      {
        id: 201,
        userId: 203,
        userName: '搞笑担当',
        userAvatar: 'https://picsum.photos/id/68/100/100',
        content: '同意！我看了不下五遍，每次都笑得肚子疼，尤其是尹正出场的《一剪梅》，画面感太强了！',
        createTime: '2025-11-21 16:20:00'
      }
    ]
  },
  {
    id: 3,
    userId: 103,
    userName: '科幻迷',
    userAvatar: 'https://picsum.photos/id/69/100/100',
    movieType: 'sci-fi',
    movieName: '星际穿越',
    content: '《星际穿越》的视觉效果和科学设定真的太震撼了！黑洞的特效制作堪称完美，虫洞、时间膨胀等科学概念的呈现既专业又易懂。最打动我的是父女之间的感情线，"爱是唯一能超越时间和空间的力量"，这句话让我泪目了。诺兰真的太会拍了，既有硬核科幻，又有温暖的人文关怀。',
    createTime: '2025-11-20 20:15:00',
    likeCount: 156,
    replyCount: 42,
    isLiked: false,
    replies: [
      {
        id: 301,
        userId: 204,
        userName: '天文爱好者',
        userAvatar: 'https://picsum.photos/id/70/100/100',
        content: '黑洞的特效是基于爱因斯坦的广义相对论计算出来的，非常科学！诺兰为了追求真实，邀请了天体物理学家基普·索恩担任科学顾问。',
        createTime: '2025-11-20 21:00:00'
      },
      {
        id: 302,
        userId: 205,
        userName: '诺兰粉丝',
        userAvatar: 'https://picsum.photos/id/71/100/100',
        content: '诺兰的电影总是能把商业性和艺术性完美结合，《星际穿越》不仅好看，还能引发对宇宙和人生的思考。',
        createTime: '2025-11-20 21:30:00'
      }
    ]
  },
  {
    id: 4,
    userId: 104,
    userName: '动画迷',
    userAvatar: 'https://picsum.photos/id/72/100/100',
    movieType: 'animation',
    movieName: '千与千寻',
    content: '宫崎骏的《千与千寻》是我心中的动画神作！画面太美了，每一帧都可以当壁纸，配乐也超级治愈。千寻从胆小懦弱到勇敢坚强的成长历程，教会了我们很多道理：不要忘记自己的名字，要保持善良，勇敢面对困难。无论多大年纪看，都能有新的感悟。',
    createTime: '2025-11-19 14:20:00',
    likeCount: 189,
    replyCount: 53,
    isLiked: false,
    replies: [
      {
        id: 401,
        userId: 206,
        userName: '二次元少女',
        userAvatar: 'https://picsum.photos/id/73/100/100',
        content: '白龙和千寻的感情太好哭了！"我会一直等你回来"，这句话我记了好多年。宫崎骏的动画总是充满了温暖和治愈。',
        createTime: '2025-11-19 15:00:00'
      },
      {
        id: 402,
        userId: 207,
        userName: '音乐爱好者',
        userAvatar: 'https://picsum.photos/id/74/100/100',
        content: '久石让的配乐真的太绝了！《Always With Me》一响起，就想起千寻和白龙的约定，满满的回忆杀。',
        createTime: '2025-11-19 15:30:00'
      }
    ]
  },
  {
    id: 5,
    userId: 105,
    userName: '动作片迷',
    userAvatar: 'https://picsum.photos/id/75/100/100',
    movieType: 'action',
    movieName: '疯狂的麦克斯：狂暴之路',
    content: '《疯狂的麦克斯：狂暴之路》的动作戏太燃了！全程无尿点，追逐场面刺激到不行，视觉冲击力极强。女主角弗瑞奥萨太飒了，独立、勇敢、有力量，打破了传统动作片的性别刻板印象。电影不仅动作场面精彩，还传递了反抗压迫、追求自由的主题，堪称动作片的标杆。',
    createTime: '2025-11-18 19:40:00',
    likeCount: 112,
    replyCount: 28,
    isLiked: false,
    replies: [
      {
        id: 501,
        userId: 208,
        userName: '动作片爱好者',
        userAvatar: 'https://picsum.photos/id/76/100/100',
        content: '实景拍摄的动作戏就是不一样，比特效堆砌的好看多了！汤姆·哈迪和查理兹·塞隆的演技也超级棒。',
        createTime: '2025-11-18 20:15:00'
      }
    ]
  },
  {
    id: 6,
    userId: 106,
    userName: '剧情片爱好者',
    userAvatar: 'https://picsum.photos/id/77/100/100',
    movieType: 'drama',
    movieName: '寄生虫',
    content: '《寄生虫》真的太惊艳了！奉俊昊的叙事能力太强了，把贫富差距的主题刻画得淋漓尽致。电影前半段搞笑，后半段惊悚，剧情反转不断，让人看得直呼过瘾。最后结局的设计也很有深意，既现实又残酷，引发了对社会阶层问题的思考。不愧是奥斯卡最佳影片！',
    createTime: '2025-11-17 16:30:00',
    likeCount: 145,
    replyCount: 39,
    isLiked: false,
    replies: [
      {
        id: 601,
        userId: 209,
        userName: '影评人',
        userAvatar: 'https://picsum.photos/id/78/100/100',
        content: '电影的隐喻太多了，半地下室的设定、暴雨的场景，都暗示了贫富差距的不可逾越。奉俊昊用商业片的外壳包裹了深刻的社会议题，太厉害了。',
        createTime: '2025-11-17 17:10:00'
      },
      {
        id: 602,
        userId: 210,
        userName: '电影爱好者',
        userAvatar: 'https://picsum.photos/id/79/100/100',
        content: '宋康昊的演技太绝了！最后那段眼神戏，把角色的绝望和无奈表现得淋漓尽致，看得我鸡皮疙瘩都起来了。',
        createTime: '2025-11-17 17:40:00'
      }
    ]
  },
  {
    id: 7,
    userId: 107,
    userName: '科幻爱好者',
    userAvatar: 'https://picsum.photos/id/80/100/100',
    movieType: 'sci-fi',
    movieName: '银翼杀手2049',
    content: '《银翼杀手2049》的画面太美了！每一帧都像艺术品，视觉风格独特，氛围感拉满。电影的节奏虽然慢，但叙事很扎实，探讨了"什么是人性"的深刻主题。复制人K的自我寻找之旅，让人看得很有代入感。这部电影不仅是科幻片，更是一部哲学片。',
    createTime: '2025-11-16 21:20:00',
    likeCount: 87,
    replyCount: 22,
    isLiked: false,
    replies: [
      {
        id: 701,
        userId: 211,
        userName: '科幻迷',
        userAvatar: 'https://picsum.photos/id/81/100/100',
        content: '丹尼斯·维伦纽瓦的导演功力太强了，把赛博朋克的美学发挥到了极致。汉斯·季默的配乐也为电影增色不少。',
        createTime: '2025-11-16 22:00:00'
      }
    ]
  },
  {
    id: 8,
    userId: 108,
    userName: '喜剧迷',
    userAvatar: 'https://picsum.photos/id/82/100/100',
    movieType: 'comedy',
    movieName: '喜剧之王',
    content: '《喜剧之王》是星爷最经典的电影之一！"我养你啊"这句话，至今还是表白金句。尹天仇对梦想的坚持，对爱情的真诚，让人又笑又哭。星爷的电影总是用喜剧的外壳包裹着悲剧的内核，看似搞笑，实则充满了对生活的热爱和对梦想的追求。每次看都有新的感悟，这就是经典的力量。',
    createTime: '2025-11-15 14:10:00',
    likeCount: 163,
    replyCount: 45,
    isLiked: false,
    replies: [
      {
        id: 801,
        userId: 212,
        userName: '星爷粉丝',
        userAvatar: 'https://picsum.photos/id/83/100/100',
        content: '星爷的电影总能让人在笑声中感受到温暖和力量。尹天仇虽然只是个跑龙套的，但他对演技的认真态度，值得我们每个人学习。',
        createTime: '2025-11-15 14:40:00'
      },
      {
        id: 802,
        userId: 213,
        userName: '电影爱好者',
        userAvatar: 'https://picsum.photos/id/84/100/100',
        content: '张柏芝在电影里太美了，尤其是最后回头一笑的镜头，堪称经典。她和星爷的对手戏也很有感染力。',
        createTime: '2025-11-15 15:10:00'
      }
    ]
  },
  {
    id: 9,
    userId: 109,
    userName: '动作迷',
    userAvatar: 'https://picsum.photos/id/85/100/100',
    movieType: 'action',
    movieName: '复仇者联盟4：终局之战',
    content: '《复仇者联盟4：终局之战》作为漫威宇宙的阶段性总结，真的太圆满了！集结了所有超级英雄，场面宏大，特效震撼。钢铁侠的牺牲让我泪目，"我爱你三千遍"这句话至今还是意难平。电影不仅有精彩的动作场面，还有对亲情、友情、爱情的刻画，给了粉丝一个完美的交代。',
    createTime: '2025-11-14 18:50:00',
    likeCount: 132,
    replyCount: 34,
    isLiked: false,
    replies: [
      {
        id: 901,
        userId: 214,
        userName: '漫威粉丝',
        userAvatar: 'https://picsum.photos/id/86/100/100',
        content: '钢铁侠的牺牲真的太好哭了！小罗伯特·唐尼的演绎太完美了，从《钢铁侠1》到《复联4》，陪伴了我们11年，感谢钢铁侠的守护。',
        createTime: '2025-11-14 19:30:00'
      }
    ]
  },
  {
    id: 10,
    userId: 110,
    userName: '动画爱好者',
    userAvatar: 'https://picsum.photos/id/87/100/100',
    movieType: 'animation',
    movieName: '寻梦环游记',
    content: '《寻梦环游记》太治愈了！皮克斯的动画总是能温暖人心，画面色彩鲜艳，充满了墨西哥文化特色。电影探讨了生与死、亲情与梦想的主题，"死亡不是终点，遗忘才是"这句话让我深受触动。音乐也超级好听，《Remember Me》一响起就忍不住想哭。适合全家一起看的好电影！',
    createTime: '2025-11-13 16:20:00',
    likeCount: 178,
    replyCount: 48,
    isLiked: false,
    replies: [
      {
        id: 1001,
        userId: 215,
        userName: '亲子观影',
        userAvatar: 'https://picsum.photos/id/88/100/100',
        content: '带孩子去看的，孩子看得又哭又笑。电影教会了孩子珍惜亲情，理解死亡的意义，非常有教育意义。',
        createTime: '2025-11-13 17:00:00'
      },
      {
        id: 1002,
        userId: 216,
        userName: '音乐迷',
        userAvatar: 'https://picsum.photos/id/89/100/100',
        content: '《Remember Me》这首歌太好听了，中文版也超级治愈。皮克斯的动画不仅画面精美，音乐也总是那么动人。',
        createTime: '2025-11-13 17:30:00'
      }
    ]
  }
];

// 分页参数
const pageSize = 3; // 每页显示3条评论
let currentPage = 1;
let filteredComments = [...mockComments]; // 筛选后的评论数据

// DOM元素
const commentsList = document.getElementById('commentsList');
const movieFilter = document.getElementById('movieFilter');
const sortFilter = document.getElementById('sortFilter');
const pagination = document.getElementById('pagination');
const pageNumbers = document.getElementById('pageNumbers');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');

// 初始化页面
document.addEventListener('DOMContentLoaded', () => {
  renderComments(getPageComments()); // 渲染当前页评论
  renderPagination(); // 渲染分页控件
  bindFilterSortEvents(); // 绑定筛选排序事件
  bindPaginationEvents(); // 绑定分页事件
  bindCommentActionEvents(); // 绑定评论操作事件（点赞、回复）
});

/**
 * 获取当前页要显示的评论数据
 */
function getPageComments() {
  const startIndex = (currentPage - 1) * pageSize;
  const endIndex = startIndex + pageSize;
  return filteredComments.slice(startIndex, endIndex);
}

/**
 * 渲染评论列表
 * @param {Array} comments - 要渲染的评论数据
 */
function renderComments(comments) {
  commentsList.innerHTML = '';

  comments.forEach(comment => {
    const commentItem = document.createElement('div');
    commentItem.className = 'comment-item';
    commentItem.dataset.id = comment.id;

    // 拼接评论项HTML
    commentItem.innerHTML = `
      <div class="comment-header">
        <div class="user-info">
          <img src="${comment.userAvatar}" alt="${comment.userName}" class="user-avatar">
          <div class="user-meta">
            <span class="user-name">${comment.userName}</span>
            <span class="comment-time">${formatTime(comment.createTime)}</span>
          </div>
        </div>
        <span class="movie-tag">${comment.movieName}</span>
      </div>
      <div class="comment-content">
        ${comment.content}
      </div>
      <div class="comment-actions">
        <button class="action-btn like-btn ${comment.isLiked ? 'liked' : ''}" data-id="${comment.id}">
          <i class="fas ${comment.isLiked ? 'fa-heart' : 'fa-heart-o'}"></i>
          <span>${comment.likeCount}</span>
        </button>
        <button class="action-btn reply-btn" data-id="${comment.id}">
          <i class="fas fa-reply"></i>
          <span>回复(${comment.replyCount})</span>
        </button>
      </div>
      <!-- 回复列表 -->
      <div class="replies-list" id="replies-${comment.id}">
        ${renderReplies(comment.replies)}
      </div>
    `;

    commentsList.appendChild(commentItem);
  });
}

/**
 * 渲染回复列表
 * @param {Array} replies - 回复数据数组
 * @returns {String} 回复列表HTML字符串
 */
function renderReplies(replies) {
  if (!replies || replies.length === 0) return '';

  let repliesHTML = '';
  replies.forEach(reply => {
    repliesHTML += `
      <div class="reply-item" data-id="${reply.id}">
        <img src="${reply.userAvatar}" alt="${reply.userName}" class="reply-avatar">
        <div class="reply-content">
          <div class="reply-meta">
            <span class="reply-username">${reply.userName}</span>
            <span class="reply-time">${formatTime(reply.createTime)}</span>
          </div>
          <div class="reply-text">${reply.content}</div>
          <div class="reply-actions">
            <button class="reply-action-btn like-reply-btn">
              <i class="fas fa-heart-o"></i> 点赞
            </button>
            <button class="reply-action-btn reply-reply-btn">
              <i class="fas fa-reply"></i> 回复
            </button>
          </div>
        </div>
      </div>
    `;
  });

  return repliesHTML;
}

/**
 * 渲染分页控件
 */
function renderPagination() {
  const totalPages = Math.ceil(filteredComments.length / pageSize);
  pageNumbers.innerHTML = '';

  // 生成页码
  for (let i = 1; i <= totalPages; i++) {
    const pageNumber = document.createElement('div');
    pageNumber.className = `page-number ${i === currentPage ? 'active' : ''}`;
    pageNumber.textContent = i;
    pageNumber.dataset.page = i;
    pageNumbers.appendChild(pageNumber);
  }

  // 禁用/启用上一页、下一页按钮
  prevBtn.disabled = currentPage === 1;
  nextBtn.disabled = currentPage === totalPages;
}

/**
 * 绑定筛选排序事件
 */
function bindFilterSortEvents() {
  // 电影类型筛选
  const movieFilterBtns = movieFilter.querySelectorAll('.tag-btn');
  movieFilterBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      movieFilterBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      filterAndSortComments();
    });
  });

  // 排序方式筛选
  const sortFilterBtns = sortFilter.querySelectorAll('.sort-btn');
  sortFilterBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      sortFilterBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');
      filterAndSortComments();
    });
  });
}

/**
 * 筛选和排序评论
 */
function filterAndSortComments() {
  // 获取当前选中的筛选和排序条件
  const selectedMovie = movieFilter.querySelector('.tag-btn.active').dataset.movie;
  const selectedSort = sortFilter.querySelector('.sort-btn.active').dataset.sort;

  // 1. 筛选（按电影类型）
  let result = [...mockComments];
  if (selectedMovie !== 'all') {
    result = result.filter(comment => comment.movieType === selectedMovie);
  }

  // 2. 排序
  switch (selectedSort) {
    case 'hot':
      // 热度优先（综合评分：点赞数*0.6 + 回复数*0.4）
      result.sort((a, b) => (b.likeCount * 0.6 + b.replyCount * 0.4) - (a.likeCount * 0.6 + a.replyCount * 0.4));
      break;
    case 'newest':
      // 最新发布（按创建时间降序）
      result.sort((a, b) => new Date(b.createTime) - new Date(a.createTime));
      break;
    case 'like':
      // 点赞最多（按点赞数降序）
      result.sort((a, b) => b.likeCount - a.likeCount);
      break;
    case 'reply':
      // 回复最多（按回复数降序）
      result.sort((a, b) => b.replyCount - a.replyCount);
      break;
  }

  // 更新筛选后的评论数据，重置分页，重新渲染
  filteredComments = result;
  currentPage = 1;
  renderComments(getPageComments());
  renderPagination();
}

/**
 * 绑定分页事件
 */
function bindPaginationEvents() {
  // 上一页
  prevBtn.addEventListener('click', () => {
    if (currentPage > 1) {
      currentPage--;
      renderComments(getPageComments());
      renderPagination();
      scrollToCommentsTop();
    }
  });

  // 下一页
  nextBtn.addEventListener('click', () => {
    const totalPages = Math.ceil(filteredComments.length / pageSize);
    if (currentPage < totalPages) {
      currentPage++;
      renderComments(getPageComments());
      renderPagination();
      scrollToCommentsTop();
    }
  });

  // 页码点击
  pageNumbers.addEventListener('click', (e) => {
    if (e.target.classList.contains('page-number')) {
      const page = parseInt(e.target.dataset.page);
      if (page !== currentPage) {
        currentPage = page;
        renderComments(getPageComments());
        renderPagination();
        scrollToCommentsTop();
      }
    }
  });
}

/**
 * 绑定评论操作事件（点赞、回复）
 */
function bindCommentActionEvents() {
  // 评论点赞/取消点赞
  commentsList.addEventListener('click', (e) => {
    if (e.target.closest('.like-btn')) {
      const btn = e.target.closest('.like-btn');
      const commentId = parseInt(btn.dataset.id);
      const commentIndex = mockComments.findIndex(c => c.id === commentId);

      if (commentIndex !== -1) {
        const comment = mockComments[commentIndex];
        // 切换点赞状态
        comment.isLiked = !comment.isLiked;
        // 更新点赞数
        comment.likeCount = comment.isLiked ? comment.likeCount + 1 : comment.likeCount - 1;

        // 同步到筛选后的评论数据
        const filteredIndex = filteredComments.findIndex(c => c.id === commentId);
        if (filteredIndex !== -1) {
          filteredComments[filteredIndex].isLiked = comment.isLiked;
          filteredComments[filteredIndex].likeCount = comment.likeCount;
        }

        // 更新按钮样式和点赞数
        const likeIcon = btn.querySelector('i');
        const likeCountSpan = btn.querySelector('span');
        if (comment.isLiked) {
          btn.classList.add('liked');
          likeIcon.classList.remove('fa-heart-o');
          likeIcon.classList.add('fa-heart');
        } else {
          btn.classList.remove('liked');
          likeIcon.classList.remove('fa-heart');
          likeIcon.classList.add('fa-heart-o');
        }
        likeCountSpan.textContent = comment.likeCount;

        // 模拟向后端发送点赞状态更新请求
        mockUpdateCommentLike(commentId, comment.isLiked);
      }
    }
  });

  // 回复按钮点击（此处仅做演示，实际项目中可弹出回复输入框）
  commentsList.addEventListener('click', (e) => {
    if (e.target.closest('.reply-btn')) {
      const btn = e.target.closest('.reply-btn');
      const commentId = parseInt(btn.dataset.id);
      alert(`点击了评论${commentId}的回复按钮，实际项目中可弹出回复输入框`);
      // 实际项目中可在此处添加回复输入框逻辑
    }
  });
}

/**
 * 格式化时间（将YYYY-MM-DD HH:mm:ss转为友好显示）
 * @param {String} timeStr - 原始时间字符串
 * @returns {String} 格式化后的时间
 */
function formatTime(timeStr) {
  const now = new Date();
  const commentTime = new Date(timeStr);
  const diffMs = now - commentTime; // 时间差（毫秒）
  const diffMin = Math.floor(diffMs / (1000 * 60)); // 分钟
  const diffHour = Math.floor(diffMs / (1000 * 60 * 60)); // 小时
  const diffDay = Math.floor(diffMs / (1000 * 60 * 60 * 24)); // 天
  const diffMonth = Math.floor(diffMs / (1000 * 60 * 60 * 24 * 30)); // 月

  if (diffMin < 1) return '刚刚';
  if (diffMin < 60) return `${diffMin}分钟前`;
  if (diffHour < 24) return `${diffHour}小时前`;
  if (diffDay < 30) return `${diffDay}天前`;
  if (diffMonth < 12) return `${diffMonth}个月前`;
  
  // 超过1年显示完整日期
  return commentTime.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  });
}

/**
 * 滚动到评论区顶部
 */
function scrollToCommentsTop() {
  commentsList.scrollIntoView({ behavior: 'smooth', block: 'start' });
}

/**
 * 模拟向后端发送评论点赞状态更新请求
 * @param {Number} commentId - 评论ID
 * @param {Boolean} isLiked - 是否点赞
 */
function mockUpdateCommentLike(commentId, isLiked) {
  console.log(`后端接口：更新评论${commentId}的点赞状态为${isLiked ? '点赞' : '取消点赞'}`);
  // 真实项目中AJAX示例：
  // fetch(`/api/comments/${commentId}/like`, {
  //   method: 'PUT',
  //   headers: { 'Content-Type': 'application/json' },
  //   body: JSON.stringify({ isLiked })
  // }).then(res => res.json()).then(data => console.log('点赞状态更新成功', data));
}