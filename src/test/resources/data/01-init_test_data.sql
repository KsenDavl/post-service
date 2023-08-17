delete from shipment_records;
delete from post_items;
delete from post_offices;

insert into post_offices(post_index, post_name, address )
values (426054, 'NY-Dep-21', 'Tolstoy st, 34');

insert into post_offices(post_index, post_name, address )
values (428034, 'LA-Dep-4', 'Dickens st, 98');

insert into post_offices(post_index, post_name, address )
values (438034, 'WA-Dep-76', 'Lincoln st, 45');


insert into post_items (id, type, receiver_index, receiver_address, receiver_name, status)
values (5, 'POSTCARD', 426054, 'Tolstoy st, 36', 'Max Kolsky', 'ACCEPTED');

insert into post_items (id, type, receiver_index, receiver_address, receiver_name, status)
values (7, 'LETTER', 426054, 'Tolstoy st, 38', 'Alexa Kops', 'IN_TRANSIT');

insert into post_items (id, type, receiver_index, receiver_address, receiver_name, status)
values (9, 'PARCEL', 438034, 'Hoffmann st, 3', 'Andrew Black', 'DELIVERED');


insert into shipment_records (id, post_item, record_text, record_time)
values (50, 5, 'Registered at : WA-Dep-76', '2022-08-16 18:44:58.797752');
insert into shipment_records (id, post_item, record_text, record_time)
values (51, 5, 'Accepted at temporary facility: WA-Dep-76', '2022-08-16 18:44:59.797752');

insert into shipment_records (id, post_item, record_text, record_time)
values (70, 7, 'Registered at : WA-Dep-76', '2022-08-14 18:44:58.797752');
insert into shipment_records (id, post_item, record_text, record_time)
values (71, 7, 'Accepted at temporary facility: WA-Dep-76', '2022-08-14 18:44:58.797752');
insert into shipment_records (id, post_item, record_text, record_time)
values (72, 7, 'Departed from temporary facility: WA-Dep-76', '2022-08-15 18:44:59.797752');