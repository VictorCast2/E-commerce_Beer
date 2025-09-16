import { activarGlassmorphism, inicialHeart, initCart, rederigirFav, finalizarCompra, verProductos } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {
    activarGlassmorphism();

    inicialHeart();

    initCart();

    rederigirFav();

    finalizarCompra();

    verProductos();


    //Abrir todos los select
    document.querySelectorAll(".custom-select").forEach(selectWrapper => {
        const selectHeader = selectWrapper.querySelector(".select-selected");
        const selectItems = selectWrapper.querySelector(".select-items");
        const arrowIcon = selectHeader.querySelector("i");
        const searchInput = selectWrapper.querySelector("input[type='text']");
        let options = selectWrapper.querySelectorAll(".option"); // ← lo declaramos como let

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

        // Evitar que se cierre al escribir en el buscador
        if (searchInput) {
            searchInput.addEventListener("click", (e) => e.stopPropagation());

            // Filtrar opciones al escribir
            searchInput.addEventListener("keyup", () => {
                const filter = searchInput.value.toLowerCase();
                // 🔥 volvemos a obtener todas las opciones cada vez (para incluir las dinámicas)
                options = selectWrapper.querySelectorAll(".option");

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

    // Contenedor de chips seleccionadas
    const selectedFilters = document.getElementById("selected-filters");

    // Función que crea un chip al seleccionar
    function addChip(value, checkbox) {
        // Evitar duplicados
        if (document.querySelector(`[data-chip="${value}"]`)) return;

        const chip = document.createElement("div");
        chip.classList.add("filter-chip");
        chip.setAttribute("data-chip", value);
        chip.innerHTML = `
    ${value} <span data-remove="${value}">✖</span>
    `;

        // Cuando se da clic en la X
        chip.querySelector("span").addEventListener("click", () => {
            chip.remove();
            checkbox.checked = false; // desmarcar en el select
        });

        selectedFilters.appendChild(chip);
    }

    // Detectar todos los checkbox de filtros
    document.addEventListener("change", (e) => {
        if (e.target.type === "checkbox") {
            const value = e.target.value;
            if (e.target.checked) {
                addChip(value, e.target);
            } else {
                // Si desmarcan desde el select, quitar chip
                const chip = document.querySelector(`[data-chip="${value}"]`);
                if (chip) chip.remove();
            }
        }
    });


    // select de marcas
    const marcasLicores = [
        "Johnnie Walker",
        "Chivas Regal",
        "Jack Daniel's",
        "Ballantine's",
        "Absolut",
        "Smirnoff",
        "Bacardi",
        "Havana Club",
        "Don Julio",
        "Patrón",
        "Tanqueray",
        "Beefeater",
        "Hennessy",
        "Martell",
        "Torres",
        "Campo Viejo",
        "Moët & Chandon",
        "Veuve Clicquot"
    ];

    const containerMarca = document.getElementById("options-container--marca");

    marcasLicores.forEach(marca => {
        const label = document.createElement("label");
        label.classList.add("option");
        label.innerHTML = `
        <input type="checkbox" value="${marca}">
        ${marca}
    `;
        containerMarca.appendChild(label);
    });

    //llenando el select de pais
    const countries = [
        "Argentina", "Alemania", "Chile", "Colombia", "Belgica",
        "Escocia", "Irlanda", "Francia", "España", "Estados Unidos",
        "Guatemala", "Holanda", "México", "Italia", "Reino Unido",
        "Russia", "Puerto Rico", "República Dominicana",
        "Suecia"
    ];

    const containerPais = document.getElementById("options-container--paises");

    countries.forEach(country => {
        const label = document.createElement("label");
        label.classList.add("option");
        label.innerHTML = `
        <input type="checkbox" value="${country}">
        ${country}
    `;
        containerPais.appendChild(label);
    });

    const categoriasLicores = {
        "Vino": ["Vino Rosado", "Vino Blanco", "Vino Espumoso", "Vino Tinto"],
        "Whisky": ["Single Malt", "Blended", "Bourbon", "Irish"],
        "Ron": ["Ron Blanco", "Ron Añejo", "Ron Oscuro", "Ron Especiado"],
        "Vodka": ["Vodka Clásico", "Vodka Saborizado"],
        "Tequila": ["Blanco", "Reposado", "Añejo", "Extra Añejo"],
        "Ginebra": ["London Dry", "Old Tom", "Ginebra de Sabor"],
        "Brandy": ["Cognac", "Armagnac", "Brandy Español"]
    };

    const containerCategoria = document.getElementById("options-container--categoria");

    Object.entries(categoriasLicores).forEach(([categoria, subcategorias]) => {
        // Categoria principal también como checkbox
        const catLabel = document.createElement("label");
        catLabel.classList.add("option");
        catLabel.style.fontWeight = "700"; // para destacarla
        catLabel.innerHTML = `
        <input type="checkbox" value="${categoria}">
        ${categoria}
    `;
        containerCategoria.appendChild(catLabel);

        // Subcategorías con sangría para diferenciarlas visualmente
        subcategorias.forEach(sub => {
            const label = document.createElement("label");
            label.classList.add("option");
            label.style.paddingLeft = "30px"; // sangría
            label.innerHTML = `
            <input type="checkbox" value="${sub}">
            ${sub}
        `;
            containerCategoria.appendChild(label);
        });
    });


});