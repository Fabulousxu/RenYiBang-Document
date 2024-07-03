package com.example.renyibang.entity;

import com.alibaba.fastjson2.JSONObject;
import jakarta.persistence.*;
	import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "service_order", schema = "renyibang")
@NoArgsConstructor
public class ServiceOrder extends Order<Service> {

	@ManyToOne
	@JoinColumn(name = "service_id", referencedColumnName = "service_id", foreignKey = @ForeignKey(name = "FK_SERVICE"))
	private Service item;

  @Override
  public Service getItem() {
    return this.item;
	}

	@Override
	public void setItem(Service item) {
		this.item = item;
	}

	public JSONObject toJSON() {
		JSONObject json = super.toJSON();
		json.put("time", item.getCreatedAt());
		json.put("name", item.getTitle());
		return json;
	}

	// equals and hashCode methods
	// ...
}
