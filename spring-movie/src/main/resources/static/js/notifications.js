// 通知列表（从后端获取）
let notifications = [];

// DOM元素
const notificationList = document.getElementById('notificationList');
const emptyNotification = document.getElementById('emptyNotification');
const filterBtns = document.querySelectorAll('.filter-btn');
const markAllReadBtn = document.getElementById('markAllRead');
const deleteAllBtn = document.getElementById('deleteAll');
const refreshBtn = document.getElementById('refreshBtn');

// 初始化页面
document.addEventListener('DOMContentLoaded', () => {
  fetchNotifications(); // 拉取后端通知
  bindEvents();        // 绑定交互
});

/**
 * 从后端加载通知列表
 */
async function fetchNotifications() {
  try {
    const res = await fetch('/notification/list');
    const data = await res.json();
    if (data.code === 'SUCCESS' && Array.isArray(data.data)) {
      // 补充 typeName，方便前端显示和筛选
      notifications = data.data.map(n => ({
        ...n,
        typeName: mapTypeName(n.type)
      }));
      renderNotifications(notifications);
    } else {
      notifications = [];
      renderNotifications(notifications);
      alert(data.errMsg || '加载通知失败');
    }
  } catch (err) {
    console.error('加载通知失败', err);
    notifications = [];
    renderNotifications(notifications);
    alert('加载通知失败，请稍后再试');
  }
}

/**
 * 渲染通知列表
 * @param {Array} list - 通知数据数组
 */
function renderNotifications(list) {
  // 清空列表
  notificationList.innerHTML = '';

  // 显示空状态或通知列表
  if (list.length === 0) {
    notificationList.style.display = 'none';
    emptyNotification.style.display = 'block';
    return;
  }

  notificationList.style.display = 'flex';
  emptyNotification.style.display = 'none';

  // 渲染每个通知项
  list.forEach(notify => {
    const notificationItem = document.createElement('div');
    // 添加基础类和未读类
    notificationItem.className = `notification-item ${notify.isRead === 0 ? 'unread' : ''}`;
    notificationItem.dataset.id = notify.id;
    notificationItem.dataset.type = notify.typeName;

    // 通知图标配置
    const iconConfig = {
      system: { icon: 'fa-cog', class: 'icon-system' },
      comment: { icon: 'fa-comment', class: 'icon-comment' },
      like: { icon: 'fa-heart', class: 'icon-like' },
      message: { icon: 'fa-envelope', class: 'icon-message' }
    };
    const { icon, class: iconClass } = iconConfig[notify.typeName] || iconConfig.system;

    // 拼接通知项HTML
    notificationItem.innerHTML = `
      <div class="notification-icon ${iconClass}">
        <i class="fas ${icon}"></i>
      </div>
      <div class="notification-content">
        <h3 class="notification-title">${notify.title}</h3>
        <p class="notification-desc">${notify.content}</p>
        <span class="notification-time">${formatTime(notify.createTime)}</span>
      </div>
      <div class="notification-actions">
        <button class="action-btn action-mark-read" data-id="${notify.id}">
          ${notify.isRead === 0 ? '标记已读' : '标记未读'}
        </button>
        <button class="action-btn action-delete action-delete" data-id="${notify.id}">
          <i class="fas fa-trash"></i> 删除
        </button>
      </div>
    `;

    notificationList.appendChild(notificationItem);
  });
}

/**
 * 绑定所有交互事件
 */
function bindEvents() {
  // 1. 筛选通知
  filterBtns.forEach(btn => {
    btn.addEventListener('click', () => {
      // 更新筛选按钮激活状态
      filterBtns.forEach(b => b.classList.remove('active'));
      btn.classList.add('active');

      const filterType = btn.dataset.type;
      let filteredNotifications = [...notifications];

      // 根据筛选类型过滤通知
      if (filterType === 'unread') {
        filteredNotifications = filteredNotifications.filter(notify => notify.isRead === 0);
      } else if (filterType !== 'all') {
        filteredNotifications = filteredNotifications.filter(notify => notify.typeName === filterType);
      }

      // 重新渲染列表
      renderNotifications(filteredNotifications);
    });
  });

  // 2. 标记单个通知为已读/未读
  notificationList.addEventListener('click', (e) => {
    if (e.target.closest('.action-mark-read')) {
      const btn = e.target.closest('.action-mark-read');
      const notifyId = parseInt(btn.dataset.id);
      const notifyIndex = notifications.findIndex(n => n.id === notifyId);

      if (notifyIndex !== -1) {
        // 切换已读状态（0→1, 1→0）
        notifications[notifyIndex].isRead = notifications[notifyIndex].isRead === 0 ? 1 : 0;

        // 重新渲染当前筛选的通知（保持筛选状态）
        const activeFilter = document.querySelector('.filter-btn.active').dataset.type;
        filterBtns.forEach(btn => {
          if (btn.dataset.type === activeFilter) {
            btn.click(); // 触发当前筛选按钮的点击事件，重新渲染
          }
        });
      }
    }
  });

  // 3. 删除单个通知（仅前端列表移除）
  notificationList.addEventListener('click', (e) => {
    if (e.target.closest('.action-delete')) {
      const btn = e.target.closest('.action-delete');
      const notifyId = parseInt(btn.dataset.id);

      if (confirm('确定要删除这条通知吗？')) {
        // 从本地数据中删除通知
        notifications = notifications.filter(n => n.id !== notifyId);

        // 重新渲染当前筛选的通知
        const activeFilter = document.querySelector('.filter-btn.active').dataset.type;
        filterBtns.forEach(btn => {
          if (btn.dataset.type === activeFilter) {
            btn.click();
          }
        });
      }
    }
  });

  // 4. 全部已读
  markAllReadBtn.addEventListener('click', () => {
    if (notifications.length === 0) return;
    notifications.forEach(notify => notify.isRead = 1);

    const activeFilter = document.querySelector('.filter-btn.active').dataset.type;
    filterBtns.forEach(btn => {
      if (btn.dataset.type === activeFilter) {
        btn.click();
      }
    });
  });

  // 5. 清空通知（仅前端列表清空）
  deleteAllBtn.addEventListener('click', () => {
    if (notifications.length === 0) return;

    if (confirm('确定要清空所有通知吗？此操作不可恢复！')) {
      notifications = [];
      renderNotifications(notifications);
    }
  });

  // 6. 刷新通知
  refreshBtn.addEventListener('click', () => {
    fetchNotifications();
  });
}

/**
 * 将后端的type映射为前端使用的typeName
 */
function mapTypeName(type) {
  const map = {
    1: 'system',
    2: 'comment',
    3: 'like',
    4: 'message'
  };
  return map[type] || 'system';
}

/**
 * 格式化时间（将YYYY-MM-DD HH:mm:ss转为友好显示）
 * @param {String} timeStr - 原始时间字符串
 * @returns {String} 格式化后的时间
 */
function formatTime(timeStr) {
  const now = new Date();
  const notifyTime = new Date(timeStr);
  const diffMs = now - notifyTime; // 时间差（毫秒）
  const diffMin = Math.floor(diffMs / (1000 * 60)); // 分钟
  const diffHour = Math.floor(diffMs / (1000 * 60 * 60)); // 小时
  const diffDay = Math.floor(diffMs / (1000 * 60 * 60 * 24)); // 天

  if (diffMin < 1) return '刚刚';
  if (diffMin < 60) return `${diffMin}分钟前`;
  if (diffHour < 24) return `${diffHour}小时前`;
  if (diffDay < 30) return `${diffDay}天前`;

  // 超过30天显示日期
  return notifyTime.toLocaleDateString('zh-CN', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit'
  });
}
