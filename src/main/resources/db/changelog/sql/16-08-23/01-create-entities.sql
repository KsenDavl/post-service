create sequence if not exists post_items_seq;
create sequence if not exists post_offices_seq;

create table post_items (
                          id BIGINT primary key,
                          type VARCHAR(64),
                          receiver_index INT,
                          receiver_address VARCHAR(128),
                          receiver_name VARCHAR(64)
);

create table post_offices (
                         post_index INT primary key,
                         post_name VARCHAR(64) not null unique,
                         address VARCHAR(128) not null
);



