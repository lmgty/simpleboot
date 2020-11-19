package com.github.simpleboot.serialize.impl;

import com.github.simpleboot.serialize.JacksonSerialize;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author LiuYe
 * @data 2020/11/19
 */
public class JacksonSerializerTest {

    @Test
    public void should_serialize_object_and_deserialize_from_bytes() {
        JacksonSerialize jacksonSerialize = new JacksonSerialize();
        SerializeObject serializeObject = new SerializeObject("java", "Java");
        byte[] bytes = jacksonSerialize.serialize(serializeObject);
        assertNotEquals(bytes.length, 0);
        SerializeObject deserializeObject = jacksonSerialize.deserialize(bytes, SerializeObject.class);
        assertEquals(serializeObject, deserializeObject);
    }
}
