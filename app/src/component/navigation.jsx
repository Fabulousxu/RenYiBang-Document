import {Menu} from 'antd';
import {useState} from 'react';
import {Link} from "react-router-dom";
import {MessageOutlined, UserOutlined} from '@ant-design/icons';
import {Header} from "antd/es/layout/layout";

export default function Navigation(props) {
  const items = [
    {
      key: 'task',
      label: <Link to='/task'>任务大厅</Link>
    },
    {
      key: 'service',
      label: <Link to='/service'>服务大厅</Link>
    },
    {
      key: 'help',
      label: <Link to='/help'>求助大厅</Link>
    },
    {
      key: 'message',
      label: <Link to='/message'>消息</Link>,
      icon: <MessageOutlined/>
    },
    {
      key: 'profile',
      label: <Link to='/profile'>用户</Link>,
      icon: <UserOutlined/>
    }
  ]
  const [current, setCurrent] = useState(props.page)
  const onClick = e => setCurrent(e.key)
  return (
    <>
      <Header
        style={{
          display: 'flex',
          alignItems: 'center',
          backgroundColor: '#f1e1ff',
          whiteSpace: 'nowrap'
        }}
      >
        <h1
          style={{
            width: '100%',
            color: '#4800ff',
            fontSize: '1.5rem',
            margin: '0 1rem',
            padding: '0.5rem'
          }}
        >
          任易帮
        </h1>
        <Menu
          onClick={onClick}
          selectedKeys={[current]}
          mode='horizontal'
          items={items}
          style={{
            backgroundColor: 'transparent',
          }}
        />
      </Header>
    </>
  );
}