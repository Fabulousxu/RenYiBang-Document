import {Card, Col, Row, Avatar, Divider, Pagination} from "antd";
import Meta from "antd/es/card/Meta";
import Search from "antd/es/input/Search";
import {Link} from "react-router-dom";

export const totalEntry = 24;

export default function ItemList(props) {
  return (<div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
    <Search
      placeholder={props.placeholder}
      allowClear
      enterButton='搜索'
      size='large'
      style={{width: '60%'}}
      onSearch={props.onSearch}
    />
    <h3 style={{margin: '0 auto 0 10px'}}>{props.title}</h3>
    <Divider/>
    <Row gutter={[32, 24]}>
      {Array.from({length: totalEntry}).map((_, index) => (<Col
        key={index}
        xs={{span: 24 / 2}}
        sm={{span: 24 / 3}}
        md={{span: 24 / 3}}
        lg={{span: 24 / 4}}
        xl={{span: 24 / 4}}
      >
        <Link
          style={{visibility: index < props.list.length ? 'visible' : 'hidden'}}
          to={props.list[index]?.url}
        >
          <Card
            hoverable
            title={props.list[index]?.title}
            cover={<div
              style={{
                position: 'relative', width: '100%', paddingBottom: '100%'
              }}
            >
              <img
                alt='任务'
                src={props.list[index]?.cover}
                style={{
                  position: 'absolute',
                  top: 0,
                  left: 0,
                  width: '100%',
                  height: '100%',
                  objectFit: 'cover'
                }}
              />
            </div>}
          >
            <Row style={{alignItems: 'center'}}>
              <Col>
                <Link to={`/profile/${props.list[index]?.user.userId}`}>
                  <Meta
                    avatar={<Avatar src={props.list[index]?.user.avatar}/>}
                    title={props.list[index]?.user.username}
                    description={`帮帮评分: ${(props.list[index]?.user.rating / 10).toFixed(1)}`}
                  />
                </Link>
              </Col>
              <Col style={{marginLeft: 'auto', color: 'red', fontSize: '1rem'}}>
                ¥{(props.list[index]?.price / 100).toFixed(2)}
              </Col>
            </Row>
          </Card>
        </Link>
      </Col>))}
    </Row>
    <Pagination
      pageSize={totalEntry}
      total={props.total}
      current={props.currentPage}
      showSizeChanger={false}
      style={{marginTop: '24px'}}
      onChange={props.onChange}
    />
  </div>)
}