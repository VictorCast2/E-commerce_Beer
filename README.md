# E-commerce_Beer 🍻

¡Una tienda online para los amantes de la cerveza!  
Este proyecto simula un sistema de e-commerce donde los usuarios pueden **explorar**, **elegir** y **comprar** cervezas artesanales y comerciales.  

Incluye:

- 🔐 Login de usuarios
- 🛍️ Carrito de compras
- 📦 Catálogo dinámico de productos
- ⚙️ Panel administrativo para gestión de inventario

---

## 🚀 Tecnologías utilizadas

- **Java 21**
- **Spring Boot**
- **Thymeleaf**
- **Maven**
- **MySQL**
- **Docker**
- **HTML + CSS + JS**

---

## 📂 Funcionalidades principales

- Registro y autenticación de usuarios
- Navegación por catálogo de cervezas
- Agregado y eliminación de productos del carrito
- Gestión de productos desde el panel admin
- Persistencia en base de datos relacional
- Diseño responsive básico para escritorio y móvil

---

## 📦 Estructura del proyecto


```plaintext
E-commerce\_Beer/
├── src/
│   ├── main/
│   │   ├── java/           # Código fuente
│   │   └── resources/      # Configuración y plantillas
│   └── test/               # Pruebas
├── Dockerfile              # Contenedor (opcional)
├── docker-compose.yml      # Entorno local (opcional)
├── pom.xml                 # Dependencias Maven
└── README.md
```

---

## ⚙️ Cómo ejecutar el proyecto
1. Clona el repositorio:
````bash

   git clone https://github.com/tuusuario/E-commerce_Beer.git
   cd E-commerce_Beer
````

2. Configura la base de datos en `application.properties`.

3. Ejecuta con Maven:

   ```bash
   mvn spring-boot:run
   ```

4. (Opcional) Usa Docker:

   ```bash
   docker-compose up --build
   ```

---

## 🤝 Contribuciones

¿Quieres ayudar? ¡Genial! Lee nuestro archivo [`CONTRIBUTING.md`](CONTRIBUTING.md) para saber cómo colaborar.

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT.
Puedes usarlo, modificarlo y distribuirlo libremente.

---

## 🍺 Créditos

Desarrollado con ❤️ para los amantes de la cerveza artesanal y el código limpio.