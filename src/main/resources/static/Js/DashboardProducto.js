document.addEventListener("DOMContentLoaded", () => {
    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.content__header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

    //Menú desplegable del perfil
    const subMenu = document.getElementById("SubMenu");
    const profileImage = document.getElementById("user__admin");

    if (subMenu && profileImage) {
        profileImage.addEventListener("click", function (e) {
            e.stopPropagation(); // Evita que el click cierre el menú inmediatamente
            subMenu.classList.toggle("open__menu");
        });

        // Cerrar menú al hacer clic fuera
        document.addEventListener("click", function (e) {
            if (!subMenu.contains(e.target) && !profileImage.contains(e.target)) {
                subMenu.classList.remove("open__menu");
            }
        });
    }

    //abrir la notificaciones del admin
    const notifIcon = document.getElementById("notifIcon");
    const notifMenu = document.getElementById("notifMenu");

    notifIcon.addEventListener("click", (e) => {
        e.stopPropagation();
        notifMenu.classList.toggle("open");
    });

    // cerrar al hacer clic fuera
    document.addEventListener("click", (e) => {
        if (!notifMenu.contains(e.target) && !notifIcon.contains(e.target)) {
            notifMenu.classList.remove("open");
        }
    });

    //FILTRO DE PRODUCTOS (por texto y estado)
    (() => {
        const searchInput = document.getElementById("inputSearch");
        const stateSelect = document.getElementById("filtro-estado");
        const tableBody = document.getElementById("favoritos-body");

        if (!searchInput || !stateSelect || !tableBody) return; // Evita errores si falta algo

        const tableRows = Array.from(tableBody.rows); // Más rápido que querySelectorAll('tr')

        const filtrarTabla = () => {
            const texto = searchInput.value.trim().toLowerCase();
            const estadoSeleccionado = stateSelect.value.trim().toLowerCase();

            tableRows.forEach(row => {
                const nombre = row.querySelector('.td__product')?.textContent.toLowerCase() || '';
                const estado = row.querySelector('.td__estados')?.textContent.toLowerCase() || '';

                const coincideTexto = !texto || nombre.includes(texto);
                const coincideEstado = estadoSeleccionado === "todos" || estado === estadoSeleccionado;

                row.hidden = !(coincideTexto && coincideEstado);
            });
        };

        // Mejor rendimiento con "input" y "change"
        searchInput.addEventListener('input', filtrarTabla);
        stateSelect.addEventListener('change', filtrarTabla);
    })();

    //abrir para mostrar editar,eliminar y ocultar
    function marcarUltimosDosMenus() {
        // Seleccionamos el tbody de la tabla (por su id)
        const tbody = document.querySelector("#favoritos-body");
        if (!tbody) return;

        // Seleccionamos todos los td con la clase menu__actions dentro del tbody
        const celdasMenu = tbody.querySelectorAll("td.menu__actions");

        // Primero, limpiamos cualquier clase 'up' previa
        celdasMenu.forEach(td => {
            const menu = td.querySelector(".dropdown__menu");
            if (menu) menu.classList.remove("up");
        });

        // Tomamos los últimos 2 <td>
        const ultimosDos = Array.from(celdasMenu).slice(-2);

        // Les aplicamos la clase 'up' a sus menús
        ultimosDos.forEach(td => {
            const menu = td.querySelector(".dropdown__menu");
            if (menu) menu.classList.add("up");
        });
    }

    // Llamamos la función cuando carga la página
    marcarUltimosDosMenus();

    // Observamos cambios en el tbody (por si se actualiza dinámicamente)
    const tbody = document.querySelector("#favoritos-body");
    if (tbody) {
        const observer = new MutationObserver(() => {
            marcarUltimosDosMenus();
        });
        observer.observe(tbody, { childList: true, subtree: true });
    }

    // Manejar abrir/cerrar los menús
    document.addEventListener("click", function (e) {
        const dropdowns = document.querySelectorAll(".dropdown");
        dropdowns.forEach((dropdown) => {
            const toggle = dropdown.querySelector(".dropdown__toggle");
            const menu = dropdown.querySelector(".dropdown__menu");

            if (toggle.contains(e.target)) {
                dropdown.classList.toggle("show");
            } else {
                dropdown.classList.remove("show");
            }
        });
    });

    // ---- Modales con SweetAlert2 ----
    document.addEventListener("click", (e) => {
        // === ELIMINAR ===
        if (e.target.closest(".eliminar")) {
            Swal.fire({
                title: "¿Estás seguro?",
                text: "Esta acción eliminará el registro permanentemente.",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#d33",
                cancelButtonColor: "#3085d6",
                confirmButtonText: "Sí, eliminar",
                cancelButtonText: "Cancelar",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Eliminado",
                        text: "El registro ha sido eliminado exitosamente.",
                        icon: "success",
                        timer: 1500,
                        showConfirmButton: false,
                        customClass: {
                            title: 'swal-title',
                            popup: 'swal-popup'
                        }
                    });

                }
            });
        }

        // === EDITAR ===
        else if (e.target.closest(".editar")) {
            Swal.fire({
                title: "¿Estás seguro?",
                text: "¿Deseas editar este registro?",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#6c757d",
                confirmButtonText: "Sí, editar",
                cancelButtonText: "Cancelar",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Editado",
                        text: "El registro está listo para ser editado.",
                        icon: "success",
                        timer: 1500,
                        showConfirmButton: false,
                        customClass: {
                            title: 'swal-title',
                            popup: 'swal-popup'
                        }
                    });

                }
            });
        }

        // === OCULTAR ===
        else if (e.target.closest(".ocultar")) {
            Swal.fire({
                title: "¿Deseas ocultar este registro?",
                text: "Podrás mostrarlo nuevamente desde la sección de registros ocultos.",
                icon: "question",
                showCancelButton: true,
                confirmButtonText: "Sí, ocultar",
                cancelButtonText: "Cancelar",
                confirmButtonColor: "#6c757d",
                cancelButtonColor: "#3085d6",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Ocultado",
                        text: "El registro ha sido ocultado correctamente.",
                        icon: "info",
                        timer: 1500,
                        showConfirmButton: false,
                        customClass: {
                            title: 'swal-title',
                            popup: 'swal-popup'
                        }
                    });

                }
            });
        }
    });

    //pagination de la tabla
    /* const filasPorPagina = 12; // Máximo de registros por página
    const tablaBody = document.getElementById("favoritos-body");
    const filas = tablaBody.querySelectorAll("tr");
    const totalFilas = filas.length;
    const totalPaginas = Math.ceil(totalFilas / filasPorPagina);

    const contenedorBotones = document.querySelector(".pagination__button");
    const textoPaginacion = document.querySelector(".span__description");

    let paginaActual = 1;

    // === Función para mostrar las filas según la página seleccionada ===
    function mostrarPagina(pagina) {
        const inicio = (pagina - 1) * filasPorPagina;
        const fin = inicio + filasPorPagina;

        filas.forEach((fila, index) => {
            fila.style.display = (index >= inicio && index < fin) ? "" : "none";
        });

        // Actualiza el texto "Mostrando del X al Y de Z entradas"
        const inicioTexto = inicio + 1;
        const finTexto = Math.min(fin, totalFilas);
        textoPaginacion.textContent = `Mostrando del ${inicioTexto} al ${finTexto} de ${totalFilas} entradas`;

        // Actualiza botones activos
        const botonesPagina = contenedorBotones.querySelectorAll(".button__item");
        botonesPagina.forEach((btn, i) => {
            btn.classList.toggle("active", i + 1 === pagina);
        });

        // Control de botones "Anterior" y "Siguiente"
        document.querySelector(".pasar--anterior").disabled = pagina === 1;
        document.querySelector(".pasar--siguiente").disabled = pagina === totalPaginas;
    }

    // === Crear botones de número de página dinámicamente ===
    function crearBotones() {
        const anterior = contenedorBotones.querySelector(".pasar--anterior");
        const siguiente = contenedorBotones.querySelector(".pasar--siguiente");

        // Elimina los botones numéricos existentes
        contenedorBotones.querySelectorAll(".button__item").forEach(btn => btn.remove());

        // Agrega nuevos según el total de páginas
        for (let i = 1; i <= totalPaginas; i++) {
            const btn = document.createElement("button");
            btn.classList.add("button__item");
            btn.textContent = i;

            btn.addEventListener("click", () => {
                paginaActual = i;
                mostrarPagina(paginaActual);
            });

            siguiente.before(btn);
        }
    }

    // === Eventos de navegación Anterior / Siguiente ===
    document.querySelector(".pasar--anterior").addEventListener("click", () => {
        if (paginaActual > 1) {
            paginaActual--;
            mostrarPagina(paginaActual);
        }
    });

    document.querySelector(".pasar--siguiente").addEventListener("click", () => {
        if (paginaActual < totalPaginas) {
            paginaActual++;
            mostrarPagina(paginaActual);
        }
    });

    // Inicializa la paginación
    crearBotones();
    mostrarPagina(paginaActual); */

});



