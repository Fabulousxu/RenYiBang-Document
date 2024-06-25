import {Card, Col, Row, Avatar} from "antd";
import Meta from "antd/es/card/Meta";

export default function ItemList(props) {
  const totalColumns = 24;

  return (
    <Row gutter={[16, 16]}>
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
              title='任务标题'
              cover={
                <div style={{
                  position: 'relative',
                  width: '100%',
                  paddingBottom: '100%',
                  overflow: 'hidden'
                }}>
                  <img
                    alt='example'
                    src='https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png'
                    style={{
                      position: 'absolute',
                      top: 0,
                      left: 0,
                      width: '100%',
                      height: '100%',
                      objectFit: 'cover'
                    }}
                  />
                </div>
              }
            >
              <Meta
                avatar={<Avatar src="https://api.dicebear.com/7.x/miniavs/svg?seed=8"/>}
                title={'用户名'}
              />
            </Card>
          </Col>
        ))
      }
    </Row>
  )
    ;
}