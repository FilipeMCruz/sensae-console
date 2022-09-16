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

create table if not exists public.device
(
    persistence_id bigint generated by default as identity
        primary key,
    oid            varchar(255)
        constraint unique_device_oid
            unique
);

alter table public.device
    owner to "user";

create table if not exists public.device_permission
(
    persistence_id        bigint generated by default as identity (maxvalue 2147483647)
        primary key,
    domain_oid            varchar(255),
    device_persistence_id bigint
        constraint pk_device
            references public.device
);

alter table public.device_permission
    owner to "user";

create table if not exists public.domain
(
    persistence_id bigint generated by default as identity
        primary key,
    name           varchar(255),
    oid            varchar(255)
        constraint unique_domain_oid
            unique,
    path           text[]
);

alter table public.domain
    owner to "user";

create table if not exists public.domain_permission
(
    persistence_id        bigint generated by default as identity
        primary key,
    type                  integer not null,
    domain_persistence_id bigint
        constraint pk_domain
            references public.domain
);

alter table public.domain_permission
    owner to "user";

create table if not exists public.tenant
(
    persistence_id bigint generated by default as identity (maxvalue 2147483647)
        primary key,
    domains        text[],
    email          varchar(255)
        constraint unique_tenant_email
            unique,
    name           varchar(255),
    oid            varchar(255)
        constraint unique_tenant_oid
            unique,
    phone_number   varchar(255)
);

alter table public.tenant
    owner to "user";

CREATE FUNCTION public.init_domains () 
RETURNS varchar(255) AS $$
    DECLARE
        root_oid varchar(255) := gen_random_uuid();
        public_oid varchar(255) := gen_random_uuid();
        unallocated_oid varchar(255) := gen_random_uuid();
    BEGIN
        INSERT INTO public.domain (name, oid, path) VALUES ('root', root_oid, ARRAY[root_oid]);
        INSERT INTO public.domain (name, oid, path) VALUES ('public', public_oid, ARRAY[root_oid, public_oid]);
        INSERT INTO public.domain (name, oid, path) VALUES ('unallocated', unallocated_oid, ARRAY[root_oid, unallocated_oid]);
        INSERT INTO public.tenant (name, oid, phone_number, email, domains) VALUES ('Anonymous', gen_random_uuid(), '', '', ARRAY[public_oid]);
        INSERT INTO public.tenant (name, oid, phone_number, email, domains) VALUES ('Admin', gen_random_uuid(), '', '$SENSAE_ADMIN_EMAIL', ARRAY[root_oid]);
        RETURN root_oid;
    END;
$$ LANGUAGE plpgsql;

select public.init_domains();

DROP FUNCTION public.init_domains;
