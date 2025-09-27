// === 🚨 Variables dinámicas de Thymeleaf ===
const body = document.body;
const mensajeError   = body.dataset.mensajeError || null;
const mensajeExitoso = body.dataset.mensajeExitoso || null;
const loginSuccess   = body.dataset.loginSuccess === "true";
const termsLabel = document.getElementById("terms-label");

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

termsLabel.addEventListener("click", (e) => {
    // Evita que el checkbox se marque/desmarque automáticamente
    e.preventDefault();

    Swal.fire({
        title: "Términos y Condiciones",
        html: `
        <div style="text-align: left; max-height: 300px; overflow-y: auto; padding: 10px;">
            <h3>1. INFORMACIÓN GENERAL</h3>
            <p>Este sitio web es operado por Costa De Oro Imports. En todo el sitio, los términos "nosotros", "nos" y "nuestro" se refieren a Costa De Oro Imports.<br>
            Sitio web: www.costadeoroimports.co<br>
            Email de contacto: costadeoroimports@gmail.com</p>
            <br>
            <h3>2. PRODUCTOS Y SERVICIOS</h3>
            <p>Nos esforzamos por mostrar con la mayor precisión los colores y las imágenes de nuestros productos que aparecen en la tienda. No podemos garantizar que la visualización de cualquier color en su monitor sea exacta.</p>
            <br>
            <h3>3. PRECIOS Y PAGOS</h3>
            <p>Todos los precios están sujetos a cambios sin previo aviso. Los precios mostrados incluyen IVA cuando corresponda.</p>
            <br>
            <h3>4. ENVÍOS</h3>
            <p>El tiempo estimado de envío es de 5 - 10 días hábiles. Los tiempos de entrega son estimados y no podemos garantizar entregas en fechas específicas.</p>
            <br>
            <h3>5. POLÍTICA DE DEVOLUCIONES</h3>
            <p>Aceptamos devoluciones dentro de los 14 días posteriores a la recepción del producto.</p>
            <br>
            <h4>5.1 PROCEDIMIENTO DE DEVOLUCIÓN</h4>
            <ul>
                <li><b>Devolución del dinero</b> (solo aplica para casos de garantía y ley de retracto):
                    <ul>
                        <li>A través de transferencia: dentro de los cinco días hábiles siguientes de recibir el producto nuevamente en nuestra bodega.</li>
                        <li>A través de reversión del pago: quince días hábiles después de recibir el producto. Esta reversión corre por cuenta de tu entidad bancaria.</li>
                    </ul>
                </li>
                <li><b>Cambio del producto</b> (sujeto a disponibilidad de inventario).</li>
                <li><b>Cupón</b> para realizar una nueva compra (válido por seis meses a partir de la fecha de creación).</li>
            </ul>
            <br>
            <h4>5.2 CONDICIONES DEL PRODUCTO PARA DEVOLUCIÓN</h4>
            <p>El producto deberá devolverse en óptimas condiciones, sin rastros de uso, con etiquetas originales o introducidas en el empaque. Una vez recibido en nuestra bodega, verificaremos las condiciones y según el resultado, se enviará un producto nuevo o se entregará un cupón para otra compra.</p>
            <br>
            <h3>6. PRIVACIDAD Y PROTECCIÓN DE DATOS</h3>
            <p>Nos comprometemos a proteger su privacidad. La información personal que nos proporcione se utilizará únicamente para procesar su pedido y mejorar su experiencia de compra.</p>
            <br>
            <h3>7. MODIFICACIONES DE LOS TÉRMINOS</h3>
            <p>Nos reservamos el derecho de modificar estos términos en cualquier momento. Los cambios entrarán en vigor inmediatamente después de su publicación en el sitio web.</p>
        </div>
    `,
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "Acepto",
        cancelButtonText: "Cancelar",
        customClass: {
            title: 'swal-title',
            popup: 'swal-popup'
        }
    }).then((result) => {
        if (result.isConfirmed) {
            checkbox.checked = true; // Marca el checkbox si acepta
        } else {
            checkbox.checked = false; // Lo deja desmarcado si cancela
        }
    });
});

// Validación dinámica de inputs
Object.keys(fields).forEach(fieldId => {
    const input = document.getElementById(fieldId);
    const inputBox = input.closest(".input-box");
    const validIcon = inputBox.querySelector(".valid-icon");
    const invalidIcon = inputBox.querySelector(".invalid-icon");
    const errorMessage = inputBox.nextElementSibling;

    input.addEventListener("input", () => {
        const value = input.value.trim();

        if (value === "") {
            // Resetear estilos si está vacío
            inputBox.classList.remove("input-error", "input-valid");
            validIcon.style.display = "none";
            invalidIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "1px solid #ccc";
        } else if (fields[fieldId].regex.test(value)) {
            // Válido
            inputBox.classList.add("input-valid");
            inputBox.classList.remove("input-error");
            validIcon.style.display = "block";
            invalidIcon.style.display = "none";
            errorMessage.style.display = "none";
            input.style.border = "2px solid #0034de";
        } else {
            // Inválido
            inputBox.classList.add("input-error");
            inputBox.classList.remove("input-valid");
            validIcon.style.display = "none";
            invalidIcon.style.display = "block";
            errorMessage.style.display = "block";
            input.style.border = "2px solid #fd1f1f";
        }
    });
});

const form = document.querySelector("form");
const inputs = document.querySelectorAll("input:not([type='checkbox'])");
const checkbox = document.querySelector(".remember-forgot input");
const loginBtn = document.querySelector("#loginBtn")
        || document.querySelector(".formulario__btn.g-recaptcha");

// Validación antes de enviar
function validateForm() {
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
    return formValid;
}

// === 🚀 Callback de Google reCAPTCHA ===
window.onSubmit = function (token) {
    form.submit();
};

// === 🚀 Evento en el botón de login ===
if (loginBtn) {
    loginBtn.addEventListener("click", () => {
        if (!validateForm()) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Por favor rellene el formulario correctamente",
                customClass: {
                    title: 'swal-title',
                    popup: 'swal-popup'
                }
            });
            return;
        }
        grecaptcha.execute();
    });
}

// === 🚀 Flujo de login exitoso antes de ir ===
window.addEventListener("DOMContentLoaded", () => {
    if (typeof loginSuccess !== "undefined" && loginSuccess === true) {
        Swal.fire({
            title: "Inicio de sesión exitoso",
            icon: "success",
            timer: 3000,
            timerProgressBar: true,
            allowOutsideClick: false,
            allowEscapeKey: false,
            customClass: {
                title: 'swal-title',
                popup: 'swal-popup'
            }
        }).then(() => {
            // después de la alerta, redirige al protegido
            window.location.href = "/";
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

// === 👁 Mostrar / ocultar contraseña ===
const passwordInput = document.getElementById("password");
const togglePasswordBtn = document.querySelector(".toggle-password");

if (togglePasswordBtn) {
    togglePasswordBtn.addEventListener("click", () => {
        const isText = passwordInput.type === "text";
        passwordInput.type = isText ? "password" : "text";
        togglePasswordBtn.classList.toggle("active", !isText);
    });
}
