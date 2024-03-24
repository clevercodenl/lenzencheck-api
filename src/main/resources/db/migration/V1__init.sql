create table merchants
(
    id        bigint auto_increment
        primary key,
    image_url varchar(255) null,
    name      varchar(255) null
);

create table brands
(
    id        int auto_increment
        primary key,
    name      varchar(255) not null,
    products_count  int unsigned not null default 0
);

create table lens_types
(
    id        int auto_increment
        primary key,
    name      varchar(255) not null,
    products_count  int unsigned not null default 0
);


create table products
(
    id                        bigint auto_increment
        primary key,
    ean               varchar(255) null
);

create table merchant_products
(
    id                        bigint auto_increment
        primary key,
    brand_id                  int        null,
    merchant_id               bigint       not null,
    product_id                bigint       not null,
    name                      varchar(255) not null,
    description               text         null,
    aw_image_url              text         null,
    alternate_image           text         null,
    alternate_image2          text         null,
    alternate_image3          text         null,
    alternate_image4          text         null,
    aw_deep_link_value        varchar(255) null,
    aw_product_id_value       varchar(255) null,
    currency                  varchar(255) null,
    delivery_cost             varchar(255) null,
    delivery_time             varchar(255) null,
    display_price             varchar(255) null,
    in_stock                  varchar(255) null,
    is_for_sale               varchar(255) null,
    large_image               text         null,
    merchant_deep_link        varchar(255) null,
    merchant_product_id_value varchar(255) null,
    product_ean               varchar(255) null,
    search_price              varchar(255) null,
    specifications            varchar(255) null,
    stock_quantity            varchar(255) null,
    store_price               varchar(255) null,
    upc                       varchar(255) null,
    constraint FK_merchants
        foreign key (merchant_id) references merchants (id),
    constraint FK_brands
            foreign key (brand_id) references brands (id),
    constraint FK_products
            foreign key (product_id) references products (id)
);

create table merchant_product_lens_types
(
    id        bigint auto_increment
        primary key,
    merchant_product_id                bigint       not null,
    lens_type_id                int       not null,
    constraint FK_merchant_products
        foreign key (merchant_product_id) references merchant_products (id),
    constraint FK_lens_types
        foreign key (lens_type_id) references lens_types (id)
);
