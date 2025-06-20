alter table carts
    modify date_created date default (curdate()) not null;

alter table cart_items
    alter column quantity set default 1;

alter table cart_items
    add constraint cart_product_unique
        unique (product_id, cart_id);

alter table cart_items
    drop foreign key `cart-item_cart_id_fk`;

alter table cart_items
    add constraint `cart-item_cart_id_fk`
        foreign key (cart_id) references carts (id)
            on delete cascade;

alter table cart_items
    drop foreign key cart_item_products_id_fk;

alter table cart_items
    add constraint cart_item_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade;