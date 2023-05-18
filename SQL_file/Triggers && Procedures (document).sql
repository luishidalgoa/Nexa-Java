#-----------------------TRIGGERS------------------

CREATE TRIGGER create_User_options
    AFTER INSERT ON nexadatabase.user
    FOR EACH ROW
BEGIN
    INSERT INTO nexadatabase.user_options (user_name)
    VALUES (NEW.user_name);
END;

CREATE TRIGGER delete_user_options
    BEFORE DELETE ON nexadatabase.user
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.user_options
    where user_options.user_name=OLD.user_name;
END;

CREATE TRIGGER delete_publication_like
    BEFORE DELETE ON nexadatabase.publication
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.likes
    where id_publication=OLD.id;
END;

CREATE TRIGGER delete_publication_share
    BEFORE DELETE ON nexadatabase.publication
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.share
    where id_publication=OLD.id;
END;

CREATE TRIGGER delete_publications
    BEFORE DELETE ON nexadatabase.user
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.publication
    where publication.user_name=OLD.user_name;
END;
# Crear trigger que elimine una coleccion cuando se elimine un usuario
CREATE TRIGGER delete_collections
    BEFORE DELETE ON nexadatabase.user
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.collection
    where collection.user_name=OLD.user_name;
END;
#Crear trigger que elimine la referencia de la tabla publications_collections cuando se borre la coleccion
CREATE TRIGGER delete_publications_collection
    BEFORE DELETE ON nexadatabase.collection
    FOR EACH ROW
BEGIN
    DELETE FROM nexadatabase.publications_collection
    where publications_collection.user_name LIKE OLD.user_name and publications_collection.name LIKE OLD.name;
END;
#--------------------PROCEDURES----------------------
    #--------------PublicationDAO----------------
    DELIMITER //
        CREATE PROCEDURE `PublicationfindById`(IN `id_publication` INTEGER)
            BEGIN
            SELECT * FROM nexadatabase.publication where id=`id_publication`;
        END //
        CREATE PROCEDURE `PublicationfindByUser`(IN `name` VARCHAR(15))
            BEGIN
            SELECT * FROM nexadatabase.publication where user_name=`name`;
        END //
        CREATE PROCEDURE `PublicationfindAll`()
            BEGIN
            SELECT * FROM nexadatabase.publication group by id;
        END //
        CREATE PROCEDURE `PublicationSave`(IN `value` varchar(255),IN `name` varchar(15))
            BEGIN
            INSERT INTO nexadatabase.publication(publication_date, text, user_name) value (CURRENT_TIMESTAMP,`value`,`name`);
        END //
        CREATE PROCEDURE `PublicationDelete`(IN `id_publication` INTEGER)
            BEGIN
            Delete from nexadatabase.publication where id=`id_publication`;
        END //
        CREATE PROCEDURE `PublicationUpdate`(IN `id_publication` INTEGER,IN `value` VARCHAR(255))
            BEGIN
                UPDATE nexadatabase.publication SET text=`value` where id=id_publication;
        END //
        CREATE PROCEDURE `PublicationFindBySocial`(IN `name` VARCHAR(50))
            BEGIN
                SELECT DISTINCT p.* FROM  nexadatabase.publication p LEFT JOIN nexadatabase.share s on p.`id`=s.`id_publication` where p.user_name=`name` OR s.user_name=`name` group by p.id;
        end //
    #----------------LikeDAO---------------
    # CONSULTA CON RIGHT JOIN
        CREATE PROCEDURE `LikeFindById`(IN `id_publication` INTEGER)
            BEGIN
                select l.* from nexadatabase.likes l INNER JOIN nexadatabase.publication p on l.id_publication=p.id where l.id_publication=`id_publication`;
        END //
    #SUBCONSULTA
        CREATE PROCEDURE `LikeFindLike`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Select * from nexadatabase.likes where id_publication in(select id from nexadatabase.publication where id=`id_pub`) and user_name LIKE(select user_name from nexadatabase.user where user_name like`name`);
        END //
        CREATE PROCEDURE `LikeSave`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Insert into nexadatabase.likes(id_publication,user_name,date_like) values(`id_pub`,`name`,CURRENT_TIMESTAMP);
        END //
        CREATE PROCEDURE `LikesDelete`(IN `id_pub` INTEGER)
            BEGIN
                Delete from nexadatabase.likes where id_publication=`id_pub`;
        END //
        CREATE PROCEDURE `LikeDelete`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Delete from nexadatabase.likes where id_publication=`id_pub` and user_name=`name`;
        END //
        #---------------SHARE DAO--------------
        CREATE PROCEDURE `ShareFindById`(IN id_pub INTEGER)
            BEGIN
                select d.* from nexadatabase.share d RIGHT JOIN nexadatabase.publication p on d.id_publication=p.id where d.id_publication=id_pub;
        END //
        CREATE PROCEDURE `ShareFindShare`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Select * from nexadatabase.share where id_publication in(select id from nexadatabase.publication where id=`id_pub`) and user_name in(select user_name from nexadatabase.user where user_name=`name`) group by id;
        END //
        CREATE PROCEDURE `ShareSave`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Insert into nexadatabase.share(id_publication,user_name,date_shared) values(`id_pub`,`name`,CURRENT_TIMESTAMP);
        END //
        CREATE PROCEDURE `SharesDelete`(IN `id_pub` INTEGER)
            BEGIN
                Delete from nexadatabase.share where id_publication=`id_pub`;
        END //
        CREATE PROCEDURE `ShareDelete`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Delete from nexadatabase.share where id_publication=`id_pub` and user_name=`name`;
        END //
        #------------------User Options DAO---------------
        CREATE PROCEDURE `User_optionsFindLanguage`(IN `name` VARCHAR(15))
            BEGIN
                SELECT language from nexadatabase.user_options where user_name=`name`;
        END //
        CREATE PROCEDURE `User_optionsUpdateLanguage`(IN `name` VARCHAR(15),IN `lang` varchar(3))
            BEGIN
                UPDATE nexadatabase.user_options SET language=lang where user_name=name;
        END //
        #--------------User DAO----------------------
        CREATE PROCEDURE `UserFindAll`()
            BEGIN
                Select * from nexadatabase.user;
        END //
        CREATE PROCEDURE `UserSearchUser`(IN `name` VARCHAR(15))
        BEGIN
            Select * from nexadatabase.user where user_name=`name`;
        END //
        CREATE PROCEDURE `UserSave`(IN `name` VARCHAR(15),IN `password` VARCHAR(100),IN `bio` VARCHAR(255))
            BEGIN
                insert into nexadatabase.user(user_name,pass,biography) values(`name`,`password`,`bio`);
        END //
        CREATE PROCEDURE `UserDelete`(IN `name` VARCHAR(15))
            BEGIN
                Delete from nexadatabase.user where user_name=`name`;
        END //
        CREATE PROCEDURE `UserUpdateBiography`(IN `name` VARCHAR(15),IN `value` VARCHAR(255))
            BEGIN
                UPDATE nexadatabase.user set biography=`value` where user_name=`name`;
        END //
        #--------------Collection DAO-----------
        CREATE PROCEDURE `CollectionFindPublications`(IN `user` varchar(50),IN n varchar(20))
            BEGIN
                select id_publication from nexadatabase.publications_collection where user_name LIKE `user` and name LIKE `n`;
        END //
        CREATE PROCEDURE `CollectionFindByUser`(IN `user` varchar(50))
            BEGIN
                select DISTINCT name from nexadatabase.collection where user_name LIKE `user`;
        END //
        CREATE PROCEDURE `CollectionAdd`(IN `user` varchar(50),IN `n` VARCHAR(20))
            BEGIN
                INSERT INTO nexadatabase.collection(user_name, name)  VALUES(`user`,`n`);
        END //
        CREATE PROCEDURE `CollectionDelete`(IN `user` varchar(50),IN `n` VARCHAR(20))
            BEGIN
                DELETE from nexadatabase.collection where user_name LIKE `user` and name LIKE `n`;
        END //
    DELIMITER ;
#------------------------TEST PROCEDURES-----------
use nexadatabase;
#-------------PUBLICATIONS
call nexadatabase.`PublicationfindById`('52'); #introducir un id publicacion existente
call nexadatabase.`PublicationfindByUser`('Luis');
call nexadatabase.`PublicationfindAll`();
call nexadatabase.`Publicationsave`('Prueba call','Luis');
call nexadatabase.`Publicationdelete`('57'); #Introducir id de la publicacion de prueba
CALL nexadatabase.PublicationFindBySocial('Luis');
#-------------LIKES
call nexadatabase.`LikeFindById`('52');
call nexadatabase.`LikeFindLike`('136','Luis');
call nexadatabase.`LikeSave`('52','Jose');
call nexadatabase.`LikesDelete`('52');
call nexadatabase.`LikeDelete`('52','Jose');
#-------------Shares
call nexadatabase.`ShareFindById`('52');
call nexadatabase.`ShareFindShare`('52','Luis');
call nexadatabase.`ShareSave`('52','Jose');
call nexadatabase.`SharesDelete`('52');
call nexadatabase.`ShareDelete`('52','Jose');
#------------User options
call nexadatabase.`User_optionsFindLanguage`('Luis');
call nexadatabase.`User_optionsUpdateLanguage`('Luis','ES');
#------------User DAO
call nexadatabase.`UserFindAll`();
call nexadatabase.`UserSearchUser`('Luis');
call nexadatabase.`UserSave`('Jose','03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4','Hola soy jose');
call nexadatabase.`UserDelete`('Jose');
call nexadatabase.UserUpdateBiography('Luis','SFGEVFDA');

#Creamos la coleccion A de luis
call nexadatabase.CollectionAdd('Luis','A');
#Buscamos las publicaciones dentro de la coleccion A de Luis
call nexadatabase.CollectionFindPublications('Luis','A');
#Buscamos todas las colecciones del usuario Luis
call nexadatabase.CollectionFindByUser('Luis');
#Eliminamos la coleccion A
call nexadatabase.CollectionDelete('Luis','A');



