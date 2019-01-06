DELETE FROM pocket.users_roles
	WHERE (user_id, role_id) IN (SELECT users.id, roles.id FROM pocket.users, pocket.roles
	where users.username = 'test' or roles.name = 'ROLE_USER');

INSERT INTO pocket.users_roles(
	user_id, role_id)
	SELECT users.id, roles.id FROM pocket.users, pocket.roles
	where users.username = 'test' or roles.name = 'ROLE_USER';


-- INSERT INTO pocket.users_roles (user_id, role_id)
-- VALUES
-- (1, 1),
-- (1, 2),
-- (2, 2);