package com.org.arise.JsonDeserialize.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.arise.JsonDeserialize.entity.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/**
 * description
 *
 * @author 24Arise 2020/12/06 19:10
 */

@SpringBootTest
class JsonDeserializeTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void jsonTest() throws IOException {
        System.out.println(1204);
        // 反序列化 Json
        String json = "{\"id\": 1024, \"name\": \"四大凶兽 穷奇，饕餮、梼杌、混沌\", \"birthday\": \"2020-12-06 21:00\", \"enabled\": \"1\"}";
        Person person = objectMapper.readValue(json, Person.class);
        System.out.println(person.getEnabled());
    }


}
