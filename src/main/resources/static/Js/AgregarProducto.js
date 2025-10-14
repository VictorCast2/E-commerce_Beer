document.addEventListener("DOMContentLoaded", () => {
    // Efecto glassmorphism solo al hacer scroll
    const header = document.querySelector('.content__header');

    window.addEventListener('scroll', () => {
        if (window.scrollY > 10) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    });

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

    const categoriaPrincipal = document.getElementById("categoriaPrincipal");
    const categoriaSecundaria = document.getElementById("categoriaSecundaria");

    // Diccionario de subcategorías
    const subcategorias = {
        Vino: ["Vino Tinto", "Vino Blanco", "Vino Rosado", "Espumoso"],
        Cerveza: ["Rubia", "Negra", "Roja", "Artesanal"],
        Whisky: ["Escocés", "Irlandés", "Americano", "Canadiense"],
        Tequila: ["Blanco", "Reposado", "Añejo"],
        Vodka: ["Ruso", "Polaco", "Sueco"],
        Ginebra: ["Seca", "Dulce", "Aromática"],
        Aguardiente: ["Antioqueño", "Cristal", "Sin azúcar"],
        Ron: ["Blanco", "Oscuro", "Añejo"],
        Mezcal: ["Joven", "Reposado", "Añejo"]
    };

    // Cuando cambia la categoría principal
    categoriaPrincipal.addEventListener("change", function () {
        const seleccion = this.value;

        // Limpiar las opciones anteriores
        categoriaSecundaria.innerHTML = '<option disabled selected>Categoría secundaria</option>';

        // Agregar las nuevas según la categoría seleccionada
        if (subcategorias[seleccion]) {
            subcategorias[seleccion].forEach((sub) => {
                const option = document.createElement("option");
                option.value = sub;
                option.textContent = sub;
                categoriaSecundaria.appendChild(option);
            });
        }

        // Reiniciar el valor seleccionado (por si antes había una opción previa)
        categoriaSecundaria.selectedIndex = 0;
    });


    // Imagen del producto
const boxImagen = document.querySelector('.formulario__boximagen');
const textoBox = document.querySelector('.boximagen__texto');

// Crear dinámicamente input file oculto
const inputFile = document.createElement('input');
inputFile.type = 'file';
inputFile.accept = 'image/*';
inputFile.style.display = 'none';
boxImagen.appendChild(inputFile);

// Crear contenedor de previsualización
const previewContainer = document.createElement('div');
previewContainer.classList.add('preview-container');
boxImagen.appendChild(previewContainer);

// Al hacer clic en el contenedor, abrir explorador
boxImagen.addEventListener('click', () => inputFile.click());

// Escuchar cambio del input
inputFile.addEventListener('change', (event) => {
    const file = event.target.files[0];
    if (!file) return;

    // Crear barra de carga
    previewContainer.innerHTML = `<div class="progress-bar"></div>`;
    previewContainer.style.display = 'flex';
    boxImagen.classList.add('imagen-activa');

    const progressBar = previewContainer.querySelector('.progress-bar');

    // Simular carga
    setTimeout(() => {
        const img = document.createElement('img');
        img.src = URL.createObjectURL(file);

        previewContainer.innerHTML = '';
        previewContainer.appendChild(img);

        // Crear botón eliminar (solo si no existe)
        let removeBtn = boxImagen.querySelector('.remove-image');
        if (!removeBtn) {
            removeBtn = document.createElement('span');
            removeBtn.classList.add('remove-image');
            removeBtn.textContent = 'Eliminar imagen';
            boxImagen.appendChild(removeBtn);
        }

        // Acción eliminar
        removeBtn.addEventListener('click', (e) => {
            e.stopPropagation(); // evita que abra el explorador

            // Eliminar imagen y botón
            previewContainer.style.display = 'none';
            previewContainer.innerHTML = '';
            boxImagen.classList.remove('imagen-activa');
            removeBtn.remove();
            inputFile.value = ''; // limpiar el input
        });
    }, 2000);
});


    //Descripcion de un producto
    const toolbarOptions = [
        [{ 'font': [] }, { 'size': [] }],           // Tipografía y tamaño
        ['bold', 'italic', 'underline', 'strike'],  // Estilo de texto
        [{ 'color': [] }, { 'background': [] }],    // Color del texto y fondo
        [{ 'script': 'sub' }, { 'script': 'super' }],// Sub/superíndice
        [{ 'header': '1' }, { 'header': '2' }, 'blockquote', 'code-block'],
        [{ 'list': 'ordered' }, { 'list': 'bullet' }],
        [{ 'indent': '-1' }, { 'indent': '+1' }],
        [{ 'align': [] }],
        ['link', 'image', 'video'],                 // Enlaces, imágenes, videos
        ['clean']                                   // Limpiar formato
    ];

    const quill = new Quill('#editor-container', {
        modules: { toolbar: toolbarOptions },
        theme: 'snow'
    });


});