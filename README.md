# Blogging Platform API

Consiste en crear una API RESTful para un blog personal. La API debe permitir a los usuarios realizar:

Link del [Proyecto](https://roadmap.sh/projects/blogging-platform-api)

- Crear un nuevo post
- Actualizar un post existente
- Eliminar un post
- Obtener un solo post
- Obtener todos los posts
- Filtrar los posts por búsqueda

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

El proyecto está construido con `Spring Boot v.3.3.3`.

Las dependencias que utiliza son:

- Spring web - Para construir la API
- Spring Jpa con Hibernate - Para mapear las clases con las entidades
- Driver de PostgreSQL - como bases de datos
- Driver de H2 - para testing
- lombok - Para evitar boilerplate

## Crear un Post

Para crear un post se tiene que usar el método `POST`

**Endpoint: POST /posts**

```JSON
{
  "title": "Mi primer post",
  "content": "Este es el contenido de mi primer post",
  "category": "Tecnología",
  "tags": [
    "Programación",
    "Bases de datos"
  ]
}
```

El endpoint debería validar el cuerpo de la request y retornar un código de respuesta `201 Created` con el contenido
creado:

````JSON
{
  "id": 1,
  "title": "Mi primer post",
  "content": "Este es el contenido de mi primer post",
  "category": "Tecnología",
  "tags": [
    "Programación",
    "Bases de datos"
  ],
  "createAt": "2021-09-01T12:00:00Z",
  "updatedAt": "2021-09-01T12:00:00Z"
}
````

Si hay un error de validación tiene que retornar un código de respuesta `400 Bad Request`

## Actualizar un Post

Actualizar un post existente usando el método `PUT`

**Endpoint: PUT /posts/1**

```JSON
{
  "title": "Mi post actualizado",
  "content": "Este es el contenido actualizado de mi primer post",
  "category": "Tecnología",
  "tags": [
    "Programación",
    "Bases de datos"
  ]
}
```

El endpoint debería validar el cuerpo de la request y retornar un código de respuesta `200 OK` con el contenido
actualizado del post

````JSON
{
  "id": 1,
  "title": "Mi post actualizado",
  "content": "Este es el contenido actualizado de mi primer post",
  "category": "Tecnología",
  "tags": [
    "Programación",
    "Bases de datos"
  ],
  "createAt": "2021-09-01T12:00:00Z",
  "updatedAt": "2021-09-01T12:30:00Z"
}
````

Si hay un error de validación tiene que retornar un código de respuesta `400 Bad Request`.

Si no existe el post deberá retornar un código de respuesta `404 Not Found`

## Eliminar un Post

Para eliminar un post se tiene que usar el método `DELETE`

**Endpoint: DELETE /posts/1**

El endpoint tiene que retornar un `204 No Content` si el post fué eliminado correctamente ó `404 Not Found` si el post
no existe

## Obtener un Post

Para obtener un post se tiene que usar el método `GET`

**Endpoint: /posts/1**

El endpoint deberá retornar un código de respuesta `200 OK` sí el post existe

```JSON
{
  "id": 1,
  "title": "Mi primer post",
  "content": "Este es el contenido de mi primer post",
  "category": "Tecnología",
  "tags": [
    "Programación",
    "Bases de datos"
  ],
  "createAt": "2021-09-01T12:00:00Z",
  "updatedAt": "2021-09-01T12:00:00Z"
}
```

En caso de que no exista el post deberá retornar un código de respuesta `404 Not Found`

## Obtener todos los posts

Para obtener todos los posts se tiene que usar el método `GET`

**Endpoint: /posts**

El endpoint tiene que retornar un código de respuesta `200 OK` con el contenido.

```JSON
[
  {
    "id": 1,
    "title": "Mi primer post",
    "content": "Este es el contenido de mi primer post",
    "category": "Tecnología",
    "tags": [
      "Programación",
      "Bases de datos"
    ],
    "createAt": "2021-09-01T12:00:00Z",
    "updatedAt": "2021-09-01T12:00:00Z"
  },
  {
    "id": 2,
    "title": "Mi segundo post",
    "content": "Este es el contenido de mi segundo post",
    "category": "Tecnología",
    "tags": [
      "Programación",
      "Bases de datos"
    ],
    "createAt": "2021-09-01T12:00:00Z",
    "updatedAt": "2021-09-01T12:00:00Z"
  }
]
```

Mientras se está recuperando los posts, el usuario puede filtrar los posts por búsqueda.

La búsqueda se tiene que hacer por los campos: Título, Contenido o Categoría. Ejemplo:

**GET /posts?term=tech**

Tendría que retornar una lista de posts que tengan el término `tech` en el título, contenido o categoría.
