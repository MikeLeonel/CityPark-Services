insert into USUARIOS (id, username, password, role)
    values (18, 'noobmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_ADMIN');
insert into USUARIOS (id, username, password, role)
    values (19, 'starmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (20, 'leonelmaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');
insert into USUARIOS (id, username, password, role)
    values (22, 'figueiredomaster@email.com', '$2a$12$fxK72wEyg70X/3.hcQZa.uqNTkhtJ.OeGu4frbwkHJB3.9JobITvy', 'ROLE_CLIENTE');

insert into CLIENTES (id, nome, cpf, id_usuario) values (27, 'Star Master', '94392380033', 19);
insert into CLIENTES (id, nome, cpf, id_usuario) values (32, 'Leonel Master', '59644826000', 20);
insert into CLIENTES (id, nome, cpf, id_usuario) values (33, 'Figueiredo Master', '26753289011', 22);

insert into vagas (id, codigo, status) values (1, 'A-01', 'OCUPADA');
insert into vagas (id, codigo, status) values (3, 'A-02', 'OCUPADA');
insert into vagas (id, codigo, status) values (5, 'A-04', 'OCUPADA');
insert into vagas (id, codigo, status) values (6, 'A-05', 'OCUPADA');
insert into vagas (id, codigo, status) values (8, 'A-06', 'OCUPADA');


insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20240815-185217', 'ABC-0001', 'RENOUT', 'SANDERO', 'AZUL', '2024-08-15 15:52:17', 27, 1);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20240815-205221', 'ABC-0002', 'VOLKSWAGEN', 'BRASÍLIA', 'AMARELA', '2024-08-15 17:52:21', 32, 3);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20240816-005556', 'ABC-0003', 'VOLKSWAGEN', 'FUSCA', 'PRETO', '2024-08-15 21:55:56', 33, 5);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20240917-191545', 'ABC-0004', 'VOLKSWAGEN', 'KOMBI', 'BRANCO', '2024-09-17 16:15:45', 33, 6);
insert into clientes_tem_vagas (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    values ('20240917-191809', 'ABC-0004', 'VOLKSWAGEN', 'GOL', 'VERDE', '2024-09-17 16:18:09', 33, 8);
