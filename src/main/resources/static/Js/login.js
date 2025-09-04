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

// Validación dinámica de inputs
Object.keys(fields).forEach(fieldId => {
    const input = document.getElementById(fieldId);
    const inputBox = input.closest(".input-box");
    const checkIcon = inputBox.querySelector(".ri-check-line");
    const errorIcon = inputBox.querySelector(".ri-close-line");
    const errorMessage = inputBox.nextElementSibling;

    input.addEventListener("input", () => {
        const value = input.value.trim();

        if (value === "") {
            // Resetear estilos si está vacío
            inputBox.classList.remove("input-error");
            checkIcon.style.display = "none";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "";
        } else if (fields[fieldId].regex.test(value)) {
            // Válido
            checkIcon.style.display = "inline-block";
            errorIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "2px solid #0034de";
            inputBox.classList.remove("input-error");
        } else {
            // Inválido
            checkIcon.style.display = "none";
            errorIcon.style.display = "inline-block";
            errorMessage.style.display = "block";
            input.style.border = "2px solid #fd1f1f";
            inputBox.classList.add("input-error");
        }
    });
});

const form = document.querySelector("form");
const inputs = document.querySelectorAll("input:not([type='checkbox'])");
const checkbox = document.querySelector(".remember-forgot input");

// Validación antes de enviar
form.addEventListener("submit", function (event) {
    let formValid = true;

    inputs.forEach(input => {
        const value = input.value.trim();
        if (value === "" || !fields[input.id].regex.test(value)) {
            formValid = false;
        }
    });

    if (!checkbox.checked) {
        formValid = false;
    }

    if (!formValid) {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "Por favor rellene el formulario correctamente",
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        });
        event.preventDefault(); // Evita enviar si hay errores
    }
});

// === 🚀 Flujo de login exitoso antes de ir a /proteted ===
window.addEventListener("DOMContentLoaded", () => {
    if (typeof loginSuccess !== "undefined" && loginSuccess === true) {
        Swal.fire({
            title: "Inicio de sesión exitoso",
            icon: "success",
            timer: 2000,
            timerProgressBar: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        }).then(() => {
            // después de la alerta, redirige al protegido
            window.location.href = "/proteted";
        });
    }
});

// === 🚨 Mensajes dinámicos desde el backend ===
if (typeof mensajeError !== "undefined" && mensajeError !== null) {
    Swal.fire({
        icon: "error",
        title: "Credenciales inválidas",
        text: mensajeError,
        customClass: {
            title: 'swal-title',
            popup: 'swal-popup'
        }
    });
}

if (typeof mensajeExitoso !== "undefined" && mensajeExitoso !== null) {
    Swal.fire({
        icon: "success",
        title: "Sesión cerrada",
        text: mensajeExitoso,
        timer: 3000,
        timerProgressBar: true,
        customClass: {
            title: 'swal-title',
            popup: 'swal-popup'
        }
    });
}