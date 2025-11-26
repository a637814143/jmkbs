// 设置当前日期
    document.addEventListener('DOMContentLoaded', function() {
      const now = new Date();
      const options = { year: 'numeric', month: 'long', day: 'numeric', weekday: 'long' };
      document.getElementById('current-date').textContent = now.toLocaleDateString('zh-CN', options);
      
      // 侧边栏切换
      const sidebar = document.getElementById('sidebar');
      const mainContent = document.getElementById('main-content');
      const toggleSidebar = document.getElementById('toggle-sidebar');
      const closeSidebar = document.getElementById('close-sidebar');
      
      toggleSidebar.addEventListener('click', function() {
        sidebar.classList.toggle('-translate-x-full');
      });
      
      closeSidebar.addEventListener('click', function() {
        sidebar.classList.add('-translate-x-full');
      });
      
      // 销售趋势图表
      const salesCtx = document.getElementById('sales-chart').getContext('2d');
      const salesChart = new Chart(salesCtx, {
        type: 'line',
        data: {
          labels: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
          datasets: [
            {
              label: '销售额',
              data: [65000, 59000, 80000, 81000, 56000, 85000, 90000, 92000, 87000, 95000, 102000, 110000],
              borderColor: '#165DFF',
              backgroundColor: 'rgba(22, 93, 255, 0.1)',
              tension: 0.4,
              fill: true
            },
            {
              label: '订单量',
              data: [450, 420, 580, 610, 480, 650, 720, 750, 690, 780, 850, 920],
              borderColor: '#36CFC9',
              backgroundColor: 'transparent',
              tension: 0.4,
              yAxisID: 'y1'
            }
          ]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          interaction: {
            mode: 'index',
            intersect: false,
          },
          scales: {
            y: {
              beginAtZero: true,
              ticks: {
                callback: function(value) {
                  return '¥' + value.toLocaleString();
                }
              }
            },
            y1: {
              beginAtZero: true,
              position: 'right',
              grid: {
                drawOnChartArea: false,
              },
            }
          }
        }
      });
      
      // 销售渠道图表
      const channelsCtx = document.getElementById('channels-chart').getContext('2d');
      const channelsChart = new Chart(channelsCtx, {
        type: 'doughnut',
        data: {
          labels: ['官网', '移动端', '第三方平台', '线下销售'],
          datasets: [{
            data: [45, 30, 15, 10],
            backgroundColor: [
              '#165DFF',
              '#36CFC9',
              '#52C41A',
              '#FAAD14'
            ],
            borderWidth: 0,
            hoverOffset: 4
          }]
        },
        options: {
          responsive: true,
          maintainAspectRatio: false,
          cutout: '70%',
          plugins: {
            legend: {
              display: false
            }
          }
        }
      });
      
      // 模拟数据加载动画效果
      setTimeout(() => {
        document.querySelectorAll('.card-shadow').forEach(el => {
          el.classList.add('opacity-100');
          el.classList.remove('opacity-0');
        });
      }, 300);

      // 发布通知
      const notificationForm = document.getElementById('notification-form');
      if (notificationForm) {
        const titleInput = document.getElementById('notification-title');
        const contentInput = document.getElementById('notification-content');
        const typeSelect = document.getElementById('notification-type');
        const resultText = document.getElementById('notification-result');

        notificationForm.addEventListener('submit', async (e) => {
          e.preventDefault();
          const title = titleInput.value.trim();
          const content = contentInput.value.trim();
          const type = typeSelect.value;

          if (!title || !content) {
            resultText.textContent = '标题和内容不能为空';
            resultText.classList.remove('text-success');
            resultText.classList.add('text-danger');
            return;
          }

          resultText.textContent = '提交中...';
          resultText.classList.remove('text-danger');
          resultText.classList.remove('text-success');

          try {
            const resp = await fetch('/notification/publish', {
              method: 'POST',
              headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
              body: new URLSearchParams({ title, content, type }).toString()
            });
            const data = await resp.json();
            if (data.code === 'SUCCESS') {
              resultText.textContent = '发布成功';
              resultText.classList.add('text-success');
              titleInput.value = '';
              contentInput.value = '';
              typeSelect.value = '1';
            } else {
              resultText.textContent = data.errMsg || '发布失败';
              resultText.classList.add('text-danger');
            }
          } catch (err) {
            console.error(err);
            resultText.textContent = '请求失败，请稍后重试';
            resultText.classList.add('text-danger');
          }
        });
      }
    });
