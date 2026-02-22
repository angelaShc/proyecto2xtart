Hidden Studio - Frontend

Esta es la web desarrollado en Angular para la plataforma Hidden Studio, una interfaz interactiva que permite a los usuarios explorar juegos, gestionar su inventario y sincronizar su progreso en tiempo real con Unity.

Descripción del Proyecto

El frontend actúa como el núcleo de la experiencia del usuario, ofreciendo:

- Catálogo de Juegos: Visualización dinámica de juegos disponibles.
- Integración con Unity: Comunicación bidireccional entre la web y los juegos WebGL (envío de puntuaciones e ítems).
- Gestión de Inventario: Visualización de objetos obtenidos dentro de los juegos.
- Seguridad: Gestión de sesiones mediante JWT (JSON Web Tokens).
- Reactividad: Uso de Angular Signals para una gestión de estado eficiente y rápida.

API Externa Utilizada

Para enriquecer la base de datos de juegos y contenidos, el proyecto se apoya en:
- RAWG Video Games Database API: Utilizada para obtener metadatos de juegos, imágenes de alta resolución, fechas de lanzamiento y valoraciones de la crítica.

Descripción del Backend

El frontend se comunica con un ecosistema de microservicios diseñado para la escalabilidad:
- Tecnología: Java 21 con Spring Boot 3.
- Base de Datos: * MongoDB: Almacena el progreso de los juegos (GameProgress), puntuaciones máximas y el inventario de los usuarios.
- Seguridad: Filtros JWT personalizados y configuración de CORS para permitir la comunicación segura con el dominio de desarrollo y producción.

Instrucciones de Ejecución

Requisitos Previos

- Node.js: Versión 18.x o superior.
- Angular CLI: Versión 17.x o superior.
- Backend: El servidor Spring Boot debe estar corriendo en http://localhost:8080.

Instalación

Clona el repositorio:

Bash
git clone [https://github.com/angelaShc/proyecto2xtart.git](https://github.com/angelaShc/proyecto2xtart.git)
cd proyecto2xtart

Instala las dependencias:

Bash
npm install
Configuración del Entorno
Crea o edita el archivo src/environments/environment.ts para apuntar a la API:

TypeScript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};

Ejecución

Para lanzar el servidor de desarrollo:

Bash
ng serve
Navega a http://localhost:4200. La aplicación se recargará automáticamente si cambias alguno de los archivos fuente.