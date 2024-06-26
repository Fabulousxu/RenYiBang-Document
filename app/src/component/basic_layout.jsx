import {useState} from 'react';
import {Link} from "react-router-dom";
import {Layout, Menu, theme} from 'antd';
import {Content, Header, Footer} from "antd/es/layout/layout";
import {
  AccountBookOutlined,
  MessageOutlined,
  PayCircleOutlined,
  QuestionCircleOutlined, TeamOutlined,
  ThunderboltOutlined,
  UserOutlined,
  PlusCircleOutlined
} from "@ant-design/icons";

export default function BasicLayout(props) {
  const items = [{
    key: 'task',
    label: <Link to='/task'>任务大厅</Link>,
    icon: <AccountBookOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }, {
    key: 'service',
    label: <Link to='/service'>服务大厅</Link>,
    icon: <ThunderboltOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }, {
    key: 'help',
    label: <Link to='/help'>求助大厅</Link>,
    icon: <QuestionCircleOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }, {
    key: 'issue',
    label: <Link to='/issue'>发布内容</Link>,
    icon: <PlusCircleOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }, {
    key: 'order', label: <Link to='/order'>订单</Link>, icon: <PayCircleOutlined/>, style: {
      fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif', marginLeft: 'auto'
    }
  }, {
    key: 'message',
    label: <Link to='/message'>消息</Link>,
    icon: <MessageOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }, {
    key: 'profile',
    label: <Link to='/profile'>用户</Link>,
    icon: <UserOutlined/>,
    style: {fontSize: '1.2rem', fontWeight: 'bold', fontFamily: 'SimSun, 宋体, serif'}
  }]
  const {token: {colorBgContainer, borderRadiusLG}} = theme.useToken();
  const [current, setCurrent] = useState(props.page)
  const onClick = e => setCurrent(e.key)
  return (<Layout>
    <Header
      style={{
        position: 'fixed',
        zIndex: '100',
        width: '100%',
        height: '4rem',
        display: 'flex',
        alignItems: 'center',
        backgroundColor: 'white',
        whiteSpace: 'nowrap'
      }}
    >
      <h1 style={{
        width: "20vw",
        color: "black",
        fontSize: "25px",
        fontFamily: "SimSun, 宋体, serif",
        fontWeight: "bold"
      }}><TeamOutlined/><PayCircleOutlined/><TeamOutlined/> 任易帮</h1>
      <Menu
        onClick={onClick}
        selectedKeys={[current]}
        mode='horizontal'
        items={items}
        style={{backgroundColor: 'transparent', flexGrow: 1}}
      />
    </Header>
    <Content
      style={{
        margin: '6rem 6% 20px',
        padding: '24px 48px',
        minHeight: 'calc(100vh - 10rem - 20px)',
        background: colorBgContainer,
        borderRadius: borderRadiusLG,
      }}
    >{props.children}</Content>
    <Footer style={{textAlign: 'center'}}>
      RenYiBang Design ©{new Date().getFullYear()} Created by RenYiBang
    </Footer>
  </Layout>);
}