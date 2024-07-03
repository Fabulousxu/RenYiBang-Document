package com.example.renyibang.dto;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.entity.Order;
import com.example.renyibang.entity.ServiceOrder;
import com.example.renyibang.entity.TaskOrder;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.Data;

@Data
public class OrderDto {

	private String name;
	private String initiatorName;
	private String initiatorImg;
	private String recipientName;
	private String recipientImg;
	private int status;
	private LocalDateTime time;
	private List<String> orderImg;
	private String description;

	public OrderDto(Order order) {
		if(order.getType() == 0) {
			this.name = ((TaskOrder) order).getItem().getTitle();
			this.time = ((TaskOrder) order).getItem().getCreatedAt();
			this.orderImg = Collections.singletonList(((TaskOrder) order).getItem().getImages());
			this.description = ((TaskOrder) order).getItem().getDescription();
		} else {
			this.name = ((ServiceOrder) order).getItem().getTitle();
			this.time = ((ServiceOrder) order).getItem().getCreatedAt();
			this.orderImg = Collections.singletonList(((ServiceOrder) order).getItem().getImages());
			this.description = ((ServiceOrder) order).getItem().getDescription();
		}
		this.initiatorName = order.getOwner().getNickname();
		this.initiatorImg = order.getOwner().getAvatar();
		this.recipientName = order.getAccessor().getNickname();
		this.recipientImg = order.getAccessor().getAvatar();
		this.status = order.getStatus().getCode();
	}

	// Getters and setters

	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("name", name);
		json.put("initiator_name", initiatorName);
		json.put("initiator_img", initiatorImg);
		json.put("recipient_name", recipientName);
		json.put("recipient_img", recipientImg);
		json.put("status", status);
		json.put("time", time);
		json.put("order_img", orderImg);
		json.put("description", description);
		return json;
	}
}
