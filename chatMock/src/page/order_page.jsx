import React from 'react';
import BasicLayout from "../component/basic_layout";
import {Table, Tag} from 'antd';
import {Link} from 'react-router-dom';
import data from '../util/order_data'

export default function OrderPage() {
    const columns = [{
        title: '任务标题',
        dataIndex: 'title',
        key: 'title',
        render: (text, record) => <Link to={`/order/${record.id}`}>{text}</Link>,
    }, {
        title: '发起人', dataIndex: 'initiator', key: 'initiator',
    }, {
        title: '接收人', dataIndex: 'receiver', key: 'receiver',
    }, {
        title: '发起时间', dataIndex: 'startTime', key: 'startTime',
    }, {
        title: '当前状态', dataIndex: 'status', key: 'status', render: (status) => {
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
    },];


    return (<BasicLayout page='order'>
        <Table columns={columns} dataSource={data}/>
    </BasicLayout>);
}
