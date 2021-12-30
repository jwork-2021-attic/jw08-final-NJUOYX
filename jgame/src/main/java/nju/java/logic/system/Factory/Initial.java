package nju.java.logic.system.Factory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Initial{
    private ObjectMapper objectMapper = new ObjectMapper();

    private static Initial INSTANCE = new Initial();

    private Initial(){
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.PUBLIC_ONLY);
    }

    public static Initial getINSTANCE() {
        return INSTANCE;
    }

    public ObjectMapper getObjectMapper(){
        return objectMapper;
    }
}
