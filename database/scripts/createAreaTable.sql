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