create table roles
(
    id   bigserial
        primary key,
    role varchar(255)
);

create table brand
(
    id    bigserial
        primary key,
    brand varchar(255)
);

create table color
(
    id    bigserial
        primary key,
    color varchar(255)
);

create table gender
(
    id     bigserial
        primary key,
    gender varchar(255)
);


create table contact
(
    id      serial
        primary key,
    name    varchar(25) not null,
    email   text        not null,
    subject text        not null,
    message text        not null
);
create table users
(
    id            bigserial
        primary key,
    email         varchar(255),
    password      varchar(255),
    username      varchar(255),
    role_id       bigint
            references roles,
    id_google     text,
    activate_code text
);

create table specification
(
    id serial primary key,
    size              text                                                   not null,
    purpose           text                                                   not null,
    material          text                                                   not null,
    membrane          boolean                                                not null,
    country           text                                                   not null,
    description       text                                                   not null,
    short_description text
);


create table products
(
    id            bigserial
        primary key,
    images        varchar(255),
    name          varchar(255),
    price         double precision,
    color         bigint
        references color,
    category      bigint
        references brand,
    gender        bigint
        references gender,
    specification bigint
        references specification
);


create table order_cart
(
    id             serial
        primary key,
    price          integer,
    discount       integer,
    phone          varchar(30),
    delivery       text,
    delivery_price varchar(10),
    user_id        bigint
);

create table order_product_info
(
    id      serial
        primary key,
    product bigint
        references products,
    count   bigint,
    size    bigint
);

create table order_cart_products
(
    id                    serial
        primary key,
    order_cart_id         bigint not null
        references order_cart,
    order_product_info_id bigint not null
        constraint order_product_info_id_fkey
            references order_product_info
);

create table newsletter
(
    id    serial
        primary key,
    email text not null
);

create table likes
(
    id serial primary key,
    user_id    bigint
        references users,
    product_id bigint
        references products,
    constraint id_user_and_id_product
        unique (user_id, product_id)
);

create table favorite
(
    id serial primary key,
    id_user bigint                                              not null
        constraint fk_favorite_user
            references users,
    product bigint
        references products,
    size    bigint,
    count   bigint,
    constraint id_user_and_product
        unique (id_user, product)
);

create table promo
(
    id               serial
        primary key,
    promo_name       text    not null,
    discount         integer not null,
    count_activation integer not null,
    active_date      date
);

create unique index promo_unique
    on promo (promo_name);

create table promo_user
(
    id_user  bigint
        references users,
    id_promo bigint
        references promo
);


create table review
(
    id         bigserial
        primary key,
    email      varchar(255),
    message    text,
    name       varchar(255),
    stars      integer,
    subject    varchar(255),
    id_product bigint
        references products
);