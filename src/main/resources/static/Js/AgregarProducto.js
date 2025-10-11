import {glassmorphismDashboard, menuDesplegableDashboard, abrirNotificacionesAdmin } from "./DashboardProducto.js";


document.addEventListener("DOMContentLoaded", () => {
    // Efecto glassmorphism solo al hacer scroll
    glassmorphismDashboard();

    //Menú desplegable del perfil 
    menuDesplegableDashboard();

    //abrir la notificaciones del admin
    abrirNotificacionesAdmin();
    

});