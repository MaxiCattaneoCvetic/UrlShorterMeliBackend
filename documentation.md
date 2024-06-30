
Tecnologias utilizadas para el proyecto

Bases de datos:

Para el proyecto consideré dos opciones de base de datos.
Desde un principio descarte la posibilidad de utilizar bases de datos SQL debido a que las NoSQL son mucho mas rapidas y considero que para este desafio era lo mas simple y efectivo utilizar.
Estuve entre 2 tipos de base de datos Nosql
1) MongoDB: Facil de configurar, ya realice proyectos  con esa tecnologia, es rapida y flexible, almacenamiento gratis (con limite).
2) Redis: Su configuracion no fue facil, tuve que investigar sobre la tecnologia

Conclusion
En este caso a pesar de las dificultades para mi experiencia acerca de Redis, decidi utilizarla por varios ascpectos interesantes que encontre en internet.
1) Permite almacenar datos en formato Clave - valor, para este proyecto, es una caracteristica clave ya que cada hash creado almacenara una URL larga. Clave- valor -> (Hash-Url).
2) Otra consideracíon es que necesitaba una base de datos que almacene datos (clave valor) y nada mas, no necesitaba grandes estructuras con lo cual redit era buena alternativa.
3) Debido al punto 2 Redis posee menos latencia que MongoDb, con lo cual podria servirme para cumplir con las RPM solicitadas en el desafio.

Tecnologias de monitorización:

1) SpringBoot actuator: Para medir la salud de mi aplicacion utilice actuator, una tecnologia que conoci en la especialización de backend realizada en DigitalHouse que servia para los requerimientos de la app y tenia informacion clara y fresca, con lo cual tambien reducia el tiempo de aprender una tecnologia nueva.
2) Admin actuator: Spring Boot actuator posee una interfaz amigable llamada admin actuator, en mi caso no la habia utilizado nunca, pero servia para medir las metricas y el estres del servidor cuando se realicen las pruebas de carga correspondiente.

Arquitectura:
Utilizo la arquitectura modelo vista controlador, en mis proyectos suelo utilizarla y me permite organizar mejor el codigo y abstraer responsabilidades.

Clases
RequestUrl: Es un record inmutable, se utiliza para establecer el tipo de dato que recibire en el controlador, en este caso utilizamos un String que seria la URl que vamos a shortear.

ShortMeliController: Controlador, encargado de recibir solicitudes del cliente y enviar respuestas tambien al mismo.

IShortMeli: Capa de abstraccion, sirve para comunicar mi servicio con mi controlador, ya que la comunicacion entre clases que estan en distintos paquetes se debe realizar mediante abstracciones.

ShortMeliService: Contiene la logica de negocio de mi aplicacion.
En este caso tenemos varios metodos que permiten almacenar la URL que viene desde el controller y asignarle un hash que se crear a partir de un algoritmo de encriptacion





Actuator: Nos va a generar toda la informacion de nuestra app, cuanto tiempo lleva ON, si se cae, si se cae la DB, nos listas las peticiones etc

Prometheus: Formatea  los datos de actuator para que los pueda entender GRAFANA -> Grafana es la que nos va a brindar los dashboard etc. Es nuestro tablero de admiinstracion




