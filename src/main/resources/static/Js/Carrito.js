import { activarGlassmorphism, inicialHeart, initCart, rederigirFav } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {

    /* console.log("✅ JS cargado correctamente");
    console.log("favTableBody:", favTableBody);
    console.log("prevPageBtn:", prevPageBtn);
    console.log("nextPageBtn:", nextPageBtn); */

    // Usar la funcion activarGlassmorphism
    activarGlassmorphism();

    inicialHeart();

    initCart();

    //rellenar la tabla mediante localStogare 
    const cartTableBody = document.getElementById("favoritos-body");
    const paginationText = document.getElementById("pagination-text");
    const prevPageBtn = document.getElementById("prev-page");
    const nextPageBtn = document.getElementById("next-page");
    const paginationButtonsContainer = document.getElementById("pagination-buttons");
    const thereSpan = document.getElementById("there?");
    const totalEl = document.getElementById("content__precio");

    let cart = JSON.parse(localStorage.getItem("cart")) || [];
    let currentPage = 1;
    const rowsPerPage = 4;

    // --- Calcular total del carrito ---
    function updateCartTotal() {
        const total = cart.reduce((acc, item) => acc + (item.price * item.qty), 0);
        totalEl.textContent = `$${total.toFixed(2)}`;
    }

    // --- Render tabla ---
    function renderTable() {
        cartTableBody.innerHTML = "";

        if (cart.length === 0) {
            const tr = document.createElement("tr");
            tr.innerHTML = `
        <td colspan="6" style="text-align:center; padding:20px; font-weight:600;">
            No hay productos en el carrito
        </td>
    `;
            cartTableBody.appendChild(tr);

            paginationText.textContent = `Mostrando 0-0 de 0`;
            paginationButtonsContainer.innerHTML = "";
            prevPageBtn.disabled = true;
            nextPageBtn.disabled = true;
            thereSpan.textContent = `Hay 0 productos en el carrito.`;

            // Si no hay productos, el total debe ser 0
            updateCartTotal();
            return;
        }

        const start = (currentPage - 1) * rowsPerPage;
        const end = start + rowsPerPage;
        const paginatedItems = cart.slice(start, end);

        paginatedItems.forEach((item, index) => {
            const globalIndex = start + index; // posición real en el array
            const subtotal = item.price * item.qty;

            const tr = document.createElement("tr");
            tr.innerHTML = `
        <td><img src="${item.img}" width="50"/></td>
        <td>${item.name}</td>
        <td>
            <div class="quantity-control" data-index="${globalIndex}">
                <button class="minus">−</button>
                <span class="qty">${item.qty}</span>
                <button class="plus">+</button>
            </div>
        </td>
        <td>$${item.price.toFixed(2)}</td>
        <td>$${subtotal.toFixed(2)}</td>
        <td>
            <button class="content__icon btn-delete" data-index="${globalIndex}">
                <i class="ri-delete-bin-6-line" title="Eliminar"></i>
            </button>
        </td>
    `;
            cartTableBody.appendChild(tr);
        });

        paginationText.textContent = `Mostrando ${start + 1}-${Math.min(end, cart.length)} de ${cart.length}`;
        thereSpan.textContent = `Hay ${cart.length} productos en el carrito.`;

        renderPaginationButtons();

        // 🔥 Actualizar el total del carrito
        updateCartTotal();
    }

    // --- Render botones de paginación ---
    function renderPaginationButtons() {
        paginationButtonsContainer.innerHTML = "";
        const totalPages = Math.ceil(cart.length / rowsPerPage);

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

    // --- Eventos flechas ---
    prevPageBtn.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            renderTable();
        }
    });

    nextPageBtn.addEventListener("click", () => {
        if (currentPage < Math.ceil(cart.length / rowsPerPage)) {
            currentPage++;
            renderTable();
        }
    });

    // --- Delegación: eliminar + actualizar cantidad ---
    cartTableBody.addEventListener("click", (e) => {
        // Eliminar producto
        const btn = e.target.closest(".btn-delete");
        if (btn) {
            const index = parseInt(btn.getAttribute("data-index"));
            Swal.fire({
                title: "¿Estás seguro?",
                text: "¡No podrás revertir esto!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                cancelButtonText: "Cancelar",
                confirmButtonText: "Sí, eliminar",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            }).then((result) => {
                if (result.isConfirmed) {
                    cart.splice(index, 1);
                    localStorage.setItem("cart", JSON.stringify(cart));

                    if ((currentPage - 1) * rowsPerPage >= cart.length && currentPage > 1) {
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
            return;
        }

        // Aumentar / disminuir cantidad
        if (e.target.classList.contains("plus") || e.target.classList.contains("minus")) {
            const control = e.target.closest(".quantity-control");
            const index = parseInt(control.getAttribute("data-index"));

            if (e.target.classList.contains("plus")) {
                cart[index].qty++;
            } else if (e.target.classList.contains("minus") && cart[index].qty > 1) {
                cart[index].qty--;
            }

            localStorage.setItem("cart", JSON.stringify(cart));
            renderTable();
        }
    });

    // --- Render inicial ---
    renderTable();

    //rederigir a Favorito
    rederigirFav();

    //abrir el formulario de envio y pago
    const abirmodal = document.getElementById("btn__pagaryenviar");
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

    const departamentos = {
        "Amazonas": ["Leticia", "Puerto Nariño"],
        "Antioquia": ["Medellín", "Bello", "Itagüí", "Envigado", "Rionegro", "Turbo"],
        "Arauca": ["Arauca", "Saravena", "Tame"],
        "Atlántico": ["Barranquilla", "Soledad", "Malambo", "Sabanalarga"],
        "Bolívar": ["Cartagena", "Magangué", "Turbaco", "Arjona"],
        "Boyacá": ["Tunja", "Duitama", "Sogamoso", "Chiquinquirá"],
        "Caldas": ["Manizales", "La Dorada", "Chinchiná"],
        "Caquetá": ["Florencia", "San Vicente del Caguán"],
        "Casanare": ["Yopal", "Aguazul", "Villanueva"],
        "Cauca": ["Popayán", "Santander de Quilichao"],
        "Cesar": ["Valledupar", "Aguachica", "Codazzi"],
        "Chocó": ["Quibdó", "Istmina"],
        "Córdoba": ["Montería", "Lorica", "Cereté"],
        "Cundinamarca": ["Bogotá", "Soacha", "Zipaquirá", "Girardot", "Facatativá"],
        "Guainía": ["Inírida"],
        "Guaviare": ["San José del Guaviare"],
        "Huila": ["Neiva", "Pitalito", "Garzón"],
        "La Guajira": ["Riohacha", "Maicao", "Uribia"],
        "Magdalena": ["Santa Marta", "Ciénaga", "Fundación"],
        "Meta": ["Villavicencio", "Acacías", "Granada"],
        "Nariño": ["Pasto", "Ipiales", "Tumaco"],
        "Norte de Santander": ["Cúcuta", "Ocaña", "Pamplona"],
        "Putumayo": ["Mocoa", "Puerto Asís", "Orito"],
        "Quindío": ["Armenia", "Calarcá", "Montenegro"],
        "Risaralda": ["Pereira", "Dosquebradas", "Santa Rosa de Cabal"],
        "San Andrés y Providencia": ["San Andrés", "Providencia"],
        "Santander": ["Bucaramanga", "Floridablanca", "Girón", "Barrancabermeja", "Piedecuesta"],
        "Sucre": ["Sincelejo", "Corozal", "Sampués"],
        "Tolima": ["Ibagué", "Espinal", "Melgar"],
        "Valle del Cauca": ["Cali", "Palmira", "Buenaventura", "Tuluá", "Buga", "Cartago", "Yumbo"],
        "Vaupés": ["Mitú"],
        "Vichada": ["Puerto Carreño"]
    };

    // Llenar departamentos
    const departamentoSelect = document.getElementById("Departamento");
    const ciudadSelect = document.getElementById("Ciudad");

    Object.keys(departamentos).forEach(dep => {
        let option = document.createElement("option");
        option.value = dep;
        option.textContent = dep;
        departamentoSelect.appendChild(option);
    });

    // Cambiar ciudades cuando se elige un departamento
    departamentoSelect.addEventListener("change", function () {
        ciudadSelect.innerHTML = "<option disabled selected>Ciudad</option>";
        const ciudades = departamentos[this.value];
        ciudades.forEach(ciudad => {
            let option = document.createElement("option");
            option.value = ciudad;
            option.textContent = ciudad;
            ciudadSelect.appendChild(option);
        });
    });

    // Validaciones del formulario 
    const fieldsEnvio = {
        direccion: {
            regex: /^(?=.*\d)(?=.*[A-Za-z])[A-Za-z0-9\s#.,-]{5,}$/,
            errorMessage: "Ingresa una dirección válida (ej. Calle 45 #10-23, 130002 o San Fernando, Calle 45 #10-23, 130002)."
        },
        Direcciónadicional: {
            regex: /^(Apartamento|Casa|Piso|Torre|Bloque)\s?[0-9]{1,4}[A-Za-z]?$/i,
            errorMessage: "Ingresa un complemento válido (ej. Apartamento 302, Casa 5, Piso 2)."
        },
        barrio: {
            regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9]+(?:[ .-][A-Za-zÁÉÍÓÚáéíóúÑñ0-9]+)*$/,
            errorMessage: "Ingresa un barrio válido (ej. San Fernando, El Prado, 12 De Julio)."
        }
    };

    const form = document.querySelector(".formulario");

    // Validar en tiempo real los inputs
    Object.keys(fieldsEnvio).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;

        input.addEventListener("input", () => {
            const value = input.value.trim();

            if (value === "") {
                inputBox.classList.remove("input-error");
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
            } else if (fieldsEnvio[fieldId].regex.test(value)) {
                checkIcon.style.display = "inline-block";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "2px solid #0034de";
                inputBox.classList.remove("input-error");
            } else {
                checkIcon.style.display = "none";
                errorIcon.style.display = "inline-block";
                errorMessage.style.display = "block";
                input.style.border = "2px solid #fd1f1f";
                inputBox.classList.add("input-error");
            }
        });
    });

    // Selects
    const selectDepartamento = document.querySelector("#Departamento");
    const errorDepartamento = document.querySelector(".error--departamento");
    const selectCiudad = document.querySelector("#Ciudad");
    const errorCiudad = document.querySelector(".error--ciudad");


    // Ocultar advertencias y errores de select al interactuar
    [selectDepartamento, selectCiudad].forEach(select => {
        select.addEventListener("change", () => {

            if (select.selectedIndex > 0) {
                select.style.border = "2px solid #0034de";
            } else {
                select.style.border = "";
            }

            if (select === selectDepartamento && select.selectedIndex > 0) {
                errorDepartamento.style.display = "none";
            }
            if (select === selectCiudad && select.selectedIndex > 0) {
                errorCiudad.style.display = "none";
            }

        });
    });

    // Validación al enviar
    form.addEventListener("submit", function (event) {
        let valid = true;

        // Validar inputs
        Object.keys(fieldsEnvio).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            if (!fieldsEnvio[fieldId].regex.test(input.value.trim())) {
                valid = false;
            }
        });

        const departamentoSeleccionada = selectDepartamento.selectedIndex > 0;
        const ciudadeleccionada = selectCiudad.selectedIndex > 0;

        // Caso: Inputs válidos, pero selects incompletos
        if (valid && (!departamentoSeleccionada || !ciudadeleccionada)) {
            if (!departamentoSeleccionada) {
                errorDepartamento.style.display = "block";
                selectDepartamento.style.border = "2px solid #fd1f1f";
            }
            if (!ciudadeleccionada) {
                errorCiudad.style.display = "block";
                selectCiudad.style.border = "2px solid #fd1f1f";
            }

            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Por favor rellene el formulario correctamente",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            });
            event.preventDefault(); 
            return;
        }

        if (!valid) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Por favor rellene el formulario correctamente",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            });
            event.preventDefault();
        } else {
            // Guardamos bandera en sessionStorage
            sessionStorage.setItem("loginSuccess", "true");
        }
    });

    // Mostrar mensaje al cargar si se registró correctamente
    window.addEventListener("DOMContentLoaded", () => {
        if (sessionStorage.getItem("loginSuccess") === "true") {
            Swal.fire({
                title: "Envio rellenado exitosamente",
                icon: "success",
                timer: 3000,
                draggable: true,
                timerProgressBar: true,
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            });
            sessionStorage.removeItem("loginSuccess");
        }
    });




});