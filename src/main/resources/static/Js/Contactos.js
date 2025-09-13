import { activarGlassmorphism, inicialHeart, initCart, rederigirFav } from "./main.js";

document.addEventListener('DOMContentLoaded', () => {
    activarGlassmorphism();

    inicialHeart();

    initCart();

    rederigirFav();


    //validaciones de contactos
    const fieldsContactos = {
        nombre: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,}$/, errorMessage: "El nombre debe tener al menos 3 letras." },
        sujeto: { regex: /^[A-Za-zÁÉÍÓÚáéíóúÑñ\s]{3,50}$/, errorMessage: "El campo Sujeto es obligatorio" },
        email: { regex: /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/, errorMessage: "El correo solo puede contener letras,numeros,puntos,guiones y guion bajo." },
        comentario: { regex: /^.{10,500}$/, errorMessage: "Por favor, ingrese un comentario." }
    }

    Object.keys(fieldsContactos).forEach(fieldId => {
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
            } else if (fieldsContactos[fieldId].regex.test(value)) {
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

    const addform = document.querySelector(".form");
    const inputs2 = addform.querySelectorAll("input");

    addform.addEventListener("submit", function (event) {
        let formValid = true;

        inputs2.forEach(input => {
            const value = input.value.trim();
            if (value === "" || !fieldsContactos[input.id].regex.test(value)) {
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

});