import {apiURL, post} from './util'

export async function issueService(newitem) {
  let res = await post(`${apiURL}/issue/service`, newitem)
  return res;
}

export async function issueTask(newitem) {
  let res = await post(`${apiURL}/issue/task`, newitem)
  return res;
}