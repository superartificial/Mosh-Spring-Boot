alter table profiles
    modify bio text null;

alter table profiles
    modify date_of_birth date null;

alter table profiles
    alter column loyalty_points set default 0;