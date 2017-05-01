package com.kedialabs.converters;

import java.util.List;

import com.kedialabs.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum JsonListConverter {
    
    INSTANCE;
    
    public String convertToDatabaseColumn(List<?> value){
        try{
            return JsonSerializer.INSTANCE.serialize(value);
        }catch(Exception ex){
            log.error("unable to serialize",ex);
        }
        return "[]";
    }

    public List<?> convertToEntityAttribute(String value) {
        try{
            return JsonSerializer.INSTANCE.deserialize(value, List.class); 
        }catch(Exception ex){
            log.error("unable to deserialize",ex);
        }
        return null;
    }

}
