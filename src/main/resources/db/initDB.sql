DROP TABLE IF EXISTS requests;
DROP TABLE IF EXISTS headers;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE requests
(
    id       INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date     TIMESTAMP           DEFAULT now() NOT NULL,
    protocol VARCHAR,
    ip       VARCHAR,
    method   VARCHAR,
    full_url VARCHAR,
    locale   VARCHAR,
    body     TEXT
);

CREATE TABLE headers
(
    request_id INTEGER NOT NULL,
    name       VARCHAR NOT NULL,
    value      VARCHAR NOT NULL,
    FOREIGN KEY (request_id) REFERENCES requests (id) ON DELETE CASCADE
);