# 🛒 Shopping Cart - Spring Boot Backend

Este proyecto es una API RESTful para gestionar un **carrito de compras**, implementado con **Spring Boot**, seguridad con **Spring Security (usuarios en memoria)** y persistencia de datos en **PostgreSQL**.

---
## ⚙️ Requisitos Previos

- Java JDK 21
- PostgreSQL
- Maven
- Postman (opcional para pruebas)

---

## 🔧 Instalación y Ejecución

### 1. Clona el repositorio

```bash
git clone https://github.com/tuusuario/shopping_cart.git
cd shopping_cart
```

### 2. Crea la base de datos en PostgreSQL

```sql
CREATE DATABASE shopping_cart;
```

### 3. Configura el archivo `.env` o `application.properties`

**Ubicación:** `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/shopping_cart
spring.datasource.username=postgres
spring.datasource.password=1234
```

> Asegúrate de que los datos coincidan con tu configuración local de PostgreSQL.

---

## 🔐 Credenciales de Usuarios

| Rol   | Usuario | Contraseña |
|-------|---------|------------|
| Admin | admin   | adminpass  |
| User  | user    | userpass   |

> Ambos roles están definidos en memoria usando Spring Security (`InMemoryUserDetailsManager`).

---

## ▶️ Ejecutar el Proyecto

Puedes ejecutar el proyecto desde tu IDE o mediante la terminal con:

```bash
./mvnw spring-boot:run
```

---

## 🔁 Flujo para probar la API

1. **Primero** se deben insertar usuarios, productos y carritos que se hace automaticamente al ejecutar el proyecto
2. Luego puedes hacer operaciones de `POST` para añadir productos al carrito.
3. Finalmente puedes:
   - Consultar los elementos del carrito.
   - Consultar uno en específico.
   - Eliminar todos o uno en específico.
   - Actualizar productos.
4. Cada vez que agregas un producto al carrito, el endpoint responde con:
   ```json
   Se insertaron X elementos y el total es $Y
   ```

---

## 📬 Postman

- Se incluye una **colección Postman** lista para importar y hacer pruebas CRUD de:
  - Productos
  - Carrito
  - Ítems del carrito
  - Usuarios

---

## 📄 Documentación Detallada

Toda la documentación técnica (incluyendo diseño, clases, relaciones y detalles funcionales) está incluida en el archivo PDF adjunto al proyecto.

```
📄 /Documentación_Shopping_Cart.pdf
```

---

```diff
+ Autor: Julieta Vargas
+ Año: 2025
```