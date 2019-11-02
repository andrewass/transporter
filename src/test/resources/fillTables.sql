
-- User inserts

insert into t_user(id, username, notify_user)
values (1, 'user_1', 1);

insert into t_user(id, username, notify_user)
values (2, 'user_2', 1);

insert into t_user(id, username, notify_user)
values (3, 'user_3', 1);

-- Driver inserts

insert into t_driver(id, notify_driver, user_id)
values (1, 1, 1);

insert into t_driver(id, notify_driver, user_id)
values (2, 0, 2);

insert into t_driver(id, notify_driver, user_id)
values (3, 1, 3);

-- Order inserts

insert into t_order(id, user_id, driver_id, order_status, trip_date)
values (1, 1, 2, 'AWAITING_CONFIRMATION', DATE_SUB(NOW(), INTERVAL 4 DAY));

insert into t_order(id, user_id, driver_id, order_status, trip_date)
values (2, 3, 2, 'AWAITING_CONFIRMATION', DATE_SUB(NOW(), INTERVAL 2 DAY));

insert into t_order(id, user_id, driver_id, order_status, trip_date)
values (3, 2, 1, 'AWAITING_CONFIRMATION', DATE_SUB(NOW(), INTERVAL 3 DAY));

insert into t_order(id, user_id, driver_id, order_status, trip_date)
values (4, 2, 3, 'AWAITING_CONFIRMATION', NOW());

insert into t_order(id, user_id, driver_id, order_status, trip_date)
values (5, 3, 1, 'AWAITING_CONFIRMATION', DATE_ADD(NOW(), INTERVAL 2 DAY));








