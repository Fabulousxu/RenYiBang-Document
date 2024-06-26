// profile_page.jsx
import React, { useEffect, useState } from 'react';
import BasicLayout from "../component/basic_layout";
import { Descriptions, Avatar, List, Typography, Tag } from 'antd';
import { getUserProfile, getUserTasks } from '../service/user';
import {Link} from "react-router-dom";
import {useParams} from "react-router-dom";

const { Title } = Typography;

export default function ProfilePage() {
    const [user, setUser] = useState({});
    const [tasks, setTasks] = useState([]);
    const { id } = useParams();

    useEffect(() => {
        getUserProfile().then(res => {
            setUser(res.user);
        }).catch(err => {
            console.error(err);
        });

        getUserTasks().then(res => {
            setTasks(res.tasks);
        }).catch(err => {
            console.error(err);
        });
    }, []);

    return (
        <BasicLayout page='profile'>
            <div style={{ display: 'flex', alignItems: 'center', marginBottom: 24 }}>
                <Avatar size={64} src={user.user_avatar} />
                <div style={{ marginLeft: 24 }}>
                    <Title level={2}>{user.user_nickname}</Title>
                    <Title level={4} type="secondary">{user.user_intro}</Title>
                </div>
            </div>

            <Descriptions title="用户信息" bordered>
                <Descriptions.Item label="用户名">{user.user_nickname}</Descriptions.Item>
                <Descriptions.Item label="管理员">
                    {user.user_admin ? <Tag color="red">是</Tag> : <Tag color="blue">否</Tag>}
                </Descriptions.Item>
                <Descriptions.Item label="评分">{(user.user_rating / 10).toFixed(1)}</Descriptions.Item>
                <Descriptions.Item label="余额">{(user.user_balance / 100).toFixed(2)}元</Descriptions.Item>
                <Descriptions.Item label="关注者">{user.user_following}</Descriptions.Item>
            </Descriptions>

            <Title level={3} style={{ marginTop: 24 }}>用户任务</Title>
            <List
                itemLayout="horizontal"
                dataSource={tasks}
                renderItem={task => (
                    <List.Item>
                        <List.Item.Meta
                            title={<Link to={`/task/${task.id}`}>{task.title}</Link>}
                            description={task.description}
                        />
                        <div>{task.status}</div>
                    </List.Item>
                )}
            />
        </BasicLayout>
    );
}
