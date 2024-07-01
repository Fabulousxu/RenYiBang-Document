package com.example.renyibang.converter;

import com.example.renyibang.enums.TaskStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Byte> {

	@Override
	public Byte convertToDatabaseColumn(TaskStatus status) {
		if (status == null) {
			return null;
		}
		return (byte) status.getCode();
	}

	@Override
	public TaskStatus convertToEntityAttribute(Byte code) {
		if (code == null) {
			return null;
		}
		return TaskStatus.fromCode(code);
	}
}