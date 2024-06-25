import React from 'react';
import BasicLayout from "../component/basic_layout";
import { Table, Tag } from 'antd';
import { Link } from 'react-router-dom';

export default function OrderPage() {
    const columns = [
        {
            title: '任务标题',
            dataIndex: 'title',
            key: 'title',
            render: (text, record) => <Link to={`/order/${record.id}`}>{text}</Link>,
        },
        {
            title: '发起人',
            dataIndex: 'initiator',
            key: 'initiator',
        },
        {
            title: '接收人',
            dataIndex: 'receiver',
            key: 'receiver',
        },
        {
            title: '发起时间',
            dataIndex: 'startTime',
            key: 'startTime',
        },
        {
            title: '当前状态',
            dataIndex: 'status',
            key: 'status',
            render: (status) => {
                let color = '';
                switch (status) {
                    case '进行中':
                        color = 'blue';
                        break;
                    case '已完成':
                        color = 'green';
                        break;
                    case '已取消':
                        color = 'red';
                        break;
                    default:
                        color = 'gray';
                }
                return <Tag color={color}>{status}</Tag>;
            },
        },
    ];

    const data = [
        {
            key: '1',
            id: '1',
            title: '开发新功能',
            initiator: '李华',
            receiver: '张伟',
            startTime: '2024-06-25 10:00',
            status: '进行中',
        },
        {
            key: '2',
            id: '2',
            title: '市场调研',
            initiator: '王芳',
            receiver: 'Helen',
            startTime: '2024-06-24 09:00',
            status: '已完成',
        },
        {
            key: '3',
            id: '3',
            title: '撰写报告',
            initiator: '赵强',
            receiver: '李娜',
            startTime: '2024-06-23 14:00',
            status: '进行中',
        },
        {
            key: '4',
            id: '4',
            title: '产品测试',
            initiator: '孙丽',
            receiver: 'Chris',
            startTime: '2024-06-22 13:30',
            status: '已取消',
        },
        {
            key: '5',
            id: '5',
            title: '客户拜访',
            initiator: '周杰',
            receiver: 'Emily',
            startTime: '2024-06-21 11:00',
            status: '进行中',
        },
        {
            key: '6',
            id: '6',
            title: '设计方案',
            initiator: '冯雪',
            receiver: 'Michael',
            startTime: '2024-06-20 16:00',
            status: '已完成',
        },
        {
            key: '7',
            id: '7',
            title: '代码审查',
            initiator: '陈晨',
            receiver: 'Tom',
            startTime: '2024-06-19 10:30',
            status: '进行中',
        },
        {
            key: '8',
            id: '8',
            title: '招聘面试',
            initiator: '李强',
            receiver: 'Anna',
            startTime: '2024-06-18 09:00',
            status: '已完成',
        },
        {
            key: '9',
            id: '9',
            title: '财务审计',
            initiator: '刘芳',
            receiver: 'James',
            startTime: '2024-06-17 15:00',
            status: '进行中',
        },
        {
            key: '10',
            id: '10',
            title: '培训新员工',
            initiator: '王磊',
            receiver: 'Sarah',
            startTime: '2024-06-16 14:00',
            status: '已取消',
        },
    ];

    return (
        <BasicLayout page='order'>
            <Table columns={columns} dataSource={data} />
        </BasicLayout>
    );
}
