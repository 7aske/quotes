alter table quote change column author_image author_image varchar(1024) null;

alter table quote add column added_by varchar(255) null;