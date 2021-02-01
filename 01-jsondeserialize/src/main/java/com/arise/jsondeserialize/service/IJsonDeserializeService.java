package com.arise.jsondeserialize.service;

import com.arise.jsondeserialize.enitty.Person;

/**
 * <p>
 * Json 反序列化 接口
 * </p>
 *
 * @author 24arise 2021/02/01 18:04
 */
public interface IJsonDeserializeService {

    /**
     * <p>
     * Json 转 Object
     * </p>
     *
     * @param jsonContext JSON字符串
     * @return com.arise.jsondeserialize.enitty.Person
     * @author Tagin 2021-02-01 18:10
     */
    Person json2Object(String jsonContext);

}