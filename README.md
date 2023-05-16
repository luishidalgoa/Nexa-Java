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

## Pautas para la instalación
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

Además deberán tener algunos requisitos mínimos como la última versión de Java instalado. Para ejecutar la aplicación deberán ejecutar el archivo Nexa.Jar: LINK

## Video explicativo sobre Nexa

Para ver un video explicativo sobre el funcionamiento y las funcionalidades de Nexa, pueden acceder al siguiente enlace: 
