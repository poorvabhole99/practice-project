package com.practice.practiceProject.helper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class SetToStringConverter implements AttributeConverter<Set<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(Set<String> attribute) {
        return attribute != null ? attribute.stream().collect(Collectors.joining(SPLIT_CHAR)) : "";
    }

    @Override
    public Set<String> convertToEntityAttribute(String dbData) {
        return dbData != null ? new HashSet<>(Arrays.asList(dbData.split(SPLIT_CHAR))) : new HashSet<>();
    }
}
