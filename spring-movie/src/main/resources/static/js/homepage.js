// 移动端菜单切换
        const mobileMenuBtn = document.querySelector('.mobile-menu-btn');
        const navMenu = document.querySelector('.nav-menu');

        mobileMenuBtn.addEventListener('click', () => {
            navMenu.classList.toggle('show');
        });

        // 轮播图功能
        const carouselSlides = document.querySelector('.carousel-slides');
        const carouselIndicators = document.querySelectorAll('.carousel-indicator');
        const carouselPrev = document.querySelector('.carousel-prev');
        const carouselNext = document.querySelector('.carousel-next');
        let currentSlide = 0;
        const totalSlides = carouselIndicators.length;

        // 切换轮播图
        function goToSlide(index) {
            currentSlide = index;
            carouselSlides.style.transform = `translateX(-${currentSlide * 100}%)`;
            // 更新指示器
            carouselIndicators.forEach((indicator, i) => {
                indicator.classList.toggle('active', i === currentSlide);
            });
        }

        // 指示器点击事件
        carouselIndicators.forEach((indicator, i) => {
            indicator.addEventListener('click', () => {
                goToSlide(i);
            });
        });

        // 上一张/下一张
        carouselPrev.addEventListener('click', () => {
            currentSlide = (currentSlide - 1 + totalSlides) % totalSlides;
            goToSlide(currentSlide);
        });

        carouselNext.addEventListener('click', () => {
            currentSlide = (currentSlide + 1) % totalSlides;
            goToSlide(currentSlide);
        });

        // 自动轮播
        setInterval(() => {
            currentSlide = (currentSlide + 1) % totalSlides;
            goToSlide(currentSlide);
        }, 5000);

        // 分类标签点击事件
        const categoryItems = document.querySelectorAll('.category-item');
        categoryItems.forEach(item => {
            item.addEventListener('click', () => {
                categoryItems.forEach(i => i.classList.remove('active'));
                item.classList.add('active');
                // 这里可添加分类筛选逻辑
            });
        });