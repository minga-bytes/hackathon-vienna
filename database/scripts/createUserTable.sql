-- Table: public.user

DROP TABLE IF EXISTS public."user";

CREATE TABLE IF NOT EXISTS public."user"
(
    id INT GENERATED ALWAYS AS IDENTITY,
    username character varying(50) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT user_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."user"
    OWNER to postgres;