// 简单交互功能：收藏按钮切换状态、分享功能
document.addEventListener('DOMContentLoaded', () => {
  // 收藏按钮
  const collectBtn = document.querySelector('.collect-btn');
  let isCollected = false;

  collectBtn.addEventListener('click', () => {
    isCollected = !isCollected;
    if (isCollected) {
      collectBtn.innerHTML = '<i class="fas fa-heart"></i> 已收藏';
      collectBtn.style.backgroundColor = '#e53e3e';
      alert('已收藏该电影！');
    } else {
      collectBtn.innerHTML = '<i class="fas fa-heart"></i> 收藏';
      collectBtn.style.backgroundColor = '#165dff';
      alert('已取消收藏');
    }
  });

  // 分享功能（模拟）
  const shareBtn = document.querySelector('.share-btn');
  shareBtn.addEventListener('click', () => {
    alert('分享链接已复制到剪贴板！\n（实际项目中可调用原生分享API或复制链接）');
    // 真实分享逻辑示例：
    // if (navigator.share) {
    //   navigator.share({
    //     title: '肖申克的救赎',
    //     text: '推荐一部经典电影：肖申克的救赎',
    //     url: window.location.href
    //   });
    // } else {
    //   // 复制链接到剪贴板
    //   navigator.clipboard.writeText(window.location.href);
    // }
  });
});