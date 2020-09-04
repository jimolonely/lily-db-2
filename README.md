# never give up

实现druid这个数据库时不懂的知识点

# jackson库

## @JsonTypeInfo 和 @JsonSybTypes注解

这是jackson里的注解。

## @JsonCreator

当json在反序列化时，默认选择类的无参构造函数创建类对象，当没有无参构造函数时会报错，@JsonCreator作用就是指定反序列化时用的无参构造函数。构造方法的参数前面需要加上@JsonProperty,否则会报错。
```java
@JsonCreator
public Person(@JsonProperty("id") String id) {
    this.id = id;
}
```
json反序列化时调用此构造函数.

## @JsonProperty


# guava库

## Preconditions.checkNotNull

# joda-time库

joda time这个库的时间操作。

