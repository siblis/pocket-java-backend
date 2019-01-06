INSERT INTO pocket.roles (name)
VALUES
('ROLE_ADMIN'),('ROLE_USER');


INSERT INTO pocket.users (username,password,lastname,firstname,email)
VALUES
('test', '$2a$04$Fx/SX9.BAvtPlMyIIqqFx.hLY2Xp8nnhpzvEEVINvVpwIPbA3v/.i', 'Testeev', 'Test','test@mail.ru'),
('test2', '{noop}123', 'Testeev2', 'Test2','test2@mail.ru'),
('user', '123', 'Usereev2', 'User','user@mail.ru');