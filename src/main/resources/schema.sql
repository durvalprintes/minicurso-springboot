CREATE SCHEMA IF NOT EXISTS minicurso;
CREATE TABLE IF NOT EXISTS minicurso.clientes (
  id UUID NOT NULL,
  nome VARCHAR(255) NOT NULL, 
  data_nascimento DATE NOT NULL, 
  telefone VARCHAR(11) NOT NULL, 
  email VARCHAR(255) NOT NULL, 
  envia_email BOOLEAN DEFAULT TRUE, 
  renda_media float8 DEFAULT 0, 
  created_date timestamp,
  modified_date timestamp,
  PRIMARY KEY (id),
  CONSTRAINT unique_telefone UNIQUE (telefone),
  CONSTRAINT unique_email UNIQUE (email));