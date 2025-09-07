import { activarGlassmorphism, inicialHeart, initCart, rederigirFav, finalizarCompra } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {

    activarGlassmorphism();

    inicialHeart();

    initCart();

    rederigirFav();

    finalizarCompra();

    //cargar los datos en ver
    const product = JSON.parse(localStorage.getItem("selectedProduct"));

    if (product) {
        document.querySelector(".flex__img img").src = product.image;
        document.querySelector(".details__category").textContent = product.category;
        document.querySelector(".details__name").textContent = product.name;

        // precios
        const prices = document.querySelectorAll(".details__precie .precie__total");
        if (prices.length > 0) {
            prices[0].textContent = "$" + product.price;
            if (product.oldPrice) {
                prices[1].textContent = "$" + product.oldPrice;
            }
        }

        // Breadcrumb dinámico (los 3 últimos)
        document.querySelector(".breadcrumb-subcategory").textContent = product.subcategory + " /";
        document.querySelector(".breadcrumb-category").textContent = product.category + " /";
        document.querySelector(".breadcrumb-name").textContent = product.name;
    }


    // Seleccionamos todos los contenedores de quantity-control
    document.querySelectorAll(".quantity-control").forEach(control => {
        const minusBtn = control.querySelector(".minus");
        const plusBtn = control.querySelector(".plus");
        const qtySpan = control.querySelector(".qty");

        let quantity = 0; // valor inicial

        plusBtn.addEventListener("click", () => {
            quantity++;
            qtySpan.textContent = quantity;
        });

        minusBtn.addEventListener("click", () => {
            if (quantity > 0) {
                quantity--;
                qtySpan.textContent = quantity;
            }
        });
    });

    //texto de detalles,envio y ayuda
    const titles = document.querySelectorAll(".complemento__title");
    const texts = document.querySelectorAll(".complemento__text");

    // Mostrar siempre el primer texto (Detalles)
    texts[0].classList.add("active");

    titles.forEach((title, index) => {
        title.addEventListener("click", () => {
            // Quitar active de todos los títulos y textos
            titles.forEach(t => t.classList.remove("active"));
            texts.forEach(t => t.classList.remove("active"));

            // Poner active en el título clickeado
            title.classList.add("active");
            texts[index].classList.add("active");
        });
    });

    //abrir modal
    //abrir el formulario de envio y pago
    const abirmodal = document.getElementById("calificar__abrir");
    const modal = document.getElementById("modalnewadd");
    const closeModalBtn = document.querySelector(".modal__close");

    abirmodal.addEventListener("click", () => {
        modal.classList.remove("newadd--hidden");
    })

    closeModalBtn.addEventListener("click", () => {
        modal.classList.add("newadd--hidden");
    });


    window.addEventListener("click", (e) => {
        if (e.target === modal) {
            modal.classList.add("newadd--hidden");
        }
    });


});