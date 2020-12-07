# `JsonDeserialize`

- 背景

> 使用 ` ObjectMapper ` 在 `Json` 反序列化时，对象属性类型为 `Boolean` 传入的值为 `"1"` 字符串，`ObjectMapper`反序列化会报错
>

- 处理方式

> 本文介绍通过 `OptimizedBooleanDeserializer` 类，解决入参 为 字符串 `"1"` 时重新返回 `true` 或者 `false` 达到正确反序列化

- 参考代码

```java
public class PersonDeserialize {
    public long id = 0;
    public String name = null;
    @JsonDeserialize(using = OptimizedBooleanDeserializer.class)
    public boolean enabled = false;
}
``` 

```java
public class OptimizedBooleanDeserializer extends JsonDeserializer<Boolean> {
    @Override
    public Boolean deserialize(JsonParser jsonParser,
                               DeserializationContext deserializationContext) throws IOException {
        String text = jsonParser.getText();
        if ("0".equals(text) || "false".equals(text)) {
            return false;
        }
        return true;
    }
}
```

- 参考文献

`[ 1 ]` [`Jackson Annotations`](http://tutorials.jenkov.com/java-json/jackson-annotations.html)