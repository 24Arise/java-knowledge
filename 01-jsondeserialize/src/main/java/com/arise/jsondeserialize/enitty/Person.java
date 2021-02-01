package com.arise.jsondeserialize.enitty;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.org.arise.JsonDeserialize.service.OptimizedBooleanDeserializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * Person 实体类
 * </p>
 *
 * @author 24Arise 2020/12/06 15:23
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Person {

	@Id
	@GeneratedValue
	private Long id;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date birthday;
	@JsonDeserialize(using = OptimizedBooleanDeserializer.class)
	private Boolean enabled;

}
