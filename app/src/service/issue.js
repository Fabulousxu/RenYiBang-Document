import {apiURL, post} from './util'

export async function issueService(title, description, price, images) {
  let res = await post(`${apiURL}/issue/service`, {title, description, price, images})
  return res;
}

export async function issueTask(title, description, price, images) {
  let res = await post(`${apiURL}/issue/task`, {title, description, price, images})
  return res;
}