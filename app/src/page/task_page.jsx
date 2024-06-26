import ItemList from "../component/item_list";
import BasicLayout from "../component/basic_layout";
import {searchTask} from "../service/task";
import {useEffect, useState} from "react";
import {totalEntry} from "../component/item_list";

export default function TaskPage() {
  const [total, setTotal] = useState(0)
  const [taskList, setTaskList] = useState([])
  const [currentPage, setCurrentPage] = useState(1)
  const [keyword, setKeyword] = useState('')
  useEffect(() => {
    searchTask('', totalEntry, 0).then(res => {
      setTotal(res.total)
      setTaskList(res.items)
    }).catch(err => {
    })
  }, [])
  return (<BasicLayout page='task'>
    <ItemList
      title={`任务 ${total}条`}
      placeholder='请输入任务关键词或用户关键词来搜索相关任务'
      value={keyword}
      onSearch={value => {
        setKeyword(value)
        searchTask(value, totalEntry, 0).then(res => {
          setTotal(res.total)
          setTaskList(res.items)
          setCurrentPage(1)
        }).catch(err => {
        })
      }}
      list={taskList}
      total={total}
      currentPage={currentPage}
      onChange={(page, pageSize) => {
        searchTask('', pageSize, page - 1).then(res => {
          setTotal(res.total)
          setTaskList(res.items)
          setCurrentPage(page)
        }).catch(err => {
        })
      }}
    />
  </BasicLayout>)
}