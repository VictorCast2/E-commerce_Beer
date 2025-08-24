
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

Object.keys(fields).forEach(fieldId => {
    const input = document.getElementById(fieldId);
    const inputBox = input.closest(".input-box");
    const checkIcon = inputBox.querySelector(".ri-check-line");
    const errorIcon = inputBox.querySelector(".ri-close-line");
    const errorMessage = inputBox.nextElementSibling;

    input.addEventListener("input", () => {
        const value = input.value.trim();

        if (value === "") {
            // Si el input está vacío, restablecer estilos
            inputBox.classList.remove("input-error");
            checkIcon.style.display = "none";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "";
        } else if (fields[fieldId].regex.test(value)) {
            // Si es válido
            checkIcon.style.display = "inline-block";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "2px solid #0034de";
            inputBox.classList.remove("input-error");
        } else {
            // Si es inválido
            checkIcon.style.display = "none";
            errorIcon.style.display = "inline-block";
            errorMessage.style.display = "block";
            input.style.border = "2px solid #fd1f1f";
            inputBox.classList.add("input-error");
        }
    });
});

const form = document.querySelector("form");
const errorMessageBox = document.querySelector(".input__advertencia");
const errorMessages = document.querySelectorAll(".input__error");
const inputs = document.querySelectorAll("input:not([type='checkbox'])"); // Todos los inputs excepto el checkbox
const checkbox = document.querySelector(".remember-forgot input"); // Selecciona el checkbox

form.addEventListener("submit", function (event) {
    let formValid = true;

    // Verificar si algún campo está vacío o inválido
    inputs.forEach(input => {
        const value = input.value.trim();
        if (value === "" || !fields[input.id].regex.test(value)) {
            formValid = false;
        }
    });

    // Verificar si el checkbox está marcado
    if (!checkbox.checked) {
        formValid = false;
    }

    if (!formValid) {
        // Si el formulario no es válido, mostrar el mensaje de advertencia
        errorMessageBox.style.display = "block";
        event.preventDefault(); // Evitar el envío del formulario
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
            title: "Inicio de sesión exitoso",
            icon: "success",
            timer: 3000,
            draggable: true,
            timerProgressBar: true,
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        });
        //Eliminamos la bandera para que no vuelva a salir al refrescar
        sessionStorage.removeItem("loginSuccess");
    }
});


// Ocultar el mensaje de advertencia cuando el usuario empieza a escribir o marca el checkbox
inputs.forEach(input => {
    input.addEventListener("input", function () {
        errorMessageBox.style.display = "none";
    });
});

checkbox.addEventListener("change", function () {
    errorMessageBox.style.display = "none";
});