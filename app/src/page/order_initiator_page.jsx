import React from 'react';
import { useParams } from 'react-router-dom';
import { Card, Avatar, Steps, Row, Col, Image, Typography, Divider, Space, Carousel } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import BasicLayout from "../component/basic_layout";
import data from "../util/order_data";

const { Title, Paragraph } = Typography;
const { Step } = Steps;

export default function OrderInitiatorPage() {
  const { id } = useParams();
  const order = data.find(order => order.id === id);
  const getStatusStep = (status) => {
    switch (status) {
      case '进行中':
        return 1;
      case '已完成':
        return 2;
      case '已取消':
        return 2;
      default:
        return 0;
    }
  };

  const statusStep = getStatusStep(order ? order.status : '');

  if (!order) {
    return (
      <BasicLayout page='order-detail'>
        <Card style={{ margin: '20px' }}>
          <h1>订单未找到</h1>
        </Card>
      </BasicLayout>
    );
  }
  return (
    <BasicLayout>
      <Row gutter={16}>
        <Col span={9}>
          <Carousel autoplay>
            {order.images && order.images.map((imageUrl, index) => (
              <div key={index}>
                <Image
                  src={imageUrl}
                  alt={`Order image ${index + 1}`}
                  style={{ width: '100%', height: 'auto' }}
                />
              </div>
            ))}
          </Carousel>
        </Col>
        <Col span={15}>
          <Typography>
            <Title level={3}>{order.title}</Title>
            <Divider orientation="left">基本信息ini</Divider>
            <Space direction="vertical" size="middle">
              <Row align="middle">
                <Col>
                  <Avatar icon={<UserOutlined />} src={`path_to_initiator_avatar/${order.initiator}.png`} style={{ marginRight: 8 }} />
                  {`发起人：${order.initiator}`}
                </Col>
                <Col>
                  <Avatar icon={<UserOutlined />} src={`path_to_receiver_avatar/${order.receiver}.png`} style={{ marginRight: 8, marginLeft: 16 }} />
                  {`接收人：${order.receiver}`}
                </Col>
              </Row>
              <Divider type="vertical" />
              {`发起时间：${order.startTime}`}
            </Space>
            <Divider orientation="left">任务描述</Divider>
            <Paragraph>
              {order.description}
            </Paragraph>
            <Divider orientation="left">当前状态</Divider>
            <Steps current={statusStep} size="small">
              <Step title="创建" />
              <Step title="进行中" />
              <Step title={order.status === '已取消' ? '已取消' : '已完成'} />
            </Steps>
          </Typography>
        </Col>
      </Row>
    </BasicLayout>
  );
}
