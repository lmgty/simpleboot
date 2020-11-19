package com.github.simpleboot.serialize;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author LiuYe
 * @data 2020/11/18
 */
public class JacksonSerialize implements Serializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(Object object) {
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        T object = null;

        try {
            object = objectMapper.readValue(bytes, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }
}
