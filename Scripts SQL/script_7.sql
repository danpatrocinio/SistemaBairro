ALTER TABLE usuarios CHANGE cod_usuarios cod_usuarios INT(15) NOT NULL AUTO_INCREMENT;
ALTER TABLE usuarios CHANGE COLUMN senha senha VARCHAR(200) NOT NULL;
ALTER TABLE usuarios ADD COLUMN login VARCHAR(80) NOT NULL AFTER cod_usuarios;

INSERT INTO usuarios (cod_usuarios, login, senha, nome, tipo) VALUES (NULL, 'administrador', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'Administrador', 'a');
