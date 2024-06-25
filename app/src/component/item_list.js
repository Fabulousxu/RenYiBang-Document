import {Card, Col, Row, Avatar} from "antd";
import Meta from "antd/es/card/Meta";

export default function ItemList(props) {
  const totalColumns = 24;

  return (
    <Row
      gutter={[16, 16]}
      style={{
        padding: '8px',
        border: '1px solid black'
      }}
    >
      {
        Array.from({length: totalColumns}).map(index => (
          <Col
            key={index}
            xs={{span: 24 / 2}}
            sm={{span: 24 / 3}}
            md={{span: 24 / 3}}
            lg={{span: 24 / 4}}
            xl={{span: 24 / 4}}
          >
            <Card
              hoverable
              title={'任务标题'}
            >
              <Meta
                avatar=<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8"/>
              title={'用户名'}
              />

            </Card>
          </Col>
        ))
      }
    </Row>
  );
}