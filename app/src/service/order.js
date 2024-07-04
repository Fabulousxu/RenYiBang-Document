import {apiURL} from "./util";
import {get, post, put, del} from "./util";

export async function changeOrderStatus(id, status) {
	const url = `${apiURL}/changeOrderStatus?id=${id}&status=${status}`;
	return put(url, {status});
}

export async function fetchInitiatorTasks() {
	let userId = 1;
	const url = `${apiURL}/order/task/initiator/${userId}`;
	return get(url);
}

export async function fetchRecipientTasks() {
	let userId = 1;
	const url = `${apiURL}/order/task/recipient/${userId}`;
	return get(url);
}

export async function fetchInitiatorServices() {
	let userId = 1;
	const url = `${apiURL}/order/service/initiator/${userId}`;
	return get(url);
}

export async function fetchRecipientServices() {
	let userId = 1;
  const url = `${apiURL}/order/service/recipient/${userId}`;
	return get(url);
}

export async function fetchOrderById(id) {
	const url = `${apiURL}/order/${id}`;
	return get(url);
}