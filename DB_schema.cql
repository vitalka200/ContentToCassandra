CREATE KEYSPACE "test";
USE test;

CREATE TABLE urls(
	url_id uuid,
	url_addr text,
	PRIMARY KEY (url_id, url_addr)
);

CREATE INDEX ON urls (url_addr);

CREATE TABLE chunks(
    chunk_id uuid,
    url_id uuid,
    chunk_seq_id int,
    chunk_data text,
    PRIMARY KEY (chunk_id, url_id, chunk_seq_id)
);

CREATE INDEX ON chunks (url_id);
CREATE INDEX ON chunks (chunk_seq_id);