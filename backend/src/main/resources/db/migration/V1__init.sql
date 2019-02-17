CREATE TABLE kuber (
  id BIGSERIAL PRIMARY KEY,
  navn varchar(32) NOT NULL
);

CREATE TABLE tid (
  id BIGSERIAL PRIMARY KEY,
  minutter smallint NOT NULL,
  sekunder smallint NOT NULL,
  hundredeler smallint NOT NULL
);

CREATE TABLE personer (
  id BIGSERIAL PRIMARY KEY,
  fornavn character varying(64) NOT NULL,
  etternavn character varying(64) NOT NULL,
  epost character varying(128) NOT NULL,
  opprettet timestamp default CURRENT_TIMESTAMP NOT NULL,
  slettet timestamp
);

