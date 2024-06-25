// profile_page.jsx
import React, { useEffect, useState } from 'react';
import BasicLayout from "../component/basic_layout";
import { Descriptions, Avatar, List, Typography } from 'antd';
import { getUserProfile, getUserTasks } from '../service/user';
import {Link} from "react-router-dom";

const { Title } = Typography;

export default function ProfilePage() {
    const [user, setUser] = useState({});
    const [tasks, setTasks] = useState([]);

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
                <Avatar size={64} src={user.avatar} />
                <div style={{ marginLeft: 24 }}>
                    <Title level={2}>{user.name}</Title>
                    <Title level={4} type="secondary">{user.email}</Title>
                </div>
            </div>

            <Descriptions title="用户信息" bordered>
                <Descriptions.Item label="用户名">{user.username}</Descriptions.Item>
                <Descriptions.Item label="角色">{user.role}</Descriptions.Item>
                <Descriptions.Item label="创建时间">{user.createdAt}</Descriptions.Item>
                <Descriptions.Item label="上次登录时间">{user.lastLogin}</Descriptions.Item>
                <Descriptions.Item label="状态">{user.status}</Descriptions.Item>
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
