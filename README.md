# Blogging Platform API

Consiste en crear una API RESTful para un blog personal. La API debe permitir a los usuarios realizar:

- Crear un nuevo post
- Actualizar un post existente
- Eliminar un post
- Obtener un solo post
- Obtener todos los posts
- Filtrar los posts por búsqueda

## Formas de Levantar el proyecto


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
