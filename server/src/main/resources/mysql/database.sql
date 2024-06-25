create table if not exists user
(
    user_id        bigint auto_increment comment '用户id，每个用户唯一对应'
        primary key,
    user_nickname  varchar(16)           not null comment '用户昵称',
    user_admin     tinyint(1) default 0  not null comment '用户是否是管理员',
    user_avator    text                  null comment '用户头像',
    user_intro     text                  null comment '用户介绍',
    user_rating    int        default 50 not null comment '用户评分（0-10且保留一位小数，数据库中存储为0-100）',
    user_balance   bigint     default 0  not null comment '用户余额，保留两位小数，数据库中按乘100计算',
    user_following bigint                not null comment '关注该用户的人数'
)
    comment '用户信息';

create table if not exists follow
(
    follow_id          bigint auto_increment comment '关注id'
        primary key,
    follow_follower_id bigint not null comment '关注者id',
    follow_user_id     bigint not null comment '被关注用户id',
    constraint follow_user_user_id_fk
        foreign key (follow_follower_id) references user (user_id),
    constraint follow_user_user_id_fk_2
        foreign key (follow_user_id) references user (user_id)
)
    comment '关注列表';

create table if not exists `order`
(
    order_id           bigint auto_increment comment '订单id'
        primary key,
    order_creater      bigint               not null comment '订单创建者id（任务中由发布者创建，服务中由顾客创建）',
    order_receiver     bigint               not null comment '订单接收者id（任务中由接取者接收，服务中由商家接收）',
    order_date         text                 not null comment '订单创建日期',
    order_is_paid      tinyint(1) default 0 not null comment '订单是否支付完成',
    order_has_finished tinyint(1) default 0 not null comment '订单是否完成',
    order_cost         bigint               not null comment '订单金额（两位小数，数据库中乘100）',
    constraint order_user_user_id_fk
        foreign key (order_creater) references user (user_id),
    constraint order_user_user_id_fk_2
        foreign key (order_receiver) references user (user_id)
);

create table if not exists report_user
(
    report_id          bigint auto_increment comment '举报id'
        primary key,
    report_reporter_id bigint not null comment '举报者id',
    report_user_id     bigint not null comment '被举报者id',
    report_content     text   not null comment '举报内容',
    constraint report_user_user_user_id_fk
        foreign key (report_user_id) references user (user_id),
    constraint report_user_user_user_id_fk_2
        foreign key (report_reporter_id) references user (user_id)
)
    comment '举报用户信息';

create table if not exists service
(
    service_id          bigint auto_increment comment '服务id'
        primary key,
    service_releaser    bigint not null comment '服务发布者id',
    service_title       text   not null comment '服务标题',
    service_cover       text   null comment '服务者提供的图片',
    service_description text   not null comment '服务内容描述',
    service_collect     bigint not null comment '服务收藏数',
    constraint service_user_user_id_fk
        foreign key (service_releaser) references user (user_id)
)
    comment '服务信息';

create table if not exists report_service
(
    report_reporter_id bigint not null comment '举报者id',
    report_service_id  bigint not null comment '被举报服务id',
    report_content     text   not null comment '举报内容',
    report_id          bigint auto_increment comment '举报id'
        primary key,
    constraint report_service_service_service_id_fk
        foreign key (report_service_id) references service (service_id),
    constraint report_service_user_user_id_fk
        foreign key (report_reporter_id) references user (user_id)
)
    comment '服务举报内容';

create table if not exists service_collect
(
    service_collect_id      bigint auto_increment comment '收藏id'
        primary key,
    service_collect_user    bigint not null comment '收藏者id',
    service_collect_service bigint not null comment '收藏服务id',
    constraint service_collect_service_service_id_fk
        foreign key (service_collect_service) references service (service_id),
    constraint service_collect_user_user_id_fk
        foreign key (service_collect_user) references user (user_id)
)
    comment '收藏服务信息';

create table if not exists service_comment
(
    service_comment_id      bigint auto_increment comment '评论id'
        primary key,
    service_comment_service bigint           not null comment '评论对应的服务id',
    service_comment_content text             not null comment '评论内容',
    service_comment_like    bigint default 0 not null comment '评论点赞数',
    service_comment_rating  int    default 0 not null comment '评论对服务的评分',
    service_comment_user    bigint           not null comment '评论者id',
    constraint service_comment_service_service_id_fk
        foreign key (service_comment_service) references service (service_id),
    constraint service_comment_user_user_id_fk
        foreign key (service_comment_user) references user (user_id)
)
    comment '服务评论';

create table if not exists service_comment_like
(
    service_comment_like_id      bigint auto_increment comment '服务点赞id'
        primary key,
    service_comment_like_user    bigint not null comment '点赞者id',
    service_comment_like_comment bigint not null comment '点赞的评论id',
    constraint service_comment_like_service_comment_service_comment_id_fk
        foreign key (service_comment_like_comment) references service_comment (service_comment_id),
    constraint service_comment_like_user_user_id_fk
        foreign key (service_comment_like_user) references user (user_id)
)
    comment '服务评论点赞表';

create table if not exists service_message
(
    service_message_id      bigint auto_increment comment '留言id'
        primary key,
    service_message_service bigint not null comment '留言对应的服务id',
    service_message_content text   not null comment '留言内容',
    service_message_like    bigint not null comment '留言点赞数',
    service_message_user    bigint not null comment '留言者id',
    constraint service_message_service_service_id_fk
        foreign key (service_message_service) references service (service_id),
    constraint service_message_user_user_id_fk
        foreign key (service_message_user) references user (user_id)
)
    comment '服务留言';

create table if not exists service_message_like
(
    service_message_like_user    bigint not null comment '点赞者id',
    service_message_like_message bigint not null comment '点赞留言id',
    service_message_like_id      bigint auto_increment comment '点赞id'
        primary key,
    constraint service_message_like_service_message_service_message_id_fk
        foreign key (service_message_like_message) references service_message (service_message_id),
    constraint service_message_like_user_user_id_fk
        foreign key (service_message_like_user) references user (user_id)
)
    comment '服务留言点赞';

create table if not exists task
(
    task_id           bigint auto_increment comment '任务id，每个任务唯一对应'
        primary key,
    task_releaser     bigint not null comment '任务的发布者id',
    task_title        text   not null comment '任务名称',
    task_cover        text   null comment '任务图片（多个图片需要将URL拼接，中间按空格划分）',
    task_description  text   not null comment '任务描述',
    task_expected_num int    not null comment '任务期望的接取人数',
    constraint task_user_user_id_fk
        foreign key (task_releaser) references user (user_id)
)
    comment '任务信息';

create table if not exists candidate
(
    candidate_id   bigint auto_increment comment '候选关联信息id'
        primary key,
    candidate_task bigint not null comment '候选对应的任务id',
    candidate_user bigint not null comment '候选的用户id',
    constraint candidate_task_task_id_fk
        foreign key (candidate_task) references task (task_id),
    constraint candidate_user_user_id_fk
        foreign key (candidate_user) references user (user_id)
)
    comment '任务候选人信息';

create table if not exists chat
(
    chat_id       bigint auto_increment comment '聊天id'
        primary key,
    chat_creater  bigint not null comment '聊天发起人id',
    chat_receiver bigint not null comment '聊天接受者id',
    chat_task     bigint null comment '聊天对应的任务id',
    chat_service  bigint not null comment '聊天对应的服务id',
    constraint chat_service_service_id_fk
        foreign key (chat_service) references service (service_id),
    constraint chat_task_task_id_fk
        foreign key (chat_task) references task (task_id),
    constraint chat_user_user_id_fk
        foreign key (chat_creater) references user (user_id),
    constraint chat_user_user_id_fk_2
        foreign key (chat_receiver) references user (user_id)
)
    comment '聊天消息';

create table if not exists chat_history
(
    chat_history_id      bigint auto_increment comment '聊天记录id'
        primary key,
    chat_history_chat_id bigint not null comment '聊天记录对应的聊天id',
    chat_history_date    text   not null comment '聊天记录日期',
    chat_history_content text   not null comment '聊天记录内容',
    constraint chat_history_chat_chat_id_fk
        foreign key (chat_history_chat_id) references chat (chat_id)
)
    comment '聊天记录信息';

create table if not exists report_task
(
    report_id          bigint auto_increment comment '任务举报id'
        primary key,
    report_task_id     bigint not null comment '举报任务id',
    report_reporter_id bigint not null comment '举报者id',
    report_content     text   not null comment '举报内容',
    constraint report_task_task_task_id_fk
        foreign key (report_task_id) references task (task_id),
    constraint report_task_user_user_id_fk
        foreign key (report_reporter_id) references user (user_id)
)
    comment '任务举报信息';

create table if not exists task_collect
(
    task_collect_user bigint not null comment '任务收藏用户id',
    task_collect_task bigint not null comment '被收藏任务id',
    task_collect_id   bigint auto_increment comment '任务收藏id'
        primary key,
    constraint task_collect_task_task_id_fk
        foreign key (task_collect_task) references task (task_id),
    constraint task_collect_user_user_id_fk
        foreign key (task_collect_user) references user (user_id)
)
    comment '任务收藏信息';

create table if not exists task_comment
(
    task_comment_id      bigint auto_increment comment '评论id'
        primary key,
    task_comment_task    bigint           null comment '评论对应的任务id',
    task_comment_content text             not null comment '评论内容',
    task_comment_like    bigint default 0 not null comment '评论点赞数',
    task_comment_rating  int              not null comment '评论给任务的评分',
    task_comment_user    bigint           not null comment '评论者id',
    constraint task_comment_task_task_id_fk
        foreign key (task_comment_task) references task (task_id),
    constraint task_comment_user_user_id_fk
        foreign key (task_comment_user) references user (user_id)
)
    comment '任务评论';

create table if not exists task_comment_like
(
    task_comment_like_user    bigint not null comment '点赞者id',
    task_comment_like_comment bigint not null comment '点赞评论id',
    task_comment_like_id      bigint auto_increment comment '点赞id'
        primary key,
    constraint task_comment_like_task_comment_task_comment_id_fk
        foreign key (task_comment_like_comment) references task_comment (task_comment_id),
    constraint task_comment_like_user_user_id_fk
        foreign key (task_comment_like_user) references user (user_id)
)
    comment '任务评论点赞';

create table if not exists task_message
(
    task_message_id      bigint auto_increment comment '留言id'
        primary key,
    task_message_task_id bigint           null comment '留言的对应任务的id',
    task_message_content text             not null comment '留言内容',
    task_message_like    bigint default 0 not null comment '留言点赞数',
    task_message_user    bigint           not null comment '留言者id',
    constraint task_message_task_task_id_fk
        foreign key (task_message_task_id) references task (task_id),
    constraint task_message_user_user_id_fk
        foreign key (task_message_user) references user (user_id)
)
    comment '留言表';

create table if not exists task_message_like
(
    task_message_like_user    bigint not null comment '点赞者id',
    task_message_like_message bigint not null comment '点赞留言id',
    task_message_like_id      bigint auto_increment comment '点赞id'
        primary key,
    constraint task_message_like_task_message_task_message_id_fk
        foreign key (task_message_like_message) references task_message (task_message_id),
    constraint task_message_like_user_user_id_fk
        foreign key (task_message_like_user) references user (user_id)
)
    comment '任务留言点赞';

create table if not exists user_auth
(
    user_auth_id       bigint auto_increment comment '账号id，每个账号唯一对应'
        primary key,
    user_auth_name     varchar(16) not null comment '账号名称',
    user_auth_password text        not null comment '账号密码',
    constraint user_auth_user_user_id_fk
        foreign key (user_auth_id) references user (user_id)
)
    comment '用户账号信息';

