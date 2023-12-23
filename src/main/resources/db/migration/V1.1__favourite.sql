create table favourite_quote
(
    quote_id           varchar(255) not null,
    `user`             varchar(255) not null,

    constraint favourite_quote_pk
        primary key (quote_id, `user`),

    constraint favourite_quote_quote_id_fk foreign key (quote_id) references quote (quote_id),

    created_by         varchar(255) not null default 'system',
    created_date       timestamp             default current_timestamp,
    last_modified_by   varchar(255) not null default 'system',
    last_modified_date timestamp             default current_timestamp
)