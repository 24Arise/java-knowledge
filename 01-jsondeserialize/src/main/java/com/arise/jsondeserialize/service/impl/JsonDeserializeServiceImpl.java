package com.arise.jsondeserialize.service.impl;

import com.arise.jsondeserialize.enitty.Person;
import com.arise.jsondeserialize.service.IJsonDeserializeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Json 反序列化
 * </p>
 *
 * @author 24arise 2021/02/01 17:58
 */
@Service
public class JsonDeserializeServiceImpl implements IJsonDeserializeService {

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public Person json2Object(String jsonContext) {
		Person person = new Person();
		try {
			person = objectMapper.readValue(jsonContext, Person.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return person;
	}

}
