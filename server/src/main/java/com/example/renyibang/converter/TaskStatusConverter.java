package com.example.renyibang.converter;

import com.example.renyibang.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<OrderStatus, Byte> {

	@Override
	public Byte convertToDatabaseColumn(OrderStatus status) {
		if (status == null) {
			return null;
		}
		return (byte) status.getCode();
	}

	@Override
	public OrderStatus convertToEntityAttribute(Byte code) {
		if (code == null) {
			return null;
		}
		return OrderStatus.fromCode(code);
	}
}