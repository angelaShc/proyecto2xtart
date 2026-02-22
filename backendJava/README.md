Los readme lo puede encontrar también en la memoria, lea los apartados de:
- acceso a base de datos (necesario para todo el proyecto)
- readme backend
- readme frontend (donde podrá ver también el juego de Unity)

Requisitos Previos
Asegúrate de tener instalado:
-	Java JDK 17 o superior.
-	Maven (gestor de dependencias).
-	Una cuenta activa en MongoDB Atlas (revisa el punto de acceso a base de datos en la memoria).
-	Un IDE como IntelliJ IDEA, Eclipse o VS Code.
-	Asegura de que están los puertos 8080 y 4200 libres.
Puede tanto hacer pruebas desde un navegador cualquiera o desde postman:
Desde Postman:
-	Métodos POST:

Loguea para obtener el token para consultar las otras direcciones, pega http://localhost:8080/auth/login en la zona de url, en auth elige ningún método y en body pega:

{
  "username": "ginkgoFox",
  "password": "shc001"
}

Para crear usuario haz lo mismo, cambia el url a http://localhost:8080/auth/register y pega esto en el body:

{
    "username": "nombre",
    "email": "jugador@ejemplo.com",
    "password": "miPasswordSeguro123"
}

-	Método GET:

En el resultado que salió del logueo, copia lo que hay dentro de las comillas del token, en forma de autenticación elige bearer token y pega el token copiado, no necesita nada más, ejecuta la prueba con los siguientes urls:

localhost:8080/user
localhost:8080/game
localhost:8080/me/progress
localhost:8080/inventory

Para las funciones creadas:
-	User:
Método	Endpoint	Descripción	Acceso
GET	/user/all_users	Lista todos los usuarios (formato DTO)	ADMIN
GET	/user	Obtiene el perfil del usuario autenticado	USER
PUT	/user/add-friend/{friendId}	Añade un amigo por su ID	USER
DELETE	/user/remove-friend/{friendId}	Elimina un amigo por su ID	USER

-	Inventory:
Método	Endpoint	Descripción	Acceso
GET	/inventory/all	Lista todos los inventarios globales	ADMIN
GET	/inventory	Obtiene los ítems del usuario autenticado	USER
GET	/inventory/{id}	Obtiene un ítem específico por su ID	USER
POST	/inventory	Registra un nuevo ítem (enviado desde el juego)	USER
PUT	/inventory/{id}	Actualiza datos de un ítem existente	USER
DELETE	/inventory/{id}	Elimina un ítem del inventario	USER

-	Progreso de juego:
Método	Endpoint	Descripción	Acceso
POST	/start/{juegoId}	Inicializa la sesión y el contador de tiempo	USER
POST	/registrar	Desde Unity: Guarda puntos y progreso actual	USER
GET	/	Lista el historial de progreso del usuario	USER
GET	/stats	Obtiene tiempo total y accesos totales	USER
DELETE	/{id}	Elimina un registro de progreso por ID	ADMIN

-	Juegos disponibles:
Método	Endpoint	Descripción	Acceso
GET	/game	Lista todos los juegos disponibles	Público
GET	/game/{gameId}	Obtiene detalles de un juego específico	Público
POST	/game	Registra un nuevo juego en la plataforma	ADMIN
PUT	/game	Actualiza la información de un juego	ADMIN
DELETE	/game/delete/{gameId}	Elimina un juego del sistema	ADMIN


