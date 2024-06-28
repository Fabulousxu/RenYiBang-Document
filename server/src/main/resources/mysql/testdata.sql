INSERT INTO user (type, nickname, avatar, intro, rating, balance) VALUES
(0, 'UserA', 'avatarA.png', 'Intro A', 50, 1000),
(1, 'UserB', 'avatarB.png', 'Intro B', 60, 2000),
(2, 'UserC', 'avatarC.png', 'Intro C', 70, 3000);

INSERT INTO user_auth (user_id, password) VALUES
(1, 'abc'),
(2, 'abc'),
(3, 'abc');

INSERT INTO follow (follower_id, followee_id) VALUES
(1, 2),
(1, 3),
(2, 3);

INSERT INTO task (owner_id, title, images, description, price, max_access, rating) VALUES
(1, 'Task 1', 'task1.png', 'Description 1', 1000, 3, 50),
(2, 'Task 2', 'task2.png', 'Description 2', 2000, 2, 60),
(3, 'Task 3', 'task3.png', 'Description 3', 3000, 1, 70);

INSERT INTO task_collect (task_id, collector_id) VALUES
(1, 2),
(2, 1),
(3, 1);

INSERT INTO task_comment (task_id, commenter_id, content, rating) VALUES
(1, 2, 'Comment on Task 1 by User B', 50),
(2, 3, 'Comment on Task 2 by User C', 60),
(3, 1, 'Comment on Task 3 by User A', 70);

INSERT INTO task_comment_like (task_comment_id, liker_id) VALUES
(1, 3),
(2, 1),
(3, 2);

INSERT INTO task_message (task_id, messager_id, content) VALUES
(1, 3, 'Message on Task 1 by User C'),
(2, 1, 'Message on Task 2 by User A'),
(3, 2, 'Message on Task 3 by User B');

INSERT INTO task_message_like (task_message_id, liker_id) VALUES
(1, 2),
(2, 3),
(3, 1);

INSERT INTO task_access (task_id, accessor_id) VALUES
(1, 3),
(2, 1),
(3, 2);

INSERT INTO task_order (task_id, owner_id, accessor_id, status, cost) VALUES
(1, 1, 3, 0, 1000), -- UNPAID
(2, 2, 1, 1, 2000), -- PAID
(3, 3, 2, 2, 3000); -- COMPLETED



INSERT INTO service (owner_id, title, images, description, price, max_access) VALUES
(1, 'Service 1', 'service1.png', 'Description 1', 1000, 3),
(2, 'Service 2', 'service2.png', 'Description 2', 2000, 2),
(3, 'Service 3', 'service3.png', 'Description 3', 3000, 1);

INSERT INTO service_collect (service_id, collector_id) VALUES
(1, 2),
(2, 1),
(3, 1);

INSERT INTO service_comment (service_id, commenter_id, content, rating) VALUES
(1, 2, 'Comment on Service 1 by User B', 50),
(2, 3, 'Comment on Service 2 by User C', 60),
(3, 1, 'Comment on Service 3 by User A', 70);

INSERT INTO service_comment_like (service_comment_id, liker_id) VALUES
(1, 3),
(2, 1),
(3, 2);

INSERT INTO service_message (service_id, messager_id, content) VALUES
(1, 3, 'Message on Service 1 by User C'),
(2, 1, 'Message on Service 2 by User A'),
(3, 2, 'Message on Service 3 by User B');

INSERT INTO service_message_like (service_message_id, liker_id) VALUES
(1, 2),
(2, 3),
(3, 1);

INSERT INTO service_access (service_id, accessor_id) VALUES
(1, 3),
(2, 1),
(3, 2);

INSERT INTO service_order (service_id, owner_id, accessor_id) VALUES
(1, 1, 3),
(2, 2, 1),
(3, 3, 2);
