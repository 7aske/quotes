create table quote
(
    quote_id           varchar(255)  not null primary key default uuid(),
    `text`             varchar(2048) not null,
    author             varchar(255)  not null,
    author_image       varchar(255)  null,
    shared             tinyint   default 1,

    created_by         varchar(255)  not null default 'system',
    created_date       timestamp default current_timestamp,
    last_modified_by   varchar(255)  not null default 'system',
    last_modified_date timestamp default current_timestamp
)