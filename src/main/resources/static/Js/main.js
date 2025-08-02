document.addEventListener('DOMContentLoaded', () => {

    const hero = document.querySelector('.hero');
    const slides = document.querySelectorAll('.carousel__slide');
    const dots = document.querySelectorAll('.carousel__dot');

    let currentSlide = 0;
    let autoPlayInterval = null;

    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === index);
            dots[i].classList.toggle('active', i === index);
        });

        const activeSlide = slides[index];
        const theme = activeSlide.getAttribute('data-theme');

        hero.classList.remove('hero--paulaner', 'hero--heineken', 'hero--budweiser');
        hero.classList.add(`hero--${theme}`);

        currentSlide = index;
    }

    // Función para pasar al siguiente slide automáticamente
    function nextSlide() {
        let nextIndex = (currentSlide + 1) % slides.length;
        showSlide(nextIndex);
    }

    // Iniciar autoplay
    function startAutoplay() {
        autoPlayInterval = setInterval(nextSlide, 5000); // cambia cada 5 segundos
    }

    // Detener autoplay (opcional, si se necesita luego)
    function stopAutoplay() {
        clearInterval(autoPlayInterval);
    }

    // Escuchar clics en los dots
    dots.forEach(dot => {
        dot.addEventListener('click', () => {
            const index = parseInt(dot.dataset.index);
            showSlide(index);
        });
    });

    // Mostrar el primer slide al cargar y arrancar autoplay
    showSlide(0);
    startAutoplay();

    /* const logos = document.getElementById("slider").cloneNode(true);
    document.getElementById("logos").appendChild(logos); */

});
