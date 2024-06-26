import BasicLayout from "../component/basic_layout";
import {useEffect, useState} from "react";
import ItemList, {totalEntry} from "../component/item_list";
import {searchService} from "../service/service";

export default function ServicePage() {
  const [total, setTotal] = useState(0)
  const [serviceList, setServiceList] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [keyword, setKeyword] = useState('')
  useEffect(() => {
    searchService('', totalEntry, 0).then(res => {
      setTotal(res.total)
      setServiceList(res.items)
    }).catch(err => {
    })
  }, [])
  return (
    <BasicLayout page='service'>
      <ItemList
        title={`服务 ${total}条`}
        placeholder='请输入任务关键词或用户关键词来搜索相关任务'
        value={keyword}
        onSearch={value => {
          setKeyword(value)
          searchService(value, totalEntry, 0).then(res => {
            setTotal(res.total)
            setServiceList(res.items)
            setCurrentPage(1)
          }).catch(err => {
          })
        }}
        list={serviceList}
        total={total}
        currentPage={currentPage}
        onChange={(page, pageSize) => {
          searchService('', pageSize, page - 1).then(res => {
            setTotal(res.total)
            setServiceList(res.items)
            setCurrentPage(page)
          }).catch(err => {
          })
        }}
      />
    </BasicLayout>
  )
}