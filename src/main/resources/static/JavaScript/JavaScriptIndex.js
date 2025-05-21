 window.addEventListener('scroll', function() {
            const navbar = document.querySelector('.navbar');
            if (window.scrollY > 50) {
                navbar.classList.add('shadow-lg');
                navbar.style.backgroundColor = '#000';
            } else {
                navbar.classList.remove('shadow-lg');
                navbar.style.backgroundColor = '#000';
            }
        });