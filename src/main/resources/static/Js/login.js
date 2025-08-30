// --- Manejo del modal ---
function openModal(message, redirect = null) {
    const modal = document.getElementById("modalSuccess");
    if (modal) {
        const h2 = modal.querySelector("h2");
        if (message) h2.textContent = message; // Cambiar texto si se pasa
        modal.style.display = "block";

        // Redirigir si corresponde
        if (redirect) {
            setTimeout(() => window.location.href = redirect, 2000);
        }
    }
}

function closeModal() {
    const modal = document.getElementById("modalSuccess");
    if (modal) modal.style.display = "none";
}

// Mostrar modal si hay logout
window.addEventListener("load", () => {
    const urlParams = new URLSearchParams(window.location.search);
    if (urlParams.has("logout")) {
        openModal("Has cerrado sesión correctamente");
        setTimeout(closeModal, 4000);
    }
});

// --- Validaciones ---
const fields = {
    email: {
        regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/,
        errorMessage: "El correo solo puede contener letras, números, puntos, guiones y guion bajo."
    },
    password: {
        regex: /^.{4,15}$/,
        errorMessage: "La contraseña tiene que ser de 4 a 15 dígitos."
    }
};

const form = document.querySelector("form");
const errorMessageBox = document.querySelector(".input__advertencia");
const inputs = document.querySelectorAll("input:not([type='checkbox'])");
const checkbox = document.querySelector(".remember-forgot input");

Object.keys(fields).forEach(fieldId => {
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
        } else if (fields[fieldId].regex.test(value)) {
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

// Validar envío
form.addEventListener("submit", function (event) {
    let formValid = true;

    inputs.forEach(input => {
        const value = input.value.trim();
        if (value === "" || !fields[input.id].regex.test(value)) {
            formValid = false;
        }
    });
    if (!checkbox.checked) formValid = false;

    if (!formValid) {
        errorMessageBox.style.display = "block";
        event.preventDefault();
    } else {
        event.preventDefault(); // detener envío
        openModal("Inicio de sesión exitoso", "/buques/");
    }
});

// Ocultar advertencia al escribir o marcar checkbox
inputs.forEach(input => input.addEventListener("input", () => {
    errorMessageBox.style.display = "none";
}));
checkbox.addEventListener("change", () => errorMessageBox.style.display = "none");

// --- Ocultar mensaje de error del backend ---
setTimeout(() => {
    const MENSAJE_PASSWORD_ERROR = document.getElementById('mensaje-password-error');
    if (MENSAJE_PASSWORD_ERROR) MENSAJE_PASSWORD_ERROR.style.display = 'none';
