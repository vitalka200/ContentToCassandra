CREATE KEYSPACE "test";
USE test;

CREATE TABLE urls(
	url_id uuid,
	url_addr text,
	PRIMARY KEY (url_addr, url_id)
);


CREATE TABLE chunks(
    chunk_id uuid,
    url_id uuid,
    chunk_seq_id int,
    chunk_data text,
    PRIMARY KEY (url_id, chunk_seq_id, chunk_id)
);