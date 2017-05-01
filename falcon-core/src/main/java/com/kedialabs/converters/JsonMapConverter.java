package com.kedialabs.converters;

import java.util.Map;

import com.kedialabs.serializer.JsonSerializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum JsonMapConverter {
    
    INSTANCE;
    
    public String convertToDatabaseColumn(Map<?,?> value){
        try{
            return JsonSerializer.INSTANCE.serialize(value);
        }catch(Exception ex){
            log.error("unable to serialize",ex);
        }
        return "{}";
    }

    public Map<?,?> convertToEntityAttribute(String value) {
        try{
            return JsonSerializer.INSTANCE.deserialize(value, Map.class); 
        }catch(Exception ex){
            log.error("unable to deserialize",ex);
        }
        return null;
    }

}
