document.addEventListener("DOMContentLoaded", function () {

    //Interaccion con el icono del select
    const select = document.getElementById("tipoIdentificacion");
    const arrow = document.getElementById("arrowIcon");

    let isOpen = false;

    select.addEventListener("mousedown", (e) => {
        // Esta parte previene un fallo visual si el usuario hace clic muchas veces
        isOpen = !isOpen;
        arrow.classList.toggle("rotate", isOpen);
    });

    select.addEventListener("blur", () => {
        isOpen = false;
        arrow.classList.remove("rotate");
    });

    //Validaciones con javascript
    const fields = {
        nombre: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El nombre debe tener al menos 3 letras." },
        apellido: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El apellido debe tener al menos 3 letras." },
        identificacion: { regex: /^\d{6,10}$/, errorMessage: "La cédula debe contener entre 6 y 10 dígitos." },
        telefono: { regex: /^\d{1,10}$/, errorMessage: "El teléfono solo puede contener números (máx. 10)." },
        email: { regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, errorMessage: "El correo solo puede contener letras,numeros,puntos,guiones y guion bajo." },
        password: { regex: /^.{4,12}$/, errorMessage: "La contraseña debe tener entre 4 y 12 caracteres." },
        repeatPassword: { regex: null, errorMessage: "Las contraseñas no coinciden." }
    };

    const tipoIdentificacionSelect = document.getElementById("tipoIdentificacion");
    const advertencia = document.querySelector(".input__advertencia");

    Object.keys(fields).forEach(fieldId => {
        const input = document.getElementById(fieldId);
        if (!input) return;

        const inputBox = input.closest(".input-box");
        const checkIcon = inputBox.querySelector(".ri-check-line");
        const errorIcon = inputBox.querySelector(".ri-close-line");
        const errorMessage = inputBox.nextElementSibling;
        const label = inputBox.querySelector("label");

        input.addEventListener("input", () => {
            hideAdvertencia();
            const value = input.value.trim();

            if (value === "") {
                checkIcon.style.display = "none";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else if (fieldId === "repeatPassword") {
                const passwordValue = document.getElementById("password").value.trim();
                if (value === passwordValue) {
                    checkIcon.style.display = "inline-block";
                    errorIcon.style.display = "none";
                    errorMessage.style.display = "none";
                    input.style.border = "2px solid #0034de";
                    label.style.color = "";
                    inputBox.classList.remove("input-error");
                } else {
                    checkIcon.style.display = "none";
                    errorIcon.style.display = "inline-block";
                    errorMessage.style.display = "block";
                    input.style.border = "2px solid #fd1f1f";
                    label.style.color = "red";
                    inputBox.classList.add("input-error");
                }
            } else if (fields[fieldId].regex.test(value)) {
                checkIcon.style.display = "inline-block";
                errorIcon.style.display = "none";
                errorMessage.style.display = "none";
                input.style.border = "2px solid #0034de";
                label.style.color = "";
                inputBox.classList.remove("input-error");
            } else {
                checkIcon.style.display = "none";
                errorIcon.style.display = "inline-block";
                errorMessage.style.display = "block";
                input.style.border = "2px solid #fd1f1f";
                label.style.color = "red";
                inputBox.classList.add("input-error");
            }
        });
    });

    const form = document.querySelector("form");
    const inputs = document.querySelectorAll("input:not([type='checkbox'])");
    const checkbox = document.querySelector(".remember-forgot input");
    const tipoIdentificacionError = tipoIdentificacionSelect.closest(".formulario__input").querySelector(".input__error");

    // Validación del select: ocultar error al seleccionar opción válida
    tipoIdentificacionSelect.addEventListener("change", () => {
        if (tipoIdentificacionSelect.selectedIndex !== 0) {
            tipoIdentificacionError.style.display = "none";
            tipoIdentificacionSelect.style.border = "2px solid #0034de";
        }
        hideAdvertencia();
    });


    [...inputs, tipoIdentificacionSelect].forEach(el => {
        el.addEventListener("input", hideAdvertencia);
        el.addEventListener("change", hideAdvertencia);
    });

    checkbox.addEventListener("change", hideAdvertencia);

    form.addEventListener("submit", function (event) {
        let valid = true;
        let emptyCount = 0;
        let tipoIdentificacionInvalido = tipoIdentificacionSelect.selectedIndex === 0;

        Object.keys(fields).forEach(fieldId => {
            const input = document.getElementById(fieldId);
            const value = input.value.trim();

            if (value === "") {
                emptyCount++;
                valid = false;
            } else if (fieldId === "repeatPassword") {
                const passwordValue = document.getElementById("password").value.trim();
                if (value !== passwordValue) {
                    valid = false;
                }
            } else if (!fields[fieldId].regex.test(value)) {
                valid = false;
            }
        });

        // Caso: todos los campos vacíos y checkbox no marcado
        const allFieldsEmpty = emptyCount === Object.keys(fields).length && tipoIdentificacionInvalido && !checkbox.checked;

        if (allFieldsEmpty) {
            advertencia.style.display = "block";
            tipoIdentificacionError.style.display = "none";
            tipoIdentificacionSelect.style.border = "";
            event.preventDefault();
            return;
        }

        // Caso: solo el select está vacío
        if (tipoIdentificacionInvalido && emptyCount === 0 && checkbox.checked) {
            tipoIdentificacionError.style.display = "block";
            tipoIdentificacionSelect.style.border = "2px solid #fd1f1f";
            advertencia.style.display = "flex";
            event.preventDefault();
            return;
        }

        // Validar tipoIdentificacion si es inválido
        if (tipoIdentificacionInvalido) {
            tipoIdentificacionError.style.display = "none";
            tipoIdentificacionSelect.style.border = "";
            valid = false;
        } else {
            tipoIdentificacionError.style.display = "none";
            tipoIdentificacionSelect.style.border = "2px solid #0034de";
        }

        // Validar checkbox
        if (!checkbox.checked) {
            valid = false;
        }

        // Si hay otros errores
        if (!valid) {
            advertencia.style.display = "block";
            event.preventDefault();
        } else {
            // Si el formulario es válido, mostrar el modal de éxito
            openModal();
            event.preventDefault(); // Evitar el envío real hasta que el modal se cierre
            setTimeout(function() {
                form.submit(); // Enviar el formulario después de un tiempo (simulando un retardo de éxito)
            }, 2000); // Ajusta el tiempo según necesites
        }
    });

    const passwordInput = document.getElementById("password");
    const repeatPasswordInput = document.getElementById("repeatPassword");
    passwordInput.addEventListener("input", () => {
        repeatPasswordInput.dispatchEvent(new Event("input"));
    });

    function hideAdvertencia() {
        advertencia.style.display = "none";
    }

    // Función para abrir el modal
    function openModal() {
        const modal = document.getElementById("modalSuccess");
        modal.style.display = "block"; // Mostrar el modal
    }

    // Función para cerrar el modal
    function closeModal() {
        const modal = document.getElementById("modalSuccess");
        modal.style.display = "none"; // Ocultar el modal
    }

});

