# Twitter ejemplo

Esto es un ejemplo de trabajar con <a href="https://developer.twitter.com" target="_blank">Twitter API v2</a> usando <a href="https://twitter4j.org" target="_blank">Twitter4j</a>

## ¿Cómo se usa? 🚀

Primeramente tenemos que disponer de las credenciales de Twitter API desarrollador e insertarlas en el fichero twitter4j.properties

En funcionamiento los tweets obtenidos se almacenan en una BBDD h2 en memoria para su procesamiento posterior.

Una vez iniciado el proyecto iremos a http://localhost:8080/swagger-ui.html y nos aparecerán los servicios disponibles.

### Servicios 🔧

/twitter/list

```
No se requieren parámetros.
Este servicio nos genera un listado de todos los tweets obtenidos hasta el momento.
```

/twitter/list/{validator}

```
Se requiere el parámetro {validator} que es un string que contendrá el nombre de validador.
Este servicio nos genera un listado de todos los tweets validador por {validator}-.
```

/twitter/validate/{validator}/{id}

```
Se requiere un primer parámetro {validator} que es un string que contendrá el nombre de validador.
Se requiree un segundo parámetro {id} que es el id del tweet a validar.
Este servicio marca como validado un tweet determinado por su id y almacena quién lo ha validado.
```

/twitter/hashtag

```
No se requieren parámetros.
Este servicio nos genera un listado con los 10 tweets más usados que tenemos cargados en BBDD.
```

## Construido con 🛠️

* [Intellij](http://www.dropwizard.io/1.0.2/docs/) - IDE
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Twitter API v2](https://developer.twitter.com) - API de twitter
* [Twitter4j](https://twitter4j.org) - Librería para manejo de twitter

## Autor ✒️

* **Luis Miguel Carrasco** - [lmcarrasco20](https://github.com/lmcarrasco20)
