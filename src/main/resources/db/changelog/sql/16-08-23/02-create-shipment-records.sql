create sequence if not exists shipment_records_seq;

create table shipment_records
(
    id          BIGINT primary key,
    post_item   BIGINT references post_items (id),
    record_text VARCHAR(128),
    record_time TIMESTAMP
);