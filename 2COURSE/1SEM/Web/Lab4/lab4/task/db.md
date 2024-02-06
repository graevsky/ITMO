```postgresql
create table results(
    id          serial primary key,
    x           double precision not null,
    y           double precision not null,
    r           double precision not null,
    hit         boolean          not null,
    check_time  varchar(50),
    submit_time varchar(50),
    user_id     integer references users,
    is_valid boolean default false
);
create table users(
    id       serial primary key,
    login    varchar(255) not null unique,
    password varchar(255) not null
);
```