import { activarGlassmorphism, inicialHeart, initCart, rederigirFav, finalizarCompra, addProductToCart } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {
    activarGlassmorphism();

    inicialHeart();

    initCart();

    rederigirFav();

    finalizarCompra();

    // --- Cargar producto seleccionado
    const product = JSON.parse(localStorage.getItem("selectedProduct"));

    if (product) {
        document.querySelector(".flex__img img").src = product.image;
        document.querySelector(".details__category").textContent = product.category;
        document.querySelector(".details__name").textContent = product.name;
        document.querySelector(".complemento__text").textContent = product.descripcion;

        const prices = document.querySelectorAll(".details__precie .precie__total");
        if (prices.length > 0) {
            prices[0].textContent = "$" + product.price;
            if (product.oldPrice) prices[1].textContent = "$" + product.oldPrice;
        }

        document.querySelector(".breadcrumb-subcategory").textContent = product.subcategory + " /";
        document.querySelector(".breadcrumb-category").textContent = product.category + " /";
        document.querySelector(".breadcrumb-name").textContent = product.name;
    }

    // --- Contador (quantity-control)
    const qtyContainer = document.querySelector(".plus__control");
    const minusBtn = qtyContainer.querySelector(".control__minus");
    const plusBtn = qtyContainer.querySelector(".control__plus");
    const qtySpan = qtyContainer.querySelector(".control__qty");

    let qty = 0; // inicializamos en 0
    qtySpan.textContent = qty;

    plusBtn.addEventListener("click", () => {
        qty++;
        qtySpan.textContent = qty;
    });

    minusBtn.addEventListener("click", () => {
        if (qty > 0) {
            qty--;
            qtySpan.textContent = qty;
        }
    });

    // --- Botón "Agregar al carrito" desde Ver.html
    const addBtn = document.querySelector(".details__addCard");
    addBtn.addEventListener("click", () => {
        if (!product || qty <= 0) return;

        const numericPrice = typeof product.price === "string"
            ? parseFloat(product.price.replace(/[^0-9.]/g, ""))
            : product.price;

        // OBTENER STOCK DEL PRODUCTO (si está disponible)
        const stock = product.stock || null;

        addProductToCart({
            name: product.name,
            price: numericPrice,
            img: product.image,
            qty: qty,          // cantidad del contador
            openDrawer: true,   // abrir drawer
            stock: stock
        });

        // Reiniciamos el contador
        qty = 0;
        qtySpan.textContent = qty;
    });

    // --- Texto detalles / envíos / ayuda
    const titles = document.querySelectorAll(".complemento__title");
    const texts = document.querySelectorAll(".complemento__text");
    texts[0].classList.add("active"); // mostrar primer texto

    titles.forEach((title, index) => {
        title.addEventListener("click", () => {
            titles.forEach(t => t.classList.remove("active"));
            texts.forEach(t => t.classList.remove("active"));
            title.classList.add("active");
            texts[index].classList.add("active");
        });
    });

    // --- Modal calificar producto
    const abirmodal = document.getElementById("calificar__abrir");
    const modal = document.getElementById("modalnewadd");
    const closeModalBtn = document.querySelector(".modal__close");

    abirmodal.addEventListener("click", () => modal.classList.remove("newadd--hidden"));
    closeModalBtn.addEventListener("click", () => modal.classList.add("newadd--hidden"));
    window.addEventListener("click", e => { if (e.target === modal) modal.classList.add("newadd--hidden"); });

    // --- Calificación estrellas
    const stars = document.querySelectorAll(".stars i");
    const ratingInput = document.getElementById("rating");

    stars.forEach(star => {
        star.addEventListener("click", () => {
            const value = star.getAttribute("data-value");
            ratingInput.value = value;

            stars.forEach(s => s.classList.remove("ri-star-fill"));
            stars.forEach(s => s.classList.add("ri-star-line"));

            for (let i = 0; i < value; i++) {
                stars[i].classList.remove("ri-star-line");
                stars[i].classList.add("ri-star-fill");
            }
        });
    });


});