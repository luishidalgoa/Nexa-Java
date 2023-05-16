const gulp = require('gulp');
const { exec } = require('child_process');

gulp.task('backup-db', function (cb) { //INTRODUCIR EN LA RUTA LA VARIABLE DE ENTORNO "mysqldump". En mi caso falla incluso agregandolo
    exec('"C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe" -h localhost -u root -p nexadatabase > ./SQL_file/script.sql', function (err, stdout, stderr) {
        console.log(stdout);
        console.log(stderr);
        cb(err);
    });
});
