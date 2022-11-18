INSERT INTO minicurso.clientes(id, nome, data_nascimento, telefone, email, envia_email, renda_media, inseriu, data_insercao, alterou, data_alteracao) 
VALUES ('ad2e8ea9-08e7-4de8-9b4e-594734fd0857', 'FULANO DA SILVA',  '1994-05-23', '91912345678', 'FULANO.SILVA@GMAIL.COM', false, 3500, 'SPRING', now(), 'SPRING', now()) 
    ON CONFLICT DO NOTHING;

INSERT INTO minicurso.clientes(id, nome, data_nascimento, telefone, email, envia_email, renda_media, inseriu, data_insercao, alterou, data_alteracao) 
VALUES ('264d198d-60a9-48da-b4fd-f278d3a881d0' , 'CICLANO DE SOUSA', '1985-10-13', '92987654321', 'CICLANO_SZ@HOTMAIL.COM', true, 10600, 'SPRING', now(), 'SPRING', now()) 
    ON CONFLICT DO NOTHING;