# Blogging Platform API

La API RESTful permite gestionar un blog personal con las siguientes funcionalidades:

- **Crear un nuevo post**.
- **Actualizar un post existente**.
- **Eliminar un post**.
- **Obtener un solo post**.
- **Obtener todos los posts**.
- **Filtrar posts por búsqueda**.

**Enlace del Proyecto:** [Blogging Platform API](https://roadmap.sh/projects/blogging-platform-api)

## Guía para Levantar el Proyecto

### Clonar el Repositorio

Primero, clona este repositorio usando el siguiente comando:

```bash
git clone https://github.com/mikellbobadilla/Bloggin-Platform.git
```

### Configuración de Variables de Entorno

El proyecto requiere tres variables de entorno esenciales para conectarse a la base de datos:

- `DB_HOST`: Dirección del host de la base de datos.
- `DB_USER`: Nombre de usuario de la base de datos.
- `DB_PASSWORD`: Contraseña del usuario de la base de datos.

**Ejemplo:**

```text
DB_HOST=localhost:5432/db
DB_USER=userDatabase
DB_PASSWORD=admin123
```

Si prefieres definir estas variables directamente, puedes editarlas en el archivo de propiedades ubicado en **/src/main/resources**.

### Compilar y Desplegar el Proyecto

El proyecto puede ser desplegado tanto en un entorno tradicional como dentro de un contenedor Docker.

**Comandos para compilar:**

```bash
# Para compilar y empaquetar
mvn clean package

# Alternativamente, puedes usar
mvn clean
mvn install
```

### Uso de Docker Compose

El proyecto incluye un archivo `docker-compose.yml` listo para usar, que facilita la configuración y despliegue en entornos Docker.

#### Configuración de Secretos

Para gestionar la contraseña de la base de datos de forma segura, se utilizan secretos configurados en Docker. Específicamente, la contraseña se debe almacenar en un archivo de texto llamado `db_password.txt` ubicado en la raíz del proyecto. Este archivo está protegido por defecto en el `.gitignore`, evitando que se suba al repositorio. Si deseas cambiar la ubicación del archivo de secretos, puedes modificar la configuración en el archivo `docker-compose.yml`.

El proyecto también acepta variables de entorno para los secretos, que se configuran de la siguiente manera:

**Ejemplo:**

```text
DB_HOST_FILE=/run/secrets/{file}
DB_USER_FILE=/run/secrets/{file}
DB_PASSWORD_FILE=/run/secrets/{file}
```

Para más detalles, revisa el archivo `docker-compose.yml`.

### Inicialización de la Base de Datos

La primera vez que la base de datos se ejecuta, creará las tablas y aplicará las sentencias SQL definidas en **/schema/init.sql**. Si necesitas añadir nuevas sentencias, modifica este archivo antes de la primera ejecución. Ten en cuenta que las tablas existentes no se eliminarán; solo se crearán si no existen.

### Despliegue del Proyecto con Docker

Para desplegar el proyecto utilizando Docker Compose, utiliza el siguiente comando:

```bash
docker compose up --build
```

Una vez completado, puedes ejecutar el contenedor en segundo plano:

```bash
docker compose up -d
```

## Stack

El proyecto utiliza `Spring Boot v.3.3.3` con las siguientes dependencias:

- **Spring Web**: Para construir la API.
- **Spring JPA con Hibernate**: Para el mapeo de entidades.
- **Spring Validations**: Para la validación de datos de entrada.
- **Driver de PostgreSQL**: Base de datos principal.
- **Driver de H2**: Base de datos para testing.
- **Lombok**: Para reducir código repetitivo.

## Operaciones de la API

### Crear un Post

**Endpoint:** `POST /posts`

- Crea un post con datos validados.
- Respuesta: `201 Created` con el contenido creado o `400 Bad Request` si hay errores.

### Actualizar un Post

**Endpoint:** `PUT /posts/{id}`

- Actualiza un post existente.
- Respuesta: `200 OK` con contenido actualizado, `400 Bad Request` por validaciones, o `404 Not Found` si no existe.

### Eliminar un Post

**Endpoint:** `DELETE /posts/{id}`

- Elimina un post.
- Respuesta: `204 No Content` si se eliminó o `404 Not Found` si no existe.

### Obtener un Post

**Endpoint:** `GET /posts/{id}`

- Devuelve un post específico.
- Respuesta: `200 OK` si existe o `404 Not Found` si no.

### Obtener todos los Posts

**Endpoint:** `GET /posts`

- Devuelve todos los posts o filtra por búsqueda.
- Respuesta: `200 OK` con los posts.
