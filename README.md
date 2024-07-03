
# URl Shorter Meli

Esta aplicación convierte una URL larga proporcionada por el usuario y la convierte en una URL corta accesible.




## Tech Stack

Para el front se utilizo NextJs. 

La estructura del backend esta hecha con Java springboot y para el almacenamiento de los datos y la utilización del cache Redis.

Para hacer el deploy se utilizo AWS. un bucket s3 para el almacenamiento del front y una t2.micro para el almacenamiento del backend. (Ec2).





## Deployment

Hay dos formas de hacer utilizar la aplicación, la primera es utilizando el deploy en linea. Este deploy integra tanto el front como el back y es la aplicación accesible para todos los usuarios.

```bash
  http://shortmeli.s3-website.us-east-2.amazonaws.com/
```
La segunda forma es hacer el deploy local, para esto vamos a necesitar los siguientes servicios.
En ambas opciones debemos seguir las instrucciones del script.

- Docker 🐳
- La aplicación por defecto corre en el puerto 8080, por lo tanto es sumamente importante que este este disponible

**Linux**
```bash  
  http://shortmeli.s3-website.us-east-2.amazonaws.com/
```


**PowerShell**
```bash  
  http://shortmeli.s3-website.us-east-2.amazonaws.com/
```

## Documentación de la API

Para entornos locales ingresar:
```bash  
http://localhost:8080/swagger-ui.html
```

Para el deploy online ingresar:
```bash  
http://34.196.176.225:8080/swagger-ui.html
```

## Metricas de la aplicación

Para ingresar a las metricas de la aplicación y ver como reacciona a determinados eventos podemos ingresar mediante el menu de navegación en el deploy online. 

De lo contrario, una vez corrido el script para el deploy local podemos ingresar al siguiente link

```bash  
http://localhost:8080/instances/830c018baa23/details
```

## Estructura del proyecto.
![image](https://github.com/MaxiCattaneoCvetic/UrlShorterMeliBackend/blob/main/Estructura%20general.jpg)
