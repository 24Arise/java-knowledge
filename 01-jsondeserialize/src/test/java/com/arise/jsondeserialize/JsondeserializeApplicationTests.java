package com.arise.jsondeserialize;

import com.arise.jsondeserialize.enitty.Person;
import com.arise.jsondeserialize.service.IJsonDeserializeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JsondeserializeApplicationTests {

    @Autowired
    private IJsonDeserializeService jsonDeserializeService;

    @Test
    void contextLoads() {
        System.out.println(" JsonDeserialize 学习");
    }

    @Test
    void jsonTest() {
        // 反序列化 Json
        String json = "{\"id\": 1024, \"name\": \"四大凶兽 穷奇，饕餮、梼杌、混沌\", \"birthday\": \"2020-12-06 21:00:00\", \"enabled\": \"1\"}";
        Person person = jsonDeserializeService.json2Object(json);
        System.out.println(person.getEnabled());
    }

}
