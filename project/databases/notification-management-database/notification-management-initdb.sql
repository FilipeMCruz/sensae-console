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

DROP DATABASE IF EXISTS notification;

CREATE DATABASE notification WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.utf8';


ALTER DATABASE notification OWNER TO "user";

\connect notification

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

SET default_tablespace = '';

SET default_table_access_method = heap;

CREATE TABLE public.notification (
    persistence_id bigint NOT NULL,
    id character varying(255),
    category character varying(255),
    sub_category character varying(255),
    level character varying(255),
    description character varying(255),
    reported_at timestamp,
    data_ids text,
    device_ids text,
    domains text,
    other character varying(255)
);


ALTER TABLE public.notification OWNER TO "user";

ALTER TABLE public.notification ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.notification_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

SELECT pg_catalog.setval('public.notification_persistence_id_seq', 1, false);

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT noti_pkey PRIMARY KEY (persistence_id);

ALTER TABLE ONLY public.notification
    ADD CONSTRAINT noti_unique UNIQUE (id);

CREATE INDEX domains_index ON public.notification (domains);

CREATE TABLE public.addressee_config (
    persistence_id bigint NOT NULL,
    id character varying(255),
    category character varying(255),
    sub_category character varying(255),
    level character varying(255),
    show_ui boolean,
    send_sms boolean,
    send_email boolean,
    send_notification boolean
);


ALTER TABLE public.addressee_config OWNER TO "user";

ALTER TABLE public.addressee_config ALTER COLUMN persistence_id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.addressee_config_persistence_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);

SELECT pg_catalog.setval('public.addressee_config_persistence_id_seq', 1, false);

ALTER TABLE ONLY public.addressee_config
    ADD CONSTRAINT addressee_config_pkey PRIMARY KEY (persistence_id);

