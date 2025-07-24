# 🐳 Docker Guide - E-commerce_Beer

Guía rápida para ejecutar y desplegar **E-commerce_Beer** usando Docker.

---

## 🛠️ Construcción y ejecución local

Cuando estés listo para probar tu aplicación localmente, ejecuta:

```bash
docker compose up --build
````

Esto construirá la imagen y levantará los contenedores necesarios.
Tu aplicación estará disponible en:

```
http://localhost:8080
```

---

## ☁️ Despliegue en la nube

### 1. Construye la imagen

```bash
docker build -t myapp .
```

Si tu entorno en la nube utiliza una arquitectura de CPU distinta a la de tu máquina local
(por ejemplo, si estás en un Mac con chip M1 y el servidor usa `amd64`), debes construir la imagen para esa plataforma:

```bash
docker build --platform=linux/amd64 -t myapp .
```

---

### 2. Sube la imagen a un registro

```bash
docker push myregistry.com/myapp
```

Reemplaza `myregistry.com/myapp` con la URL real de tu Docker Registry (por ejemplo: Docker Hub o GitHub Container Registry).

---

## 📚 Recursos adicionales

Consulta la guía oficial de Docker para más detalles sobre cómo compartir tus imágenes:

👉 [Docker - Getting Started: Sharing Your App](https://docs.docker.com/go/get-started-sharing/)

---

¡Y listo! Ya tienes tu app lista para el mundo, con la magia de Docker. 🚀🍻

```

¿Quieres que lo adapte a un flujo con **Docker Hub** específico o que incluya instrucciones para una base de datos como MySQL en Docker Compose?
```