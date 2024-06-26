export async function searchService(keyword, pageSize, pageIndex) {
  // 模拟得到的信息
  let item = {
    taskId: 1,
    title: '服务',
    cover: 'https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png',
    description: '服务内容',
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

  for (let item of getResponse.items) item.id = item.taskId
  return {total: getResponse.total, items: getResponse.items}
}