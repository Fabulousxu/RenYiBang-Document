import React, { useState, useMemo } from 'react';
import BasicLayout from "../component/basic_layout";
import { Table, Tag, Tabs } from 'antd';
import { Link } from 'react-router-dom';
import data from '../util/order_data';
import moment from 'moment';

const { TabPane } = Tabs;

export default function OrderPage() {
    const [activeTab, setActiveTab] = useState('1');

    const handleTabChange = (key) => {
        setActiveTab(key);
    };

    const filteredData = useMemo(() => {
        switch (activeTab) {
            case '1':
                return data;
            case '2':
                return data;
            case '3':
                return data;
            case '4':
                return data;
            default:
                return data;
        }
    }, [activeTab]);

    const generateLink = (record) => {
        switch (activeTab) {
            case '1':
                return `/order/task/initiator/${record.id}`;
            case '2':
                return `/order/task/recipient/${record.id}`;
            case '3':
                return `/order/service/initiator/${record.id}`;
            case '4':
                return `/order/service/recipient/${record.id}`;
            default:
                return `/order/${record.id}`;
        }
    };

    const columns = [{
        title: '任务标题',
        dataIndex: 'title',
        key: 'title',
        render: (text, record) => <Link to={generateLink(record)}>{text}</Link>,
    }, {
        title: '发起人', dataIndex: 'initiator', key: 'initiator',
    }, {
        title: '接收人', dataIndex: 'receiver', key: 'receiver',
    }, {
        title: '发起时间',
        dataIndex: 'startTime',
        key: 'startTime',
        sorter: (a, b) => moment(a.startTime).unix() - moment(b.startTime).unix(),
    }, {
        title: '当前状态',
        dataIndex: 'status',
        key: 'status',
        filters: [
            { text: '进行中', value: '进行中' },
            { text: '已完成', value: '已完成' },
            { text: '已取消', value: '已取消' },
        ],
        onFilter: (value, record) => record.status.includes(value),
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
    },];

    return (
      <BasicLayout page='order'>
          <Tabs defaultActiveKey="1" onChange={handleTabChange}>
              <TabPane tab="我发布的任务" key="1">
                  <Table columns={columns} dataSource={filteredData} />
              </TabPane>
              <TabPane tab="我接收的任务" key="2">
                  <Table columns={columns} dataSource={filteredData} />
              </TabPane>
              <TabPane tab="我发布的服务" key="3">
                  <Table columns={columns} dataSource={filteredData} />
              </TabPane>
              <TabPane tab="我接收的服务" key="4">
                  <Table columns={columns} dataSource={filteredData} />
              </TabPane>
          </Tabs>
      </BasicLayout>
    );
}
