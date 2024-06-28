import React from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { Card, Avatar, Steps, Row, Col, Image, Typography, Divider, Space, Carousel, Button } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import BasicLayout from "../component/basic_layout";
import data from "../util/order_data";
import { changeOrderStatus } from "../service/order";

const { Title, Paragraph } = Typography;
const { Step } = Steps;

export default function OrderRecipientPage() {
  const { id } = useParams();
  const location = useLocation();
  const order = data.find(order => order.id === id);
  const isTask = location.pathname.includes('task');
  const isService = location.pathname.includes('service');

  const handleConfirm = async (id) => {
    try {
      let status = await changeOrderStatus(id, '已完成');
    } catch (error) {
      console.error(error);
    }
  };

  const handleCancel = async (id) => {
    try {
      let status = await changeOrderStatus(id, '已取消');
    } catch (error) {
      console.error(error);
    }
  };

  const handleComplete = async (id) => {
    try {
      let status = await changeOrderStatus(id, '待确认');
    } catch (error) {
      console.error(error);
    };
  }
  const getStatusStep = (status) => {
    switch (status) {
      case '进行中':
        return 1;
      case '待确认':
        return 2;
      case '已完成':
        return 3;
      case '已取消':
        return 3;
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
    <BasicLayout page='order-detail'>
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
            <Divider orientation="left">基本信息</Divider>
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
              <Step title="待确认" />
              <Step title={order.status === '已取消' ? '已取消' : '已完成'} />
            </Steps>
          </Typography>
          {(isService && order.status === "待确认") ? (
            <div>
              <Button type="primary" onClick={() => handleConfirm(order.id)} style={{margin: '20px'}}>确认完成</Button>
              <Button type="danger" onClick={() => handleCancel(order.id)} style={{margin: '20px'}}>发起退款</Button>
            </div>
          ) : null}
          {(isTask && order.status === "进行中") ? (
            <div>
              <Button type="primary" onClick={() => handleComplete(order.id)} style={{margin: '20px'}}>完成订单</Button>
            </div>
          ) : null}
        </Col>
      </Row>
    </BasicLayout>
  );
}
