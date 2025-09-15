import { activarGlassmorphism, inicialHeart, initCart, rederigirFav, finalizarCompra } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {
    activarGlassmorphism();

    inicialHeart();

    initCart();

    rederigirFav();

    finalizarCompra();

    document.querySelectorAll(".custom-select").forEach(selectWrapper => {
        const selectHeader = selectWrapper.querySelector(".select-selected");
        const selectItems = selectWrapper.querySelector(".select-items");
        const arrowIcon = selectHeader.querySelector("i");
        const searchInput = selectWrapper.querySelector("input[type='text']");
        const options = selectWrapper.querySelectorAll(".option");

        // Abrir/cerrar el select
        selectHeader.addEventListener("click", (e) => {
            e.stopPropagation();

            // Cierra los demás selects antes de abrir este
            document.querySelectorAll(".custom-select").forEach(other => {
                if (other !== selectWrapper) {
                    other.querySelector(".select-items").classList.add("select-hide");
                    other.querySelector(".select-selected i").classList.remove("up");
                }
            });

            // Alternar visibilidad del menú
            selectItems.classList.toggle("select-hide");

            // Alternar flecha
            arrowIcon.classList.toggle("up");
        });

        // 🚀 Evitar que se cierre al escribir en el buscador
        if (searchInput) {
            searchInput.addEventListener("click", (e) => e.stopPropagation());

            // Filtrar opciones al escribir
            searchInput.addEventListener("keyup", () => {
                const filter = searchInput.value.toLowerCase();
                options.forEach(option => {
                    const text = option.textContent.toLowerCase();
                    option.style.display = text.includes(filter) ? "flex" : "none";
                });
            });
        }
    });

    // Cerrar todos si hago clic fuera
    document.addEventListener("click", () => {
        document.querySelectorAll(".custom-select").forEach(selectWrapper => {
            const selectItems = selectWrapper.querySelector(".select-items");
            const arrowIcon = selectWrapper.querySelector(".select-selected i");

            selectItems.classList.add("select-hide");
            arrowIcon.classList.remove("up");
        });
    });


});