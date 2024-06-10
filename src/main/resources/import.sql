insert into article (title, description) values ('Loco', "Perro");


--- Datos iniciales
-- Add Roles
insert into role (role) values ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_TEACHER'), ('ROLE_PREACHER'), ('ROLE_ACCOUNTANT');

-- Add articles's mapping

--- GET

insert into mapping (type, url) values ('GET',"/api/article/v1/");
insert into mapping (type, url) values ('GET',"/api/article/v1/un-checked");
insert into mapping (type, url) values ('GET',"/api/article/v1/{topic}");
insert into mapping (type, url) values ('GET',"/api/article/v1/{idArticle}");

--- POST

insert into mapping (type, url) values ('POST',"/api/article/v1/add-article/{idUser}");
insert into mapping (type, url) values ('POST',"/api/article/v1/add-content/{idArticle}/{idUser}");
insert into mapping (type, url) values ('POST',"/api/article/v1/add-comment/{idArticle}/{idUser}");

--- PUT

insert into mapping (type, url) values ('PUT',"/api/article/v1/video/{idArticle}/{idUser}");
insert into mapping (type, url) values ('PUT',"/api/article/v1/addMultimedia/{idContent}/{idUser}/{tipe}");
insert into mapping (type, url) values ('PUT',"/api/article/v1/update-content/{idContent}/{idUser}");
insert into mapping (type, url) values ('PUT',"/api/article/v1/update-article/{idArticle}/{idUser}");
insert into mapping (type, url) values ('PUT',"/api/article/v1/update-comment/{idComment}/{idUser}");

--- DELETE

insert into mapping (type, url) values ('DELETE',"/api/article/v1/delete-article/{idArticle}/{idUser}");
insert into mapping (type, url) values ('DELETE',"/api/article/v1/delete-content/{idArticle}/{idContent}/{idUser}");
insert into mapping (type, url) values ('DELETE',"/api/article/v1/delete-comment/{idComment}/{idUser}");

--15

-- ADD ROLE_MAPPING - ARTICLE
--- USER
insert into role_mapping (mapping_id, role_id) values (1,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1), (9,1), (10, 1), (11,1), (12, 1),
(13,1), (14,1), (15, 1);

-- ADMIN
insert into role_mapping (mapping_id, role_id) values (1,2), (2,2), (3,2), (4,2), (5,2), (6,2), (7,2), (8,2), (9,2), (10, 2), (11,2), (12, 2),
(13,2), (14,2), (15,2);

-- TEACHER

insert into role_mapping (mapping_id, role_id) values (1,3), (2,3), (3,3), (4,3), (5,3), (6,3), (7,3), (8,3), (9,3), (10, 3), (11,3), (12, 3),
(13,3), (14,3), (15,3);

-- PREACHER

insert into role_mapping (mapping_id, role_id) values (1,4), (2,4), (3,4), (4,4), (5,4), (6,4), (7,4), (8,4), (9,4), (10, 4), (11,4), (12, 4),
(13,4), (14,4), (15,4);

-- Add Course's mapping 
-- GET
-- ..16
insert into mapping (type, url) values ('GET',"/api/course/v1/");
insert into mapping (type, url) values ('GET',"/api/course/v1/{idCourse}");

-- POST

insert into mapping (type, url) values ('POST',"/api/course/v1/add");

-- PUT

insert into mapping (type, url) values ('PUT',"/api/course/v1/update/{idCourse}/{idUser}");
insert into mapping (type, url) values ('PUT',"/api/course/v1/add-teacher/{idTeacher}/{idCourse}/{idUser}");
insert into mapping (type, url) values ('PUT',"/api/course/v1/update-tutor/{idUser}/{idCourse}");

-- DELETE

insert into mapping (type, url) values ('DELETE',"/api/course/v1/delete/{idCourse}");
insert into mapping (type, url) values ('DELETE',"/api/course/v1/delete-teacher/{idTeacher}/{idCourse}/{idUser}");
-- 23
-- ADD ROLE_MAPPING - COURSE
-- USER 
insert into role_mapping (mapping_id, role_id) values (16,1), (17,1);

-- ADMIN 
insert into role_mapping (mapping_id, role_id) values (18, 2), (19,2), (20,2), (21,2),
(22,2), (23,2);

-- TEACHER
insert into role_mapping (mapping_id, role_id) values (19, 3), (20, 3), (23, 3);

-- PREACHER
insert into role_mapping (mapping_id, role_id) values (18, 4), (19,4), (20,4), (21,4),
(22,4), (23,4);

-- Add Event's mapping
--From 24
--GET 
insert into mapping (type, url) values ('GET',"/api/event/v1/");
insert into mapping (type, url) values ('GET',"/api/event/v1/{idEvent}");
--POST
insert into mapping (type, url) values ('POST',"/api/event/v1/add");
--PUT
insert into mapping (type, url) values ('PUT',"/api/event/v1/update/{idEvent}");
insert into mapping (type, url) values ('PUT',"/api/event/v1/update-organizer/{idUser}/{idEvent}");
insert into mapping (type, url) values ('PUT',"/api/event/v1/add-assistent/{idUser}/{idEvent}");
--DELETE
insert into mapping (type, url) values ('DELETE',"/api/event/v1/delete/{idEvent}");
insert into mapping (type, url) values ('DELETE',"/api/event/v1/delete-assistent/{idUser}/{idEvent}");
--- 31
-- ADD ROLE_MAPPING - EVENT
-- USER
insert into role_mapping (mapping_id, role_id) values (24,1), (25,1), (29,1), (31,1);
-- ADMIN 
insert into role_mapping (mapping_id, role_id) values (26,2), (27,2), (28,2), (30,2);
-- Not special permissions for teacher role
-- PREACHER
insert into role_mapping (mapping_id, role_id) values (26,2), (27,2), (28,2), (30,2);
-- Add inscription's mapping
--FROM 32
---GET
insert into mapping (type, url) values ('GET',"/api/inscription/v1/{name}");
insert into mapping (type, url) values ('GET',"/api/inscription/v1/{idStudent}/{idUser}");
---POST
insert into mapping (type, url) values ('POST',"/api/inscription/v1/add/{idUser}/{idInscription}");
---PUT
insert into mapping (type, url) values ('PUT',"/api/inscription/v1/update/{value}/{idInscription}/{idUser}");
---DELETE
insert into mapping (type, url) values ('DELETE',"/api/inscription/v1/delete/{idInscription}/{idUser}");
-- ADD ROLE_MAPPING - EVENT
--...36
-- USER
insert into role_mapping (mapping_id, role_id) values (33,1), (34,1), (35,1), (36,1);
-- ADMIN
insert into role_mapping (mapping_id, role_id) values (32,2);
-- TEACHER
insert into role_mapping (mapping_id, role_id) values (32,3);
-- PREACHER
insert into role_mapping (mapping_id, role_id) values (32,4);
--Add Message's mapping
-- From 37
--GET
insert into mapping (type, url) values ('GET',"/api/message/v1/{id}");
--POST
insert into mapping (type, url) values ('POST',"/api/message/v1/add/{idSender}/{idReceiver}");
--DELETE
insert into mapping (type, url) values ('DELETE',"/api/message/v1/delete/{idUser}/{idMessage}");
insert into mapping (type, url) values ('DELETE',"/api/message/v1/delete-dialog/{idUser}/{idMessage}");
insert into mapping (type, url) values ('DELETE',"/api/message/v1/delete-all/{idUser}");
--41
-- ADD ROLE_MAPPING - MESSAGE
insert into role_mapping (mapping_id, role_id) values (37,1), (38,1), (39,1), (40,1), (41,1);









