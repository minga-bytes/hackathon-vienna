-- Database: postgres

DROP DATABASE IF EXISTS postgres;

CREATE DATABASE postgres
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'German_Germany.1252'
    LC_CTYPE = 'German_Germany.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

COMMENT ON DATABASE postgres
    IS 'default administrative connection database';


-- Table: public.user

DROP TABLE IF EXISTS public."user";

CREATE TABLE IF NOT EXISTS public."user"
(
    id integer NOT NULL DEFAULT nextval('user_id_seq'::regclass),
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;


-- Table: public.area

DROP TABLE IF EXISTS public.area;

CREATE TABLE IF NOT EXISTS public.area
(
    geo geometry[],
    user_id INT,
    created_at timestamp without time zone NOT NULL,
    id INT GENERATED ALWAYS AS IDENTITY,
    CONSTRAINT area_pkey PRIMARY KEY (id),
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES public.user(id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.area
    OWNER to postgres;

-- Prefill Table: public.user

INSERT INTO public.user(
	username, created_at)
	VALUES ('Timm', '12.05.2022');

INSERT INTO public.user(
	username, created_at)
	VALUES ('Jonas', '01.01.2022');

INSERT INTO public.user(
	username, created_at)
	VALUES ('Dominik', '02.02.2022');

INSERT INTO public.user(
	username, created_at)
	VALUES ('Aleksei', '03.03.2022');
    
    
    