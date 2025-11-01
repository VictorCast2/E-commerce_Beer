import { activarGlassmorphism, inicialHeart, initCart, finalizarCompra, addProductToCart } from "./main.js";


document.addEventListener('DOMContentLoaded', () => {

    /* console.log("JS cargado correctamente");
    console.log("favTableBody:", favTableBody);
    console.log("prevPageBtn:", prevPageBtn);
    console.log("nextPageBtn:", nextPageBtn); */


    // Usar la funcion activarGlassmorphism
    activarGlassmorphism();

    inicialHeart();

    initCart();

    //finalizar compra
    finalizarCompra();

    //rellenar la tabla con paginación
    const favTableBody = document.getElementById("favoritos-body");
    const favCount = document.getElementById("fav-count");
    const paginationText = document.getElementById("pagination-text");
    const prevPageBtn = document.getElementById("prev-page");
    const nextPageBtn = document.getElementById("next-page");
    const paginationButtonsContainer = document.getElementById("pagination-buttons");
    const thereSpan = document.getElementById("there?");

    let favoritos = JSON.parse(localStorage.getItem("favoritos")) || [];
    let currentPage = 1;
    const rowsPerPage = 4; // productos por página

    // Mostrar contador inicial
    if (favCount) favCount.textContent = favoritos.length;

    // Función para renderizar la tabla según la página
    function renderTable() {
        favTableBody.innerHTML = "";

        if (favoritos.length === 0) {
            // Mostrar mensaje cuando no hay productos
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td colspan="6" style="text-align:center; padding:20px; font-weight:600;">
                No hay productos en Favorito / Lista de deseos
            </td>
        `;
            favTableBody.appendChild(tr);

            if (favCount) favCount.textContent = 0;
            paginationText.textContent = `Mostrando 0-0 de 0`;
            paginationButtonsContainer.innerHTML = "";
            prevPageBtn.disabled = true;
            nextPageBtn.disabled = true;

            // Aquí mostramos 0 productos en lugar de limpiar
            thereSpan.textContent = `Hay 0 productos en esta lista de deseos.`;
            return; // salir de la función
        }

        // Render normal si hay productos
        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        const paginatedItems = favoritos.slice(start, end);

        paginatedItems.forEach((producto, index) => {
            const estadoClass = producto.estado === "En Stock" ? "estado-stock" : "estado-agotado";

            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td><img src="${producto.imagen}" width="50"/></td>
            <td>${producto.nombre}</td>
            <td>${producto.precio}</td>
            <td><span class="estado ${estadoClass}">${producto.estado}</span></td>
            <td><button class="content__carrito">Agregar al carrito</button></td>
            <td>
                <button class="content__icon btn-delete" data-index="${start + index}">
                    <i class="ri-delete-bin-6-line" title="Eliminar"></i>
                </button>
            </td>
        `;
            favTableBody.appendChild(tr);
        });

        // Actualizar contador y texto de paginación
        favCount.textContent = favoritos.length;
        paginationText.textContent = `Mostrando ${start + 1}-${Math.min(end, favoritos.length)} de ${favoritos.length}`;

        thereSpan.textContent = `Hay ${favoritos.length} productos en esta lista de deseos.`;

        renderPaginationButtons();
    }

    // Función para renderizar botones de páginas
    function renderPaginationButtons() {
        paginationButtonsContainer.innerHTML = "";
        const totalPages = Math.ceil(favoritos.length / rowsPerPage);

        for (let i = 1; i <= totalPages; i++) {
            const btn = document.createElement("button");
            btn.textContent = i;
            btn.classList.add("pagination__btn");
            if (i === currentPage) btn.classList.add("active");

            btn.addEventListener("click", () => {
                currentPage = i;
                renderTable();
            });

            paginationButtonsContainer.appendChild(btn);
        }

        prevPageBtn.disabled = currentPage === 1;
        nextPageBtn.disabled = currentPage === totalPages;
    }

    // Eventos de flechas
    prevPageBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            renderTable();
        }
    });

    nextPageBtn.addEventListener("click", () => {
        if (currentPage < Math.ceil(favoritos.length / rowsPerPage)) {
            currentPage++;
            renderTable();
        }
    });

    // Delegación de eventos para eliminar
    favTableBody.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-delete");
        if (!btn) return;

        const index = parseInt(btn.getAttribute("data-index"));

        Swal.fire({
            title: "¿Estás seguro?",
            text: "¡No podrás revertir esto!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "Cancelar",
            confirmButtonText: "Sí, ¡eliminalo!",
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                //Ahora sí, eliminar el producto
                favoritos.splice(index, 1);
                localStorage.setItem("favoritos", JSON.stringify(favoritos));

                // Ajustar página si se queda vacía
                if ((currentPage - 1) * rowsPerPage >= favoritos.length && currentPage > 1) {
                    currentPage--;
                }

                renderTable();

                Swal.fire({
                    title: "¡Eliminado!",
                    text: "El producto ha sido eliminado.",
                    icon: "success",
                    customClass: {
                        title: 'swal-title',
                        popup: 'swal-popup'
                    }
                });
            }
        });
    });

    // Render inicial
    renderTable();


    // Delegación de eventos: un único listener que maneje "Agregar al carrito" y "Eliminar"
    favTableBody.addEventListener("click", (e) => {
        // 1) AGREGAR AL CARRITO
        const addBtn = e.target.closest(".content__carrito");
        if (addBtn) {
            const row = addBtn.closest("tr");
            if (!row) return;

            // Obtener valores desde las celdas (asegúrate del orden: imagen, nombre, precio, ...)
            const imgEl = row.querySelector("img");
            const img = imgEl ? imgEl.src : "";
            const name = row.querySelectorAll("td")[1].textContent.trim();
            const priceText = row.querySelectorAll("td")[2].textContent.trim();
            // Normalizar precio (quita símbolos y coma decimal)
            const price = parseFloat(priceText.replace(/[^0-9.,-]/g, "").replace(",", ".")) || 0;

            // Llamamos la función pública
            addProductToCart({ name, price, img, openDrawer: true });

            // Opcional: si quieres quitar el producto de favoritos al agregar al carrito:
            // const index = parseInt(addBtn.getAttribute("data-index"));
            // favoritos.splice(index, 1);
            // localStorage.setItem("favoritos", JSON.stringify(favoritos));
            // renderTable();

            return; // salir para no ejecutar el bloque de eliminar
        }

        // 2) ELIMINAR (tu código ya existente)
        const btn = e.target.closest(".btn-delete");
        if (!btn) return;

        const index = parseInt(btn.getAttribute("data-index"));

        Swal.fire({
            title: "¿Estás seguro?",
            text: "¡No podrás revertir esto!",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            cancelButtonText: "Cancelar",
            confirmButtonText: "Sí, ¡eliminalo!",
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        }).then((result) => {
            if (result.isConfirmed) {
                favoritos.splice(index, 1);
                localStorage.setItem("favoritos", JSON.stringify(favoritos));

                if ((currentPage - 1) * rowsPerPage >= favoritos.length && currentPage > 1) {
                    currentPage--;
                }

                renderTable();

                Swal.fire({
                    title: "¡Eliminado!",
                    text: "El producto ha sido eliminado.",
                    icon: "success",
                    customClass: {
                        title: 'swal-title',
                        popup: 'swal-popup'
                    }
                });
            }
        });
    });



});