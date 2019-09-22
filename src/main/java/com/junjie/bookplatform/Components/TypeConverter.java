/*
 * Copyright (c)-- Created By JUNJIE Lin -> On 2019/9/22
 */

package com.junjie.bookplatform.Components;

import com.junjie.bookplatform.Model.Type;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TypeConverter implements AttributeConverter<Type, String> {
    @Override
    public String convertToDatabaseColumn(Type attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute.toString();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        if (dbData==null) {
            return null;
        }
        return Stream.of(Type.values())
                .filter(t->t.toString().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
