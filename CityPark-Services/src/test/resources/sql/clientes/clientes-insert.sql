insert into USUARIOS (id, username, password, role) values (100, 'noobmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role) values (101, 'starmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (102, 'leonelmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role) values (103, 'figueiredomaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (10, 'Star Master', '94392380033', 101);
insert into CLIENTES (id, nome, cpf, id_usuario) values (20, 'Leonel Master', '59644826000', 102);