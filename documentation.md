# Proyecto de Acortador de URLs

Este proyecto implementa un servicio de acortamiento de URLs utilizando diversas tecnologías para optimizar la velocidad, la eficiencia y la monitorización.

## Tecnologías Utilizadas

### Bases de Datos

Para el proyecto, consideré dos opciones de bases de datos NoSQL debido a su velocidad y simplicidad. Desde un principio, descarté la posibilidad de utilizar bases de datos SQL. A continuación, las opciones consideradas:

1. **MongoDB**:
   - Fácil de configurar
   - Experiencia previa con la tecnología
   - Rápida y flexible
   - Almacenamiento gratuito (con límite)

2. **Redis**:
   - Configuración más compleja, requirió investigación
   - Velocidad al almacenar estructuras de datos simples
   - Implementación de caché, reduciendo la necesidad de consultas constantes a la base de datos

**Conclusión**: A pesar de las dificultades iniciales, decidí utilizar Redis por su velocidad y la capacidad de implementar caché, lo que permite respuestas rápidas y menos costosas para los usuarios finales.

### Tecnologías de Monitorización

1. **Spring Boot Actuator**:
   - Medición de la salud de la aplicación
   - Métricas diversas, como la salud general de la aplicación

2. **Admin Actuator**:
   - Interfaz amigable para observar información del servicio en tiempo real

### Arquitectura

Utilizo la arquitectura Modelo-Vista-Controlador (MVC), que permite organizar mejor el código y abstraer responsabilidades.

### Responsabilidades y Clases

- **RequestUrl**: Record inmutable para establecer el tipo de dato recibido en el controlador (la URL a acortar).
- **ShortMeliController**: Controlador encargado de recibir solicitudes del cliente y enviar respuestas.
- **IShortMeli**: Capa de abstracción para comunicar el servicio con el controlador.
- **ShortMeliService**: Contiene la lógica de negocio de la aplicación.
- **IMeliPersistance**: Se utiliza para comunicarse con la base de datos.
- **Actuator**: Genera información sobre la aplicación, como tiempo de actividad, caídas de la base de datos, y lista de peticiones.
- **SwaggerConfig**: Clase de configuración para personalizar Swagger (título, versión de la app).
- **CorsConfiguration**: Configuración de CORS para permitir comunicación entre el frontend y la aplicación.
- **UrlShortCustomMetrics**: Mide métricas personalizadas como la cantidad de peticiones GET y POST.

### Manejo de Errores

Para el manejo de errores, utilizo un Controller Advisor que centraliza y personaliza la gestión de errores. La aplicación maneja dos tipos de errores personalizados: `BadRequest` y `NotFound`, lanzados por la capa de servicio.

- **GlobalHandlerExceptions**: Clase maestra encargada de formar los errores de manera clara y detallada para el cliente.
- **GlobalErrorResponse**: Da la estructura de la respuesta de error.

### Mappers

- **RequestUrlToUrlMapper**: Transforma la solicitud desde la capa de presentación en un objeto `Url` conocido por la aplicación. También realiza validaciones y reformaciones de la solicitud para asegurar que sea una URL válida.
