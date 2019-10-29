#################### Entities #######################
create table t_user
(
    id         bigint(20) not null auto_increment,
    username   varchar(50),
    first_name varchar(50),
    primary key (id)
);

create table t_driver
(
    id           bigint(20) not null auto_increment,
    date_created datetime,
    date_updated datetime,
    primary key (id)
);

create table t_order
(
    id           bigint(20) not null auto_increment,
    user_id      bigint(20),
    driver_id    bigint(20),
    order_status varchar(50),
    date_created datetime,
    date_updated datetime,
    trip_date    datetime,
    primary key (id),
    foreign key (user_id) references t_user (id),
    foreign key (driver_id) references t_driver (id)
);

create table t_driver_review
(
    id           bigint(20) not null auto_increment,
    customer_id  bigint(20),
    driver_id    bigint(20),
    review       varchar(400),
    rating       int,
    date_created datetime,
    date_updated datetime,
    primary key (id),
    foreign key (customer_id) references t_user (id),
    foreign key (driver_id) references t_driver (id)
);


create table t_trip
(
    id           bigint(20) not null auto_increment,
    customer_id  bigint(20),
    trip_time    datetime,
    date_created datetime,
    date_updated datetime,
    primary key (id)
);

######### Batch Tables ############

create table t_order_cancel_row
(
    id            bigint(20) not null auto_increment,
    order_id      bigint(20),
    notify_user   bool,
    notify_driver bool,
    processed     bool,
    primary key (id),
    foreign key (order_id) references t_order (id)
);

