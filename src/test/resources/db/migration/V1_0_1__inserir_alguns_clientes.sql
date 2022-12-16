INSERT INTO clientes(id, nome, data_nascimento, telefone, email, inseriu, data_insercao, alterou, data_alteracao) 
VALUES ('bd911320-480f-4bd9-a128-c4b2b570cec0', 'FULANO DA SILVA',  '1974-05-23', '91912345678', 'FULANO.SILVA@GMAIL.COM', 'SPRING', now(), 'SPRING', now()) 
    ON CONFLICT DO NOTHING;

INSERT INTO clientes(id, nome, data_nascimento, telefone, email, envia_email, renda_media, inseriu, data_insercao, alterou, data_alteracao) 
VALUES ('e66b91cc-d81d-49c8-81cf-c48b7b965cfb' , 'CICLANO DE SOUSA', '1990-10-13', '92987654321', 'CICLANO_SZ@HOTMAIL.COM', true, 10600, 'SPRING', now(), 'SPRING', now()) 
    ON CONFLICT DO NOTHING;