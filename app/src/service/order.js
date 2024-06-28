import { apiURL } from "./util";
import { get, post, put, del } from "./util";

export async function changeOrderStatus(id, status) {
  const url = `${apiURL}/changeOrderStatus?id=${id}&status=${status}`;
    return put(url, { status });
}