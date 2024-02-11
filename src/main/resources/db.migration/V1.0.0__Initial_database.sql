CREATE DATABASE infobip;

nesto_glupo

CREATE SCHEMA IF NOT EXISTS "infobip-uri-shortener";

CREATE TABLE IF NOT EXISTS "infobip-uri-shortener".account (
	id                          varchar(255) NOT NULL,
    password                    varchar(255) NOT NULL,
	CONSTRAINT countries_id_pkey PRIMARY KEY (id)
);
CREATE INDEX IF NOT EXISTS account_id_idx ON "infobip-uri-shortener".account(id);
