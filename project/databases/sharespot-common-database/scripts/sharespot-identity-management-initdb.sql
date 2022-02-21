SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE IF EXISTS identity;

CREATE DATABASE identity WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE identity OWNER TO "user";

\connect identity

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

ALTER SCHEMA public OWNER TO "user";

COMMENT ON SCHEMA public IS 'standard public schema';

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.device (
    persistence_id bigint NOT NULL,
    oid character varying(255)
);

ALTER TABLE public.device OWNER TO "user";

CREATE TABLE public.device_permission (
    persistence_id bigint NOT NULL,
    domain_oid character varying(255),
    type integer NOT NULL,
    device_persistence_id bigint
);

ALTER TABLE public.device_permission OWNER TO "user";

ALTER TABLE public.device_permission ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.device_permission_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

ALTER TABLE public.device ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.device_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.domain (
    persistence_id bigint NOT NULL,
    name character varying(255),
    oid character varying(255),
    path text[]
);

ALTER TABLE public.domain OWNER TO "user";

ALTER TABLE public.domain ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.domain_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

CREATE TABLE public.tenant (
    persistence_id bigint NOT NULL,
    domains text[],
    email character varying(255),
    name character varying(255),
    oid character varying(255)
);

ALTER TABLE public.tenant OWNER TO "user";

ALTER TABLE public.tenant ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.tenant_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

INSERT INTO public.domain (persistence_id, name, oid, path) VALUES (1, 'root', '6740fe77-7a1b-449f-a437-bfcb4398ecc5', '{6740fe77-7a1b-449f-a437-bfcb4398ecc5}');
INSERT INTO public.domain (persistence_id, name, oid, path) VALUES (2, 'unallocated', '0539aab1-97f3-4b68-b138-1a69fe1b44b7', '{6740fe77-7a1b-449f-a437-bfcb4398ecc5,0539aab1-97f3-4b68-b138-1a69fe1b44b7}');

INSERT INTO public.tenant (persistence_id, domains, email, name, oid) VALUES (1, '{6740fe77-7a1b-449f-a437-bfcb4398ecc5}', 'filipe.cruz@sensae.pt', 'Filipe Cruz', '6740fe77-7a1b-449f-a437-bfcb4398ecc5');

SELECT pg_catalog.setval('public.device_permission_persistence_id_seq', 1, false);

SELECT pg_catalog.setval('public.device_persistence_id_seq', 1, false);

SELECT pg_catalog.setval('public.domain_persistence_id_seq', 18, true);

SELECT pg_catalog.setval('public.tenant_persistence_id_seq', 1, true);

ALTER TABLE ONLY public.device_permission
    ADD CONSTRAINT device_permission_pkey PRIMARY KEY (persistence_id);

ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (persistence_id);

ALTER TABLE ONLY public.domain
    ADD CONSTRAINT domain_pkey PRIMARY KEY (persistence_id);

ALTER TABLE ONLY public.tenant
    ADD CONSTRAINT tenant_pkey PRIMARY KEY (persistence_id);

ALTER TABLE ONLY public.tenant
    ADD CONSTRAINT uk_192jkmibgcj10gdjx6u51kw3s UNIQUE (oid);

ALTER TABLE ONLY public.tenant
    ADD CONSTRAINT uk_1wuu4ilo8ya2tm94iswtp6ev1 UNIQUE (email);

ALTER TABLE ONLY public.device
    ADD CONSTRAINT uk_8nx9kguudoak57q55dki215mf UNIQUE (oid);

ALTER TABLE ONLY public.domain
    ADD CONSTRAINT uk_tlgokcy26phhe2qvkmft2kr2s UNIQUE (oid);

ALTER TABLE ONLY public.device_permission
    ADD CONSTRAINT fki2c6qmuo17j0t9mivs20c5yin FOREIGN KEY (device_persistence_id) REFERENCES public.device(persistence_id);


