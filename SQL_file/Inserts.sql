CREATE PROCEDURE `PublicationSave`(IN `value` varchar(255),IN `name` varchar(15))
            BEGIN
            INSERT INTO nexadatabase.publication(publication_date, text, user_name) value (CURRENT_TIMESTAMP,`value`,`name`);
END //

CREATE PROCEDURE `LikeSave`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Insert into nexadatabase.likes(id_publication,user_name,date_like) values(`id_pub`,`name`,CURRENT_TIMESTAMP);
END //

CREATE PROCEDURE `ShareSave`(IN `id_pub` INTEGER,IN `name` VARCHAR(15))
            BEGIN
                Insert into nexadatabase.share(id_publication,user_name,date_shared) values(`id_pub`,`name`,CURRENT_TIMESTAMP);
END //

CREATE PROCEDURE `UserSave`(IN `name` VARCHAR(15),IN `password` VARCHAR(100),IN `bio` VARCHAR(255))
            BEGIN
                insert into nexadatabase.user(user_name,pass,biography) values(`name`,`password`,`bio`);
END //

CREATE PROCEDURE `CollectionAdd`(IN `user` varchar(50),IN `n` VARCHAR(20))
            BEGIN
                INSERT INTO nexadatabase.collection(user_name, name)  VALUES(`user`,`n`);
END //