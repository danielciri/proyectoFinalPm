# Instalacion y configuracion 
Para poder poner en marcha nuestra aplicacion, es necesario disponer de una conexion a internet y configurar los parametros de conexion, como lo son la ip del movil, la ip del servidor y ademas el puerto. Tanto la ip del movil como del servidor han de ser la misma, en caso de no serlo podria tendriamos un problema al obtener datos desde el servidor. Una ves realizado los pasos anteriores arrancamos la aplicacion.

# Funcionamiento aplicacion
 Al arrancar nuestra aplicacion se nos cargara la siguiente vista: 

<img src="capturas/1.png"
     alt="Markdown Monster icon" />

como podemos observar directamente nos mostrara la frase programada para dicho dia, una ves  hemos observado la frase del dia podemos dirigirnos a a el menu ubicado en la parte superior derecha, y cambiar los parametros de conexion con el servidor y ademas puede escoger la hora en la cual quiere que se active la alarma

# Pantalla de preferencias aplicacion

### Parametros de conexion
Si pulsamos en la opcion explicada anteriormente se cargaria la siguiente vista que serian las preferencias:

<img src="capturas/2.png"
     alt="Markdown Monster icon" />

* Direccion Ip: Podemos cambiar la direccion ip, con tan solo pulsar donde dice "Introduzca la direccion ip". Por defecto ya viene una Ip. A continuacion observaremos

<img src="capturas/3.png"
     alt="Markdown Monster icon" />



* Puerto: Tambien tenemos la opcion de escoger un puerto, para ello le mostraremos una serie de puertos disponibles:

<img src="capturas/4.png"
     alt="Markdown Monster icon" />

### Configuracion de alarma

* Hora para mostrar: Indicaremos la hora a la cual sonara la alarma. La hora debe ser en sistema de 24hrs.


<img src="capturas/5.png"
     alt="Markdown Monster icon" />

* Miutos: En este apartado tenemos que indicar el minuto exacto en el cual esa alarma se activara:

<img src="capturas/5.png"
     alt="Markdown Monster icon" />


## Menu Navigation Drawer
Cuando abriomos el  menu desplegable, se cargara la siguiente vista:

<img src="capturas/7.png"
     alt="Markdown Monster icon" />

## Frase del Dia 
Si pulsase en frase del dia, se le cargara la frase correspondiente del dia:

<img src="capturas/8.png"
     alt="Markdown Monster icon" />

## Autores:
Cuando el usuarip pulsase en autores apreciaria la siguiente lista de los autores:

<img src="capturas/9.png"
     alt="Markdown Monster icon" />

Si pulsamos sobre uno de los autores, se mostrarian las frases del autor:

<img src="capturas/10.png"
     alt="Markdown Monster icon" />

## Categorias

Cuando pulsamos en categorias, nos mostrara una lista de todas las categorias que disponemos. A continuacion apreciaremos la imagen.

<img src="capturas/11.png"
     alt="Markdown Monster icon" />

Si pulsamos sobre alguna de estas categorias, se nos cargara  la frase correspondiente a la categoria seleccionada.

<img src="capturas/13.png"
     alt="Markdown Monster icon" />

## Iniciar Sesion(Modo admin)
Para acceder a esta seccion es solo administradores, por ellos cuando pulsemos dicha opcion se nos cargara la siguiente vista de login:

<img src="capturas/14.png"
     alt="Markdown Monster icon" />

debemos colocar el usuario y contrasena, en caso de que pulsaramos el checkbox ubicado en la parte inferior, nos guardaria el usuario y contrasena para el proximo logueo.

* En caso de loguearnos de manera correcta nos mostrara el siguiente mensaje: 
<img src="capturas/15.png"
     alt="Markdown Monster icon" />
     
* En caso de loguearnos con usuario o contrasena incorrecta nos mostrara el siguiente mensaje: 

<img src="capturas/16.png"
     alt="Markdown Monster icon" />

### Opciones Habilitadas:
al loguearnos como administradores se nos mostrara una seria de nuevas opciones, y ademas se nos cambiara la imagen de nuestro nav_header, a continuacion observaremos:

<img src="capturas/17.png"
     alt="Markdown Monster icon" />

### Anadir o modificar Autor:
Al pulsar sobre esta opcion, se nos cargara un dialogo, que mostrara lo siguiente:

 <img src="capturas/18.png"
     alt="Markdown Monster icon" />

* Anadir: Al pulsar el boton anadir nos cargara un formulario con una serie de datos que debemos rellenar para poder insertar el autor:

 <img src="capturas/19.png"
     alt="Markdown Monster icon" />

ademas si nos escribimos nada en el formulario nos mostrara mensajes de error diciendo que el campo es requerido. Una ves rellenados los campos ya podemos insertar nuestro autor.

* Modificar: Al pulsarlo se nos mostrara una lista de autores y alli escogeremos cual queremos editar:

 <img src="capturas/9.png"
     alt="Markdown Monster icon" />

una  ves escogido el autor a editar, nos cargara un formulario con los datos del autor seleccionado, a continuacion lo observamos.

 <img src="capturas/20.png"
     alt="Markdown Monster icon" />

realizamos los cambios que deseemos y luego pulsamos en el boton de update.

### Anadir o modificar Categoria:

* Anadir: Cuando pulsemos el boton de anadir se nos cargara un formulario que observaremos a continuacion:

 <img src="capturas/21.png"
     alt="Markdown Monster icon" />

insertamos los valores y luego pulsamos el boton y listo.

* Modificar: Cuando pulsemos modificar se nos cargara una lista con todas las categorias:
 <img src="capturas/22.png"
     alt="Markdown Monster icon" />

debemos escoger la que deseemos modificar, cuando la escojamos se nos cargara un formulario con la categoria seleccionada:

 <img src="capturas/24.png"
     alt="Markdown Monster icon" />

realizamos los cambios que deseemos y pulsamos el boton update.

### Anadir o modificar Frase:

* Anadir: Al pulsar anadir frase se nos cargara un formulario, con unos campos y ademas tambien unos desplegables con las opciones de las categorias disponibles y autores. A continuacion observaremos:

 <img src="capturas/25.png"
     alt="Markdown Monster icon" />

rellenamos los campos necesario y pulsamos el boton de insertar.

* Modificar: Al pulsar modificar se nos cargaran las frases disponibles como observaremos a continuacion:


 <img src="capturas/26.png"
     alt="Markdown Monster icon" />

al seleccionar la frase que deseemos modificar se nos cargara un formulario con los datos de la frase, a continuacion lo observaremos 
<img src="capturas/27.png"
     alt="Markdown Monster icon" />

una ves realizamos los cambios deseados pulsamos en modificar frase y listo.

## Alarma
* Si queremos que nuestra alarma suene a la hora definida debemos cambiarla como ya se explico anteriormente, una ves programada la hora. Se nos mostrara dicha alarma acontinuacion observaremos la notificacion de alarma:

<img src="capturas/28.png"
     alt="Markdown Monster icon" />
     
al pulsar la notifiacion nos abrirara la frase del dia.