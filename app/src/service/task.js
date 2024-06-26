export async function searchTask(keyword, pageSize, pageIndex) {
  // 模拟得到的信息
  let item = {
    taskId: 1,
    title: '任务',
    cover: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    description: '任务内容',
    price: 2000,
    user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }, items = [], number = 0
  if (pageIndex === 0 || pageIndex === 1) number = 24
  else if (pageIndex === 2) number = 2
  for (let i = 0; i < number; ++i) items.push(item)

  let getResponse = {total: 50, items}
  for (let item of getResponse.items) item.url = `/task/${item.taskId}`
  return getResponse
}

export async function getTask(taskId) {
  // 模拟得到的信息
  let task = {
    taskId: 1,
    title: '任务',
    description: '任务内容',
    images: [
      'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
      'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
      'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
      'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
      'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png'
    ],
    price: 2000,
    user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }

  let getResponse = {task}
  return getResponse.task
}

export async function getTaskComment(taskId, pageSize, pageIndex, order) {
  // 模拟得到的信息
  let item = {
    content: '评论内容',
    rating: 70,
    user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }, items = [], number = 0
  if (pageIndex === 0 || pageIndex === 1) number = 10
  else if (pageIndex === 2) number = 2
  for (let i = 0; i < number; ++i) items.push(item)

  let getResponse = {total: 22, items}
  return getResponse
}

export async function getTaskMessage(taskId, pageSize, pageIndex, order) {
  // 模拟得到的信息
  let item = {
    content: '留言内容',
    rating: 70,
    user: {
      userId: 1,
      avatar: 'https://api.dicebear.com/7.x/miniavs/svg?seed=8',
      username: '用户',
      rating: 98
    }
  }, items = [], number = 0
  if (pageIndex === 0 || pageIndex === 1 || pageIndex === 2) number = 10
  else if (pageIndex === 3) number = 6
  for (let i = 0; i < number; ++i) items.push(item)

  let getResponse = {total: 36, items}
  return getResponse
}