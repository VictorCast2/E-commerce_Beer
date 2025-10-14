export function glassmorphismDashboard() {
    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.content__header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });
}


export function menuDesplegableDashboard() {
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
}

export function abrirNotificacionesAdmin() {
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
}

document.addEventListener("DOMContentLoaded", () => {
    // Efecto glassmorphism solo al hacer scroll
    glassmorphismDashboard();

    //Menú desplegable del perfil
    menuDesplegableDashboard();

    //abrir la notificaciones del admin
    abrirNotificacionesAdmin();

    // === FILTRO DE PRODUCTOS (por texto y estado) ===
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
                const coincideEstado = estadoSeleccionado === "todos" || estado.includes(estadoSeleccionado);

                row.hidden = !(coincideTexto && coincideEstado);
            });
        };

        // Mejor rendimiento con "input" y "change"
        searchInput.addEventListener('input', filtrarTabla);
        stateSelect.addEventListener('change', filtrarTabla);
    })();

    //abrir para mostrar editar,eliminar y ocultar
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
});
