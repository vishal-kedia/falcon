package com.kedialabs.converters;

import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.kedialabs.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Converter
@Slf4j
public class JsonMapConverter implements AttributeConverter<Map<String,Object>, String> {
    
    @Override
    public String convertToDatabaseColumn(Map<String, Object> value){
        try{
            return JsonSerializer.INSTANCE.serialize(value);
        }catch(Exception ex){
            log.error("unable to serialize",ex);
        }
        return "{}";
    }
    
    @Override
    public Map<String,Object> convertToEntityAttribute(String value) {
        try{
            return JsonSerializer.INSTANCE.deserialize(value, Map.class); 
        }catch(Exception ex){
            log.error("unable to deserialize",ex);
        }
        return null;
    }

}
