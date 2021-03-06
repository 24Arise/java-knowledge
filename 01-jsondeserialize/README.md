# `JsonDeserialize`

- 背景

> 使用 ` ObjectMapper ` 在 `Json` 反序列化时，对象属性类型为 `Boolean`，入参 `"1"` 字符串，`ObjectMapper`反序列化会报错

- 处理方式

> 本文介绍通过 `OptimizedBooleanDeserializer` 类，解决入参 为 字符串 `"1"` 时重新返回 `true` 或者 `false` 达到正确反序列化

- 示例代码

```json5
{
  "id": 1024,
  "name": "四大凶兽 穷奇，饕餮、梼杌、混沌",
  "birthday": "2020-12-06 21:00",
  "enabled": "1"
}
```

```java
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
``` 

```java
public class OptimizedBooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        return !"0".equals(text) && !"false".equals(text);
    }
}
```

- 问题

> 目前 工程 中 `ObjectMapper` 类 注入不进来，还未找到原因，因此使用 `ObjectMapper objectMapper = new ObjectMapper()` 方式

- 参考文献

`[ 1 ]` [`Jackson Annotations`](http://tutorials.jenkov.com/java-json/jackson-annotations.html)