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

Object.keys(fields).forEach(fieldId => {
    const input = document.getElementById(fieldId);
    if (!input) return;

    const inputBox = input.closest(".input-box");
    const checkIcon = inputBox.querySelector(".ri-check-line");
    const errorIcon = inputBox.querySelector(".ri-close-line");
    const errorMessage = inputBox.nextElementSibling;
    const label = inputBox.querySelector("label");

    input.addEventListener("input", () => {
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
});



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
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor rellene el formulario correctamente",
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        });
        tipoIdentificacionError.style.display = "none";
        tipoIdentificacionSelect.style.border = "";
        event.preventDefault();
        return;
    }

    // Caso: solo el select está vacío
    if (tipoIdentificacionInvalido && emptyCount === 0 && checkbox.checked) {
        tipoIdentificacionError.style.display = "block";
        tipoIdentificacionSelect.style.border = "2px solid #fd1f1f";
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
        // El formulario se envía normalmente
    }
});

// Al cargar la página, revisamos si hay bandera de login
window.addEventListener("DOMContentLoaded", () => {
    if (sessionStorage.getItem("loginSuccess") === "true") {
        Swal.fire({
            title: "Registro exitoso",
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

const passwordInput = document.getElementById("password");
const repeatPasswordInput = document.getElementById("repeatPassword");
passwordInput.addEventListener("input", () => {
    repeatPasswordInput.dispatchEvent(new Event("input"));
});


//abri el formulario de completar empresa
const abirmodal = document.getElementById("formulario__complete");
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


//validaciones de completar de empresas
const fieldsEmpresa = {
    phone: { regex: /^\d{1,10}$/, errorMessage: "El teléfono solo puede contener números (máx. 10)." },
    correo: { regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, errorMessage: "El correo solo puede contener letras,numeros,puntos,guiones y guion bajo." },
    nit: { regex: /^\d{9,10}$/, errorMessage: "El NIT debe tener entre 9 y 10 dígitos y solo contener números." },
    razonsocial: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ0-9&.,\- ]{3,100}$/, errorMessage: "La Razón Social debe tener entre 3 y 100 caracteres" },
    direccion: { regex: /^(?=.*\d)(?=.*[A-Za-z])[A-Za-z0-9\s#.,-]{5,}$/, errorMessage: "Ingresa una dirección válida (ej. Calle 45 #10-23, 130002 o San Fernando, Calle 45 #10-23, 130002)." },
    sector: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ ]{3,50}$/, errorMessage: "El sector debe tener entre 3 y 50 caracteres y solo contener letras y espacios" }
};

Object.keys(fieldsEmpresa).forEach(fieldId => {
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
        } else if (fieldsEmpresa[fieldId].regex.test(value)) {
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

const addform = document.querySelector(".add__formulario");
const inputs2 = addform.querySelectorAll("input");

addform.addEventListener("submit", function (event) {
    let formValid = true;

    inputs2.forEach(input => {
        const value = input.value.trim();
        if (value === "" || !fieldsEmpresa[input.id].regex.test(value)) {
            formValid = false;
        }
    });

    if (!formValid) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor rellene el formulario correctamente",
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            },
            footer: '<a href="#">Why do I have this issue?</a>'
        });

        event.preventDefault();
    } else {
        sessionStorage.setItem("loginSuccess", "true");
        // El formulario se envía normalmente
    }
});
