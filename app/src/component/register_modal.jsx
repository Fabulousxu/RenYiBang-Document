import React, { useState } from 'react';
import { Modal, Form, Input, Button, notification, Upload } from 'antd';
import { UserOutlined, LockOutlined, SmileOutlined, InfoCircleOutlined } from '@ant-design/icons';
import axios from 'axios';

const { TextArea } = Input;

const RegisterModal = ({ visible, onCancel }) => {
  const [form] = Form.useForm();
  const [loading, setLoading] = useState(false);
  const [userId, setUserId] = useState(null);

  const handleRegister = async (values) => {
    const { name, password, confirmPassword, nickname, intro, avatar } = values;

    if (password !== confirmPassword) {
      notification.error({
        message: '注册失败',
        description: '两次输入的密码不一致',
        placement: 'topRight',
      });
      return;
    }

    setLoading(true);
    try {
      const url = `${process.env.REACT_APP_API_URL}/user/register`;
      const response = await axios.post(url, {
        name,
        password,
        nickname,
        intro,
        avatar
      });

      const { data } = response;

      if (data.status !== 'success') {
        throw new Error(data.message || '注册失败，请重试');
      }

      setUserId(data.data.userId);
      notification.success({
        message: '注册成功',
        description: `您的账号ID是：${data.data.userId}，请记住此ID！`,
        placement: 'topRight',
      });
      form.resetFields();
    } catch (error) {
      notification.error({
        message: '注册失败',
        description: error.message || '注册失败，请重试',
        placement: 'topRight',
      });
    } finally {
      setLoading(false);
    }
  };

  return (
    <Modal
      visible={visible}
      title="注册"
      onCancel={onCancel}
      footer={null}
    >
      <Form form={form} onFinish={handleRegister}>
        <Form.Item
          name="name"
          rules={[{ required: true, message: '请输入你的用户名!' }]}
        >
          <Input
            prefix={<UserOutlined className="site-form-item-icon" />}
            placeholder="用户名"
          />
        </Form.Item>
        <Form.Item
          name="password"
          rules={[{ required: true, message: '请输入你的密码!' }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="密码"
          />
        </Form.Item>
        <Form.Item
          name="confirmPassword"
          rules={[{ required: true, message: '请再次输入你的密码!' }]}
        >
          <Input
            prefix={<LockOutlined className="site-form-item-icon" />}
            type="password"
            placeholder="确认密码"
          />
        </Form.Item>
        <Form.Item
          name="nickname"
          rules={[{ required: true, message: '请输入你的昵称!' }]}
        >
          <Input
            prefix={<SmileOutlined className="site-form-item-icon" />}
            placeholder="昵称"
          />
        </Form.Item>
        <Form.Item
          name="intro"
        >
          <TextArea
            prefix={<InfoCircleOutlined className="site-form-item-icon" />}
            placeholder="自我简介"
            rows={3}
          />
        </Form.Item>
        <Form.Item
          name="avatar"
        >
          <Input
            placeholder="头像 URL"
          />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" style={{ width: '100%' }} loading={loading}>
            注册
          </Button>
        </Form.Item>
      </Form>
      {userId && (
        <div style={{ marginTop: 16, textAlign: 'center' }}>
          <p>您的账号ID是：{userId}，请记住此ID！</p>
        </div>
      )}
    </Modal>
  );
};

export default RegisterModal;
