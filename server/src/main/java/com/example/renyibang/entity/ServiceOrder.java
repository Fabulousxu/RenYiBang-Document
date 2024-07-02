package com.example.renyibang.entity;

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

	// equals and hashCode methods
	// ...
}
