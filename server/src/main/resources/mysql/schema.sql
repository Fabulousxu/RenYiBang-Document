DROP DATABASE IF EXISTS RenYiBang;
CREATE DATABASE RenYiBang;
USE RenYiBang;

CREATE TABLE user
(
    user_id  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户id',
    type     TINYINT DEFAULT 0  NOT NULL COMMENT '用户类型(0:普通用户,1:客服,2:管理员)',
    nickname VARCHAR(16)        NOT NULL COMMENT '用户昵称',
    avatar   LONGTEXT COMMENT '用户头像',
    intro    TEXT COMMENT '用户介绍',
    rating   TINYINT DEFAULT 50 NOT NULL COMMENT '用户评分(存储10倍评分,范围0~100)',
    balance  BIGINT  DEFAULT 0  NOT NULL COMMENT '用户余额(存储100倍余额)'
) comment '用户表';

CREATE TABLE user_auth
(
    user_id  BIGINT PRIMARY KEY COMMENT '用户id',
    password VARCHAR(16) NOT NULL COMMENT '用户密码',
    FOREIGN KEY (user_id) REFERENCES user (user_id) ON UPDATE CASCADE ON DELETE CASCADE
) COMMENT '用户密码表';

CREATE TABLE follow
(
    follower_id BIGINT NOT NULL COMMENT '关注者id',
    followee_id BIGINT NOT NULL COMMENT '被关注者id',
    PRIMARY KEY (follower_id, followee_id),
    FOREIGN KEY (follower_id) REFERENCES user (user_id) ON UPDATE CASCADE,
    FOREIGN KEY (followee_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '用户关注表';

CREATE TABLE task
(
    task_id     BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务id',
    owner_id    BIGINT      NOT NULL COMMENT '任务发布者id',
    title       VARCHAR(32) NOT NULL COMMENT '任务标题',
    images      LONGTEXT COMMENT '任务图片',
    description TEXT COMMENT '任务描述',
    price       BIGINT               DEFAULT 0 NOT NULL COMMENT '任务价格(存储100倍价格)',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务发布时间',
    max_access  INT         NOT NULL DEFAULT 1 COMMENT '任务最大接取数',
    rating      TINYINT              DEFAULT 50 NOT NULL COMMENT '任务评分(存储10倍评分,范围0~100)',
    collected   BIGINT      NOT NULL COMMENT '任务收藏数',
    FOREIGN KEY (owner_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务表';

CREATE TABLE task_collect
(
    task_collect_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务收藏id',
    task_id         BIGINT    NOT NULL COMMENT '任务id',
    collector_id    BIGINT    NOT NULL COMMENT '收藏者id',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (collector_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务收藏表';

CREATE TABLE task_comment
(
    task_comment_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务评论id',
    task_id         BIGINT    NOT NULL COMMENT '任务id',
    commenter_id    BIGINT    NOT NULL COMMENT '任务评论者id',
    content         TEXT      NOT NULL COMMENT '任务评论内容',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '任务评论时间',
    rating          TINYINT            DEFAULT 50 NOT NULL COMMENT '任务评论评分(存储10倍评分,范围0~100)',
    liked_number    BIGINT    NOT NULL COMMENT '任务评论点赞数',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (commenter_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务评论表(仅有接取任务者才能评论)';

CREATE TABLE task_comment_like
(
    task_comment_id BIGINT NOT NULL COMMENT '任务评论id',
    liker_id        BIGINT NOT NULL COMMENT '点赞者id',
    PRIMARY KEY (task_comment_id, liker_id),
    FOREIGN KEY (task_comment_id) REFERENCES task_comment (task_comment_id) ON UPDATE CASCADE,
    FOREIGN KEY (liker_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务评论点赞表';

CREATE TABLE task_message
(
    task_message_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务留言id',
    task_id         BIGINT    NOT NULL COMMENT '任务id',
    messager_id     BIGINT    NOT NULL COMMENT '留言者id',
    content         TEXT      NOT NULL COMMENT '消息内容',
    created_at      TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
    liked_number    BIGINT    NOT NULL COMMENT '任务留言点赞数',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (messager_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务留言表';

CREATE TABLE task_message_like
(
    task_message_id BIGINT NOT NULL COMMENT '任务留言id',
    liker_id        BIGINT NOT NULL COMMENT '点赞者id',
    PRIMARY KEY (task_message_id, liker_id),
    FOREIGN KEY (task_message_id) REFERENCES task_message (task_message_id) ON UPDATE CASCADE,
    FOREIGN KEY (liker_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务留言点赞表';

CREATE TABLE task_access
(
    task_access_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务接取候选id',
    task_id        BIGINT    NOT NULL COMMENT '任务id',
    accessor_id    BIGINT    NOT NULL COMMENT '接取者id',
    created_at     TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '接取时间',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (accessor_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务接取候选表';

CREATE TABLE task_order
(
    task_order_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '任务订单id',
    task_id       BIGINT                     NOT NULL COMMENT '任务id',
    owner_id      BIGINT                     NOT NULL COMMENT '任务发布者id',
    accessor_id   BIGINT                     NOT NULL COMMENT '任务接取者id',
    status        TINYINT UNSIGNED DEFAULT 0 NOT NULL COMMENT '任务状态(0:未付款,1:已付款,任务进行中,2:接收者已完成，等待发布者确认,3:发布者已确认完成,4:订单已取消)',
    cost          BIGINT           DEFAULT 0 NOT NULL COMMENT '任务价格(存储100倍价格)',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES user (user_id) ON UPDATE CASCADE,
    FOREIGN KEY (accessor_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务订单表';

CREATE TABLE service
(
    service_id  BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务id',
    owner_id    BIGINT      NOT NULL COMMENT '服务发布者id',
    title       VARCHAR(32) NOT NULL COMMENT '服务标题',
    images      LONGTEXT COMMENT '服务图片',
    description TEXT COMMENT '服务描述',
    price       BIGINT               DEFAULT 0 NOT NULL COMMENT '服务价格(存储100倍价格)',
    created_at  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务发布时间',
    max_access  INT         NOT NULL DEFAULT 1 COMMENT '服务最大购买数',
    rating      TINYINT              DEFAULT 50 NOT NULL COMMENT '任务评分(存储10倍评分,范围0~100)',
    FOREIGN KEY (owner_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务表';

CREATE TABLE service_collect
(
    service_collect_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务收藏id',
    service_id         BIGINT    NOT NULL COMMENT '服务id',
    collector_id       BIGINT    NOT NULL COMMENT '收藏者id',
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (collector_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务收藏表';

CREATE TABLE service_comment
(
    service_comment_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务评论id',
    service_id         BIGINT    NOT NULL COMMENT '服务id',
    commenter_id       BIGINT    NOT NULL COMMENT '服务评论者id',
    content            TEXT      NOT NULL COMMENT '服务评论内容',
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '服务评论时间',
    rating             TINYINT            DEFAULT 50 NOT NULL COMMENT '服务评论评分(存储10倍评分,范围0~100)',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (commenter_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务评论表(仅有购买服务者才能评论)';

CREATE TABLE service_comment_like
(
    service_comment_id BIGINT NOT NULL COMMENT '服务评论id',
    liker_id           BIGINT NOT NULL COMMENT '点赞者id',
    PRIMARY KEY (service_comment_id, liker_id),
    FOREIGN KEY (service_comment_id) REFERENCES service_comment (service_comment_id) ON UPDATE CASCADE,
    FOREIGN KEY (liker_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务评论点赞表';

CREATE TABLE service_message
(
    service_message_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务留言id',
    service_id         BIGINT    NOT NULL COMMENT '服务id',
    messager_id        BIGINT    NOT NULL COMMENT '留言者id',
    content            TEXT      NOT NULL COMMENT '消息内容',
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (messager_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务留言表';

CREATE TABLE service_message_like
(
    service_message_id BIGINT NOT NULL COMMENT '服务留言id',
    liker_id           BIGINT NOT NULL COMMENT '点赞者id',
    PRIMARY KEY (service_message_id, liker_id),
    FOREIGN KEY (service_message_id) REFERENCES service_message (service_message_id) ON UPDATE CASCADE,
    FOREIGN KEY (liker_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务留言点赞表';

CREATE TABLE service_access
(
    service_access_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务购买候选id',
    service_id        BIGINT    NOT NULL COMMENT '服务id',
    accessor_id       BIGINT    NOT NULL COMMENT '购买者id',
    created_at        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '购买时间',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (accessor_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务购买候选表';

CREATE TABLE service_order
(
    service_order_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '服务订单id',
    service_id       BIGINT NOT NULL COMMENT '服务id',
    owner_id         BIGINT NOT NULL COMMENT '服务发布者id',
    accessor_id      BIGINT NOT NULL COMMENT '服务购买者id',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (owner_id) REFERENCES user (user_id) ON UPDATE CASCADE,
    FOREIGN KEY (accessor_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务订单表';

CREATE TABLE task_chat
(
    task_chat_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '聊天id',
    task_id      BIGINT NOT NULL COMMENT '任务id',
    chatter_id   BIGINT NOT NULL COMMENT '发起聊天者id',
    FOREIGN KEY (task_id) REFERENCES task (task_id) ON UPDATE CASCADE,
    FOREIGN KEY (chatter_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务聊天表';

CREATE TABLE service_chat
(
    service_chat_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '聊天id',
    service_id      BIGINT NOT NULL COMMENT '服务id',
    chatter_id      BIGINT NOT NULL COMMENT '发起聊天者id',
    FOREIGN KEY (service_id) REFERENCES service (service_id) ON UPDATE CASCADE,
    FOREIGN KEY (chatter_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务聊天表';

CREATE TABLE task_chat_message
(
    task_chat_message_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '聊天消息id',
    task_chat_id         BIGINT    NOT NULL COMMENT '聊天id',
    sender_id            BIGINT    NOT NULL COMMENT '消息发送者id',
    receiver_id          BIGINT    NOT NULL COMMENT '消息接收者id',
    content              TEXT      NOT NULL COMMENT '消息内容',
    images               LONGTEXT  NOT NULL COMMENT '消息图片',
    created_at           TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
    FOREIGN KEY (task_chat_id) REFERENCES task_chat (task_chat_id) ON UPDATE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user (user_id) ON UPDATE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '任务聊天消息表';

CREATE TABLE service_chat_message
(
    service_chat_message_id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '聊天消息id',
    service_chat_id         BIGINT    NOT NULL COMMENT '聊天id',
    sender_id               BIGINT    NOT NULL COMMENT '消息发送者id',
    receiver_id             BIGINT    NOT NULL COMMENT '消息接收者id',
    content                 TEXT      NOT NULL COMMENT '消息内容',
    images                  LONGTEXT  NOT NULL COMMENT '消息图片',
    created_at              TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消息发送时间',
    FOREIGN KEY (service_chat_id) REFERENCES service_chat (service_chat_id) ON UPDATE CASCADE,
    FOREIGN KEY (sender_id) REFERENCES user (user_id) ON UPDATE CASCADE,
    FOREIGN KEY (receiver_id) REFERENCES user (user_id) ON UPDATE CASCADE
) COMMENT '服务聊天消息表';

