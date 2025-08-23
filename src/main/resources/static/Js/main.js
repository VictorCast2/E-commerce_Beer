export function activarGlassmorphism() {
    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
}


document.addEventListener('DOMContentLoaded', () => {
    //Llamamos a la funcion
    activarGlassmorphism();

    //Carrusel del inicio
    const hero = document.querySelector('.hero');
    const slides = document.querySelectorAll('.carousel__slide');
    const dots = document.querySelectorAll('.carousel__dot');
    const arrows = document.querySelectorAll('.carousel__arrow');

    let currentSlide = 0;
    let autoPlayInterval = null;

    // Mostrar slide por índice
    function showSlide(index) {
        slides.forEach((slide, i) => {
            slide.classList.toggle('active', i === index);
            dots[i]?.classList.toggle('active', i === index);
        });

        const activeSlide = slides[index];
        const theme = activeSlide.getAttribute('data-theme');

        hero.classList.remove('hero--paulaner', 'hero--heineken', 'hero--budweiser', 'hero--guinness');
        hero.classList.add(`hero--${theme}`);

        currentSlide = index;
    }


    // Siguiente slide
    function nextSlide() {
        const nextIndex = (currentSlide + 1) % slides.length;
        showSlide(nextIndex);
    }

    // Slide anterior
    function prevSlide() {
        const prevIndex = (currentSlide - 1 + slides.length) % slides.length;
        showSlide(prevIndex);
    }

    // Iniciar autoplay
    function startAutoplay() {
        autoPlayInterval = setInterval(nextSlide, 5000);
    }

    // Detener autoplay temporalmente
    function stopAutoplay() {
        clearInterval(autoPlayInterval);
    }

    // Click en dots
    dots.forEach(dot => {
        dot.addEventListener('click', () => {
            const index = parseInt(dot.dataset.index);
            showSlide(index);
            stopAutoplay();
            startAutoplay(); // Reiniciar autoplay al interactuar
        });
    });

    // Click en flechas
    arrows.forEach(arrow => {
        arrow.addEventListener('click', () => {
            const direction = arrow.dataset.direction;
            if (direction === 'next') {
                nextSlide();
            } else {
                prevSlide();
            }
            stopAutoplay();
            startAutoplay(); // Reiniciar autoplay
        });
    });

    // Mostrar primer slide
    showSlide(0);
    startAutoplay();

    //duplicacion de los logos
    const logos = document.getElementById("slider").cloneNode(true);
    document.getElementById("logos").appendChild(logos);

    // mostrar los productos con carrusel
    document.querySelectorAll(".flex").forEach(carrusel => {
        const track = carrusel.querySelector(".flex__productos-track");
        const prevBtn = carrusel.querySelector(".arrow--left");
        const nextBtn = carrusel.querySelector(".arrow--right");

        const cardWidth = 300; // ancho de cada card
        const gap = 40;        // espacio entre cards
        const visibles = 4;    // cuántos se muestran a la vez

        let posicion = 0;
        const totalProductos = track.querySelectorAll(".card").length;
        const maxPosicion = (totalProductos - visibles) * (cardWidth + gap);

        const paso = visibles * (cardWidth + gap); // mueve 4 productos

        nextBtn.addEventListener("click", () => {
            if (posicion < maxPosicion) {
                posicion += paso;
                if (posicion > maxPosicion) posicion = maxPosicion; // no pasar límite
                track.style.transform = `translateX(-${posicion}px)`;
            }
        });

        prevBtn.addEventListener("click", () => {
            if (posicion > 0) {
                posicion -= paso;
                if (posicion < 0) posicion = 0; // no pasar inicio
                track.style.transform = `translateX(-${posicion}px)`;
            }
        });
    });

    //cambiar anio del footer automaticamente
    document.getElementById("anio__pagina").textContent = new Date().getFullYear();

});
