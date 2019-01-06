DROP TABLE IF EXISTS pocket.roles CASCADE;
CREATE TABLE pocket.roles
(
    id serial NOT NULL,
    name character varying(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT roles_name_key UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE pocket.roles
    OWNER to postgres;

--

DROP TABLE IF EXISTS pocket.users CASCADE;
CREATE TABLE pocket.users
(
    id serial NOT NULL,
    username varchar(50) NOT NULL,
    password character varying(100) NOT NULL,
    lastname varchar(50) NOT NULL,
    firstname varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT users_username_key UNIQUE (username),
    CONSTRAINT users_email_key UNIQUE (email)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE pocket.users
    OWNER to postgres;

--

DROP TABLE IF EXISTS pocket.users_roles;
CREATE TABLE pocket.users_roles
(
    user_id integer NOT NULL,
    role_id integer NOT NULL,

    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role_id FOREIGN KEY (role_id)
        REFERENCES pocket.roles (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id)
        REFERENCES pocket.users (id)
        ON DELETE NO ACTION ON UPDATE NO ACTION
)
WITH (
    OIDS = FALSE
);