import { Button, Input, Upload, Image, Radio, Modal } from 'antd';
import React, { useState } from 'react';
import { PlusOutlined } from '@ant-design/icons';
import BasicLayout from '../component/basic_layout';

const getBase64 = (file) =>
    new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = () => resolve(reader.result);
      reader.onerror = (error) => reject(error);
    });

export default function IssuePage() {
  const [previewOpen, setPreviewOpen] = useState(false);
  const [previewImage, setPreviewImage] = useState('');
  const [fileList, setFileList] = useState([
    {
      uid: '-1',
      name: 'image.png',
      status: 'done',
      url: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    },
    {
      uid: '-2',
      name: 'image.png',
      status: 'done',
      url: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    },
    {
      uid: '-3',
      name: 'image.png',
      status: 'done',
      url: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    },
    {
      uid: '-4',
      name: 'image.png',
      status: 'done',
      url: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    },
  ]);
  const uploadUrl = '';

  const handlePreview = async (file) => {
    if (!file.url && !file.preview) {
      file.preview = await getBase64(file.originFileObj);
    }
    setPreviewImage(file.url || file.preview);
    setPreviewOpen(true);
  };
  const handleChange = ({ fileList: newFileList }) => setFileList(newFileList);
  const uploadButton = (
    <button
      style={{
        border: 0,
        background: 'none',
      }}
      type="button"
    >
      <PlusOutlined />
      <div
        style={{
          marginTop: 8,
        }}
      >
        Upload
      </div>
    </button>
  );
  //上传图片界面设置

  const [value, setValue] = useState(1);
  const onRadioChange = (e) => {
    setValue(e.target.value);
  };
  //单选框设置

  const handleSubmit = () => { 
    Modal.success({
      content: '发布成功',
    });
  }

  return(
    <BasicLayout page='issue'>
      <h1>发布一个新的内容</h1>
      <div style={{ display: 'flex', justifyContent: 'flex-start' }}>
        <div style={{ width: '40%', display: 'flex', 'flex-direction': 'column' }}>
          <h3>请上传预览照片</h3>
          <Upload
          action={uploadUrl}
          listType="picture-card"
          fileList={fileList}
          onPreview={handlePreview}
          onChange={handleChange}
          >
          {fileList.length >= 8 ? null : uploadButton}
          </Upload>
          {previewImage && (
          <Image
            wrapperStyle={{
            display: 'none',
            }}
            preview={{
            visible: previewOpen,
            onVisibleChange: (visible) => setPreviewOpen(visible),
            afterOpenChange: (visible) => !visible && setPreviewImage(''),
            }}
            src={previewImage}
          />
          )}
        </div>
        <div style={{ width: '40%', display: 'flex', 'flex-direction': 'column' }}>
          <Input placeholder="请输入标题" style={{ margin: '20px', width: '80%' }} size='large'/>
          <h3 style={{ margin: '20px' }}>请选择发布类型</h3>
          <Radio.Group onChange={onRadioChange} value={value} style={{ margin: '20px', width: '80%' }}>
            <Radio value={1}>任务</Radio>
            <Radio value={2}>服务</Radio>
          </Radio.Group>
          <Input.TextArea placeholder="请输入描述" style={{ margin: '20px', width: '80%' }}/>
          <Input placeholder="初步定价" addonBefore="￥" style={{ margin: '20px', width: '80%' }}/>
          <Button type="primary" style={{ margin: '20px', width: '100px' }} onClick={handleSubmit}>提交</Button>
        </div>
      </div>
    </BasicLayout>
  )
}