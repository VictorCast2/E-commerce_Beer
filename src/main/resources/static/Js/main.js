// =======================
// 🌟 Efecto Glassmorphism Header
// =======================
export function activarGlassmorphism() {
    const header = document.querySelector('.header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header?.classList.add('scrolled');
        } else {
            header?.classList.remove('scrolled');
        }
    });
}

// =======================
// 🚀 Carrusel Hero (Inicio)
// =======================
function initHeroCarousel() {
    const hero = document.querySelector('.hero');
    const slides = document.querySelectorAll('.carousel__slide');
    const dots = document.querySelectorAll('.carousel__dot');
    const arrows = document.querySelectorAll('.carousel__arrow');

    if (!hero || slides.length === 0) return;

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

        hero.classList.remove(
            'hero--paulaner',
            'hero--heineken',
            'hero--budweiser',
            'hero--guinness'
        );
        hero.classList.add(`hero--${theme}`);

        currentSlide = index;
    }

    function nextSlide() {
        showSlide((currentSlide + 1) % slides.length);
    }

    function prevSlide() {
        showSlide((currentSlide - 1 + slides.length) % slides.length);
    }

    function startAutoplay() {
        autoPlayInterval = setInterval(nextSlide, 5000);
    }

    function resetAutoplay() {
        clearInterval(autoPlayInterval);
        startAutoplay();
    }

    // Click en dots
    dots.forEach(dot =>
        dot.addEventListener('click', () => {
            showSlide(parseInt(dot.dataset.index));
            resetAutoplay();
        })
    );

    // Click en flechas
    arrows.forEach(arrow =>
        arrow.addEventListener('click', () => {
            arrow.dataset.direction === 'next' ? nextSlide() : prevSlide();
            resetAutoplay();
        })
    );

    showSlide(0);
    startAutoplay();
}

// =======================
// ♻️ Duplicación de logos (scroll infinito)
// =======================
function duplicarLogos() {
    const slider = document.getElementById("slider");
    const logos = document.getElementById("logos");
    if (slider && logos) {
        logos.appendChild(slider.cloneNode(true));
    }
}

// =======================
// 🛒 Carrusel de productos
// =======================
function initCarruselesProductos() {
    document.querySelectorAll(".flex").forEach(carrusel => {
        const track = carrusel.querySelector(".flex__productos-track");
        const prevBtn = carrusel.querySelector(".arrow--left");
        const nextBtn = carrusel.querySelector(".arrow--right");
        if (!track || !prevBtn || !nextBtn) return;

        const cardWidth = 300; // ancho de cada card
        const gap = 40;        // espacio entre cards
        const visibles = 4;    // cuántos se muestran a la vez

        let posicion = 0;
        const totalProductos = track.querySelectorAll(".card").length;
        const maxPosicion = (totalProductos - visibles) * (cardWidth + gap);
        const paso = visibles * (cardWidth + gap);

        function mover(delta) {
            posicion = Math.min(Math.max(posicion + delta, 0), maxPosicion);
            track.style.transform = `translateX(-${posicion}px)`;
        }

        nextBtn.addEventListener("click", () => mover(paso));
        prevBtn.addEventListener("click", () => mover(-paso));
    });
}

// =======================
// 📅 Año automático en footer
// =======================
function actualizarAnioFooter() {
    const el = document.getElementById("anio__pagina");
    if (el) el.textContent = new Date().getFullYear();
}

// =======================
// 👤 Menú Perfil Usuario
// =======================
function initSubMenuPerfil() {
    const subMenu = document.getElementById("SubMenu");
    const profileImage = document.querySelector(".nav__img");
    if (!subMenu || !profileImage) return;

    profileImage.addEventListener("click", () => {
        subMenu.classList.toggle("open__menu");
    });

    document.addEventListener("click", (e) => {
        if (!subMenu.contains(e.target) && !profileImage.contains(e.target)) {
            subMenu.classList.remove("open__menu");
        }
    });
}

// =======================
//  🚀 Inicialización
// =======================
document.addEventListener('DOMContentLoaded', () => {
    activarGlassmorphism();
    initHeroCarousel();
    duplicarLogos();
    initCarruselesProductos();
    actualizarAnioFooter();
    initSubMenuPerfil();
});