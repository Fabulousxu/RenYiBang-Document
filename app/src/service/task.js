import {apiURL, get} from "./util";

export async function searchTask(keyword, pageSize, pageIndex, order, timeRange, priceRange) {
  let res = await get(`${apiURL}/task/search?keyword=${keyword}&pageSize=${pageSize}&pageIndex=${pageIndex}&order=${order}&timeBegin=${timeRange[0]}&timeEnd=${timeRange[1]}&priceLow=${priceRange[0]}&priceHigh=${priceRange[1]}`)
  for (let task of res.items) task.url = `/task/${task.taskId}`
  return res
}

export async function getTask(taskId) {
  // 模拟得到的信息
  let task = {
    taskId: 1,
    title: '任务',
    description: '任务内容',
    images: ['https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png', 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png', 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png', 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png', 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png'],
    price: 2000,
    user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    },
    rating: 70,
  }

  let getResponse = {task}
  return getResponse.task
}

export async function getTaskComment(taskId, pageSize, pageIndex, order) {
  // 模拟得到的信息
  let item = {
    content: '评论内容', rating: 70, user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }, items = [], number = 0
  if (pageIndex === 0 || pageIndex === 1) number = 10; else if (pageIndex === 2) number = 2
  for (let i = 0; i < number; ++i) items.push(item)

  let getResponse = {total: 22, items}
  return getResponse
}

export async function getTaskMessage(taskId, pageSize, pageIndex, order) {
  // 模拟得到的信息
  let item = {
    content: '留言内容', rating: 70, user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }, items = [], number = 0
  if (pageIndex === 0 || pageIndex === 1 || pageIndex === 2) number = 10; else if (pageIndex === 3) number = 6
  for (let i = 0; i < number; ++i) items.push(item)

  let getResponse = {total: 36, items}
  return getResponse
}