package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import com.example.renyibang.enums.OrderStatus;
import jakarta.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
//@MappedSuperclass
@Table(name = "`order`")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Order<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long orderId;

	@Column(name = "type")
	private Byte type;

	@ManyToOne
	@JoinColumn(name = "owner_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_OWNER"))
	private User owner;

	@ManyToOne
	@JoinColumn(name = "accessor_id", referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "FK_ACCESSOR"))
	private User accessor;

	@Column(name = "status")
	private OrderStatus status;

	@Column(name = "cost")
	private long cost;

	// Getters and setters
	// ...

	public abstract T getItem();
	public abstract void setItem(T item);

	public T orElse(Object o) {
		return null;
	}

	public JSONObject toJSON(){
		JSONObject json = new JSONObject();
		json.put("id", orderId);
		json.put("type", type);
		json.put("initiator", owner.getNickname());
		json.put("recipient", accessor.getNickname());
		json.put("status", status.getCode());
		json.put("cost", cost);
		return json;
	}
}
