import {Avatar, Card, Carousel, Col, Divider, Image, Row} from "antd";
import React from "react";
import {Link} from "react-router-dom";
import Meta from "antd/es/card/Meta";

export default function ItemDetail(props) {
  return (<Row gutter={16}>
    <Col>
      <Carousel style={{width: '400px', height: '400px'}}>
        {props.detail?.images.map((src, index) => (<div
          key={index}
          style={{width: '400px', height: '400px', overflow: 'hidden'}}
        >
          <Image
            src={src}
            style={{width: '400px', height: '400px', objectFit: 'cover'}}
          />
        </div>))}
      </Carousel>
    </Col>
    <Col style={{flexGrow: 1}}>
      <Card
        title={<h1 style={{fontSize: '2rem'}}>{props.detail?.title}</h1>}
        style={{border: 'none'}}
      >
        <Row style={{alignItems: 'center'}}>
          <Col>
            <Link to={`/profile/${props.detail?.user.userId}`}>
              <Meta
                avatar={<Avatar src={props.detail?.user.avatar}/>}
                title={props.detail?.user.username}
                description={`帮帮评分: ${(props.detail?.user.rating / 10).toFixed(1)}`}
              />
            </Link>
          </Col>
          <Col style={{marginLeft: 'auto', fontSize: '1.6rem', color: 'red'}}>
            ¥{(props.detail?.price / 100).toFixed(2)}
          </Col>
        </Row>
      </Card>
      <Divider>{props.descriptionTitle}</Divider>
      <p style={{padding: '12px 24px'}}>{props.detail?.description}</p>
    </Col>
  </Row>)
}