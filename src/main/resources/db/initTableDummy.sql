DROP TABLE IF EXISTS dummies;

CREATE TABLE dummies
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
    date            TIMESTAMP           DEFAULT now() NOT NULL,
    description     VARCHAR NOT NULL,
    content_type    VARCHAR NOT NULL,
    dummy           TEXT
);