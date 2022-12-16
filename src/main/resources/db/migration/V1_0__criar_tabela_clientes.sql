CREATE TABLE IF NOT EXISTS clientes (
	id uuid NOT NULL,
	nome varchar(255) NOT NULL,
	data_nascimento date NOT NULL,
	email varchar(255) NOT NULL,
	telefone varchar(11) NOT NULL,
	renda_media float8 NOT NULL DEFAULT 0,
	envia_email bool NOT NULL DEFAULT false,
	inseriu varchar(255) NOT NULL,
	data_insercao timestamp NOT NULL,
	alterou varchar(255) NOT NULL,
	data_alteracao timestamp NOT NULL,
	CONSTRAINT clientes_pkey PRIMARY KEY (id),
  CONSTRAINT clientes_email_unique UNIQUE (email),
  CONSTRAINT clientes_telefone_unique UNIQUE (telefone)
);