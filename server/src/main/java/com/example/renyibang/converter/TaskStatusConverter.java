package com.example.renyibang.converter;

import com.example.renyibang.enums.TaskStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

	@Override
	public Integer convertToDatabaseColumn(TaskStatus attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public TaskStatus convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		}
		return TaskStatus.fromCode(dbData);
	}
}
