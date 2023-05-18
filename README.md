# Nexa: ¡ Conecta !

<p align="center">
  <img src="https://github.com/luishidalgoa/Nexa-Java/blob/main/src/main/resources/Logo.png" alt="Logo">
</p>

Nexa es un proyecto de programación realizado 100% con Java. Es una red social que permite a los usuarios crear y compartir publicaciones, visitar perfiles, dar likes, configurar el idioma y tener colecciones de publicaciones favoritas.

## Características principales

- Permite crear e iniciar sesión con usuarios
- Los usuarios pueden realizar publicaciones con texto e imágenes
- Los usuarios pueden visitar perfiles de otros usuarios incluido el suyo propio
- Los usuarios pueden configurar el idioma de la aplicación entre español e inglés
- Los usuarios pueden dar likes a las publicaciones que les gusten
- Los usuarios pueden compartir otras publicaciones. Al compartirlas serán visibles en su propio perfil
- Los usuarios pueden tener colecciones de publicaciones. En ellas pueden almacenar sus publicaciones favoritas en sus distintas colecciones

## Diagrama de clases:

<p align="center">
  <img src="https://github.com/luishidalgoa/Nexa-Java/blob/main/src/main/resources/Main.jpg" alt="Logo">
</p>

## Diagrama E/R

<p align="center">
  <img src="https://github.com/luishidalgoa/Nexa-Java/blob/main/src/main/resources/BBDD.jpg" alt="Logo">
</p>

## Requisitos cumplidos:
Se	debe	realizar	un	diseño	de	la	solución,	documentando	esta	fase	en	al	menos:
- Modelo	E-R ✅
- Diagrama	de	clases ✅
- Diseño	de	pantallas/casos	de	uso 🟧
En	el	diseño	de	la	base	de	datos	se	solicita	como	requisito	mínimo:
- Al	menos	una	relación	1:N /	N:M,	con	un	mínimo	de	tres	tablas	relacionadas. ✅
- Implementación	en	MySQL ✅
- Opcionalmente,	(se	valora),	implementación	en	H2. 
- Configuración	de	la	conexión	a	la	base	de	datos	almacenado	en	XML	(se	valora	uso	de JAXB) ✅
En	el	diseño	de	clases	se	solicita:
- Empleo	de	interfaces ✅
- Empleo	de	clases	abstractas ✅
- Sobrecarga	y	sobreescritura	de	métodos ✅
- Herencia	optimizada. ✅ __Clase Like, Share, Controllers__
- Desarrollo	de	DAOS,	de	forma	eficaz,	implementando	aproximaciones	lazy	y	eager. ✅ __ En casi todos los DAOS __
- Empleo	de	Colecciones	adecuadas	a	cada	situación. ✅ __ Se utilizo HashMaps , y HashSet
El	desarrollo	se	realizará	en	el	IDE	que	más	convenga	con	los	siguientes	requisitos:
- JDK	>8
- Emplear	Maven	para	la	estructura	del	proyecto ✅
- Emplear	Github	para	control	de	repositorios ✅
- Emplear	JavaFX para	la	interfaz	gráfica ✅
En	las	funcionalidades	básica	se	solicita:
- Carga	de	conexión	y	control	de	la	misma. ✅
- CRUD	con	base	de	datos,	además	de	las	operaciones	básicas,	se	valorarán	el	empleo	 ✅
de	subconsultas	o	JOINS.
- Control	de	impedancia	Relación	- Objeto.	Se	valorarán	el	control	de	transacciones. ✅ Existen varios DTO
- Utilidad	real	de	la	aplicación. ✅
- Diseño	intuitivo	de	la	interfaz. ✅
- Edición	avanzada	de	datos:	multimedia… ❌
- Uso	de	stream y	expresiones	lambda. ✅ Clase utils  OrderByTime() ... Entre otros
- Log	de	errores	en	ficheros. ✅
- Herramientas	de	copias	de	seguridad	(¿consola?). ✅ __ Existe un archivo Gulp encargado de la extraccion de la bbdd ademas de otras opciones explicadas en esta documentacion__
- Uso	de	datos	avanzados:	fechas,	expresiones	regulares… ✅

# Pautas para la instalación

## Requisitos minimos:
  - Java FX JDK 20.0.1 o posterior:  <a href="https://openjfx.io/">Link
  - Version 1.19 o posterior de Java : <a href="https://www.oracle.com/java/technologies/javase/jdk19-archive-downloads.html">Link 
  
  
### Clonar el proyecto

Para clonar este proyecto, puedes ejecutar el siguiente comando en tu terminal:


```
git clone https://github.com/luishidalgoa/Nexa-Java
```

### Cargar Base de datos (REQUISITO PRIMORDIAL)

__Tu puedes ejecutar una extraccion de la bbdd en un script.sql con el comando__

```
mysqldump -h localhost -u root -p nexadatabase > ./SQL_file/script.sql
```
__o desde la terminal ejecutando el siguiente comando si tienes Node + NPM + Gulp__ 

```
gulp backup-db
```

Para instalar la aplicación deberán descargarse el fichero Script.SQL : Link del SQL generado : <a href="https://github.com/luishidalgoa/Nexa-Java/blob/main/SQL_file/script.sql">Link

Además deberán tener algunos requisitos mínimos como la última versión de Java instalado. Para ejecutar la aplicación deberán ejecutar el archivo Nexa.Jar: Link
   
  ### Como ejecutar el .JAR 
  Deberan tener la ultima version del JDK como se menciono en el __#Requisitos minimos__. Este JDK debera estar en la ruta 
  "C:\Program Files\Java\"
  
### ¡IMPORTANTE! ejecutar el siguiente comando 
  
   ```
  java --module-path "C:\Program Files\Java\javafx-sdk-20.0.1\lib" --add-modules javafx.controls,javafx.fxml -jar Nexa-Java.jar
  ```
  
## Video explicativo sobre Nexa

Para ver un video explicativo sobre el funcionamiento y las funcionalidades de Nexa, pueden acceder al siguiente enlace: [Link](https://youtu.be/R2uRt105Nk0)

