document.addEventListener('DOMContentLoaded', () => {
    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });


    //despliegue de los item
    const items = document.querySelectorAll('.list__item');

    items.forEach(item => {
        const icon = item.querySelector('.despliegue__icon i');

        item.addEventListener('click', () => {
            // Cierra los demás ítems
            items.forEach(otherItem => {
                if (otherItem !== item) {
                    otherItem.classList.remove('active');
                    const otherIcon = otherItem.querySelector('.despliegue__icon i');
                    if (otherIcon) {
                        otherIcon.classList.remove('ri-subtract-line');
                        otherIcon.classList.add('ri-add-line');
                    }
                }
            });

            // Alterna el ítem actual
            item.classList.toggle('active');

            // Cambia el ícono
            if (item.classList.contains('active')) {
                icon.classList.remove('ri-add-line');
                icon.classList.add('ri-subtract-line');
            } else {
                icon.classList.remove('ri-subtract-line');
                icon.classList.add('ri-add-line');
            }
        });
    });

    //Overflow de los productos
    const scrollContainer = document.querySelector('.deal__flex');

    scrollContainer.addEventListener('mouseenter', () => {
        scrollContainer.classList.add('scroll-active');
    });

    scrollContainer.addEventListener('mouseleave', () => {
        scrollContainer.classList.remove('scroll-active');
    });

});