# Chagenge MeLi.
# Operación Fuego de Quasar.


## Herramientas Utilizadas.
Para el desarrollo de esta aplicacion se utilizo:

- Java 8.
- Framework SpringBoot.
- JPS H2
- Integracion Swagger org.springdoc.
- Postman para pruebas API.

## Contenido
A continuación se listan los archivos dentro del repositorio:

1. Carpeta src:
    - Codigo Fuente carpeta src.
2. Carpeta file:
    - Archivo CHALLEGE.postman_collection.json en el cual encontrara los apis disponibles.
    - Archivo java.uml en el cual se muestra la estructura de clases del proyecto.

## Consideraciones:

- Los Satelites tienen como nombre los expuestos en el documento [KENOBI, SKYWALKER, SATO].
- Las posiciones de los satellites son fijas segun la entregada en la documentacion.
- Ningun servicio permite ingresar informacion de un satellite desconocido.
- Los request de cada servicio contienen sus propias validaciones que consideran los campos obligatorios para el correcto funcionamiento.
- El mensaje entregado por los satellites se recibe como una lista de string, los cuales son validados en caso de tener campos nulos o vacios, estos seran reemplazados con el fin de priorizar la decodificacion del mensaje (todo esto teniendo en cuanta la importancia del mismo), adicional a esto se valida la integridad del mensaje en caso que el mensaje de algun satellite contenga diferentes palabras en la misma posicion se considerara un mensaje ambiguo, y se le notificara al usuario por medio de un mensaje de error.
- El metodo utilizado para el calculo de la ubicacion es el de distancia entre dos puntos los cuales al tener 3 satellites nos generara 3 ecuaciones con dos incognitas. Esto puede generar ubicaciones de la nave imperial extrapoladas si se presentan inconsistencias el el dato distancia entregado por ellas.
- Se realiza validacion de la posicion calculada con el fin que la ubicacion entregada sea la correcta, en caso que las distancias entregadas por los satellites no converjan en un punto, se entregara la posicion extrapolada de la nave imperial, pero no sin antes notificar al usuario en el response con una variable 'warning' que le indica que la ubicacion es aproximada dado que no hay convergencia.
- Para los servicios split se realizo los calculos y recopilacion del mensaje con el patron singleton, se penso hacerlo mas general con una base de daos que almacenara la informacion de los satellites con un id, pero dato que el docuemtno no era claro hacerca de si los satellites se podian comunicar entre ellos para informar el mismo mensaje con un id unico, se asumio que los tres satelites envian informacion relativa al mismo mensaje. Aun asi se almacena los mensajes que son exitosamente decodificados.
- Para todos los servicios el formato de respuesta sera el siguiente (Figura A.)

Figura A.
![image](https://user-images.githubusercontent.com/65415988/156901588-121e0271-0b04-435a-ab9f-10396c122912.png)

  - timeStamp
  Timestamp del error para hacer seguimiento y log de los mismos.
  - uri
  Uri para identificar el servicicio que se esta ejecutando.
  - message
  Breve mensaje de error.
  - listMessage
  Se envia un array con un detalle de la falla.

## Servicios

- POST /topsecret
En este servicio se recibe un json segun la imagen (Figura 1.) y se retorna la ubicacion y la distancia segun la (Figura 2. o la Figura 3.).

Figura 1.
![image](https://user-images.githubusercontent.com/65415988/156901276-356cb2e8-0afc-45a5-ad6d-8518fe56eda1.png)

Figura 2.
![image](https://user-images.githubusercontent.com/65415988/156901334-6970b86d-f353-498a-bd7f-6760f0239ad7.png)

Figura 3.
![image](https://user-images.githubusercontent.com/65415988/156901386-bebd7b70-db97-4746-977c-e85f78537b4b.png)

- POST /topsecret_split/{satellite_name}
En este servicio se realiza la variacion en el request path (Figura 4.) y body (Figura 5.) y se debe enviar el nombre del satellite en el patch en la variable {satellite_name}, este servicio requiere el envio de los datos para los tres satellites (kenobi, skywalker, sato) se debe tener en cuanta que si se desea actualizar la informacion de un satellite existente se debe hacer uso del servicio /topsecret_split/update/{satellite_name} y si desea conocer cuales son lo satelites que ha ingresado puede hacer uso del servicio /topsecret_splitquery/. Respuesta exitosoa HTTP Status 200.

Figura 4.
![image](https://user-images.githubusercontent.com/65415988/156901486-86025b47-c408-4265-b4d6-1a6495a59bb1.png)

Figura 5.
![image](https://user-images.githubusercontent.com/65415988/156901507-6bec6c8e-7a86-4a15-98d0-c27f2f2a61b4.png)

- GET /topsecret_split
Este servicio retornara la posicion y el mensaje en caso de ser posible, este servicio aplica para la informacion de los satellites ingresada por medio del servicio POST /topsecret_split/{satellite_name}, en caso de no haber informacion de satelites se notificara mediante un error BadRequest 400. Este servicio tiene como formato de response la (Figura 6. o la Figura 7.).

Figura 6.
![image](https://user-images.githubusercontent.com/65415988/156901334-6970b86d-f353-498a-bd7f-6760f0239ad7.png)

Figura 7.
![image](https://user-images.githubusercontent.com/65415988/156901386-bebd7b70-db97-4746-977c-e85f78537b4b.png)

- POST /topsecret_split/update/{satellite_name}  [EXTRA]
En este servicio se realiza la actualizacion de la informacion de los satelites para la variante de consulta split, Respuesta exitosoa HTTP Status 200.

Figura 8.
![image](https://user-images.githubusercontent.com/65415988/156901486-86025b47-c408-4265-b4d6-1a6495a59bb1.png)

Figura 8.
![image](https://user-images.githubusercontent.com/65415988/156901507-6bec6c8e-7a86-4a15-98d0-c27f2f2a61b4.png)

- GET /topsecret_split/query/{satellite_name}  [EXTRA]
En este servicio se realiza la consulta de la informacion almacenada de los satelites para la variante split 

- GET /topsecret_repo  [EXTRA]
Este servicio permite consultar el log de todos los mensajes exitosos decodificados, adicional a eso peromite identificar el metodod en el que fue enviado el mensaje, split o normal.

## Swagger

![image](https://user-images.githubusercontent.com/65415988/156906215-5f6a8672-0d68-4ba6-8cbd-f08885d5c2a7.png)

## URL del Despliegue.

  - EndPoint: http://starwar-env-1.eba-2uecspmm.us-east-2.elasticbeanstalk.com
  - Swagger: {{EndPoint}}/swagger-ui/index.html
