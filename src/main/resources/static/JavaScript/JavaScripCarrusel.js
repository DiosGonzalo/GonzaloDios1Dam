<script>
        function changeSlide(index) {
            const carousel = new bootstrap.Carousel(document.getElementById('expeditionCarousel'));
            carousel.to(index);
            
            // Actualizar miniaturas activas
            document.querySelectorAll('.thumbnail').forEach((thumb, i) => {
                if(i === index) {
                    thumb.classList.add('active');
                } else {
                    thumb.classList.remove('active');
                }
            });
        }
    </script>