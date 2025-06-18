create table cart
(
    id           binary(16) default (uuid_to_bin(uuid())) not null
        primary key,
    date_created datetime   default CURRENT_TIMESTAMP     not null
);

create table cart_item
(
    id         bigint auto_increment
        primary key,
    cart_id    binary(16) not null,
    product_id bigint     null,
    quantity   int        not null,
    constraint `cart-item_cart_id_fk`
        foreign key (cart_id) references cart (id),
    constraint cart_item_products_id_fk
        foreign key (product_id) references products (id)
);