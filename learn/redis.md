# Redis使用指北

教程->
<https://www.runoob.com/redis/redis-tutorial.html>

## 下载

<https://www.runoob.com/redis/redis-install.html>

## 命令

### Key

1. `DEL key`  
   该命令用于在 key 存在时删除 key。
   不存在的键会被忽略。

2. `EXISTS key`  
   检查给定 key 是否存在。

3. `EXPIRE key seconds`  
   为给定 key 设置过期时间，以秒计。

4. `PEXPIRE key milliseconds`  
   设置 key 的过期时间以毫秒计。

5. `KEYS pattern`  
   查找所有符合给定模式(pattern)的 key。

    ```shell
    查找以 runoob 为开头的 key：
    redis 127.0.0.1:6379> KEYS runoob*
    1) "runoob3"
    2) "runoob1"
    3) "runoob2"
    ```

6. `MOVE key db`  
   将当前数据库的 key 移动到给定的数据库 db 当中。

   ```shell
   # key 存在于当前数据库

    redis> SELECT 0                             # redis默认使用数据库 0，为了清晰起见，这里再显式指定一次。
    OK

    redis> SET song "secret base - Zone"
    OK

    redis> MOVE song 1                          # 将 song 移动到数据库 1
    (integer) 1

    redis> EXISTS song                          # song 已经被移走
    (integer) 0

    redis> SELECT 1                             # 使用数据库 1
    OK

    redis:1> EXISTS song                        # 证实 song 被移到了数据库 1 (注意命令提示符变成了"redis:1"，表明正在使用数据库 1)
    (integer) 1


    # 当 key 不存在的时候

    redis:1> EXISTS fake_key
    (integer) 0

    redis:1> MOVE fake_key 0                    # 试图从数据库 1 移动一个不存在的 key 到数据库 0，失败
    (integer) 0

    redis:1> select 0                           # 使用数据库0
    OK

    redis> EXISTS fake_key                      # 证实 fake_key 不存在
    (integer) 0


    # 当源数据库和目标数据库有相同的 key 时

    redis> SELECT 0                             # 使用数据库0
    OK
    redis> SET favorite_fruit "banana"
    OK

    redis> SELECT 1                             # 使用数据库1
    OK
    redis:1> SET favorite_fruit "apple"
    OK

    redis:1> SELECT 0                           # 使用数据库0，并试图将 favorite_fruit 移动到数据库 1
    OK

    redis> MOVE favorite_fruit 1                # 因为两个数据库有相同的 key，MOVE 失败
    (integer) 0

    redis> GET favorite_fruit                   # 数据库 0 的 favorite_fruit 没变
    "banana"

    redis> SELECT 1
    OK

    redis:1> GET favorite_fruit                 # 数据库 1 的 favorite_fruit 也是
    "apple"
    ```

7. `PERSIST key`  
   移除 key 的过期时间，key 将持久保持。

8. `PTTL key`  
    以毫秒为单位返回 key 的剩余的过期时间。

9. `TTL key`  
    以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
    -2: key 不存在
    -1: key 存在但没有设置剩余生存时间

10. `RANDOMKEY`  
    从当前数据库中随机返回一个 key。

11. `RENAME key newkey`  
    修改 key 的名称。

12. `RENAMENX key newkey`  
    仅当 newkey 不存在时，将 key 改名为 newkey。

    ```shell
    # key 存在且 newkey 不存在

    redis> SET message "hello world"
    OK

    redis> RENAME message greeting
    OK

    redis> EXISTS message               # message 不复存在
    (integer) 0

    redis> EXISTS greeting              # greeting 取而代之
    (integer) 1


    # 当 key 不存在时，返回错误

    redis> RENAME fake_key never_exists
    (error) ERR no such key


    # newkey 已存在时， RENAME 会覆盖旧 newkey

    redis> SET pc "lenovo"
    OK

    redis> SET personal_computer "dell"
    OK

    redis> RENAME pc personal_computer
    OK

    redis> GET pc
    (nil)

    redis> GET personal_computer      # 原来的值 dell 被覆盖了
    "lenovo"
    ```

13. `SCAN cursor [MATCH pattern] [COUNT count]`  
    迭代数据库中的数据库键。

14. `TYPE key`  
    返回 key 所储存的值的类型。

### String 命令

1. `SET key value`  
   设置指定 key 的值。

2. `GET key`  
   获取指定 key 的值。

3. `GETRANGE key start end`  
   返回 key 中字符串值的子字符。

4. `GETSET key value`  
   将给定 key 的值设为 value，并返回 key 的旧值(old value)。
   Tip: 包含始末；索引从 0 开始；-1 表示最后一个字符。

5. `GETBIT key offset`  
   对 key 所储存的字符串值，获取指定偏移量上的位(bit)。

6. `MGET key1 [key2..]`  
   获取所有(一个或多个)给定 key 的值。

   ```shell
    redis 127.0.0.1:6379> SET key1 "hello"
    OK
    redis 127.0.0.1:6379> SET key2 "world"
    OK
    redis 127.0.0.1:6379> MGET key1 key2 someOtherKey
    1) "Hello"
    2) "World"
    3) (nil)
    ```

7. `SETBIT key offset value`  
   对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。

8. `SETEX key seconds value`  
   将值 value 关联到 key，并将 key 的过期时间设为 seconds (以秒为单位)。

9. `SETNX key value`  
   只有在 key 不存在时设置 key 的值。

10. `SETRANGE key offset value`  
    用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。

11. `STRLEN key`  
    返回 key 所储存的字符串值的长度。

12. `MSET key value [key value ...]`  
    同时设置一个或多个 key-value 对。

    ```shell
    redis 127.0.0.1:6379> MSET key1 "Hello" key2 "World"
    OK
    redis 127.0.0.1:6379> GET key1
    "Hello"
    redis 127.0.0.1:6379> GET key2
    1) "World"
    ```

13. `MSETNX key value [key value ...]`  
    同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。

14. `PSETEX key milliseconds value`  
    这个命令和 SETEX 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 SETEX 命令那样，以秒为单位。

15. `INCR key`  
    将 key 中储存的数字值增一。
    如果 key 不存在，那么 key 的值会先被初始化为 0 ，然后再执行 INCR 操作。
    如果值包含错误的类型，或字符串类型的值不能表示为数字，那么返回一个错误。
    本操作的值限制在 64 位(bit)有符号数字表示之内。

16. `INCRBY key increment`  
    将 key 所储存的值加上给定的增量值（increment）。

17. `INCRBYFLOAT key increment`  
    将 key 所储存的值加上给定的浮点增量值（increment）。

18. `DECR key`  
    将 key 中储存的数字值减一。

19. `DECRBY key decrement`  
    key 所储存的值减去给定的减量值（decrement）。

20. `APPEND key value`  
    如果 key 已经存在并且是一个字符串，APPEND 命令将指定的 value 追加到该 key 原来值（value）的末尾。

### Hash, List, Set

Redis中是可以存数据结构的，如 Hash, List, Set 等。它们的操作和  c++中的 map, vector, set 等类似。

以Hash为例：

Redis hash 是一个 string 类型的 field（字段） 和 value（值） 的映射表，hash 特别适合用于存储对象。

```shell
127.0.0.1:6379>  HMSET runoobkey name "redis tutorial" description "redis basic commands for caching" likes 20 visitors 23000
OK
# HGTEALL 命令用于获取在哈希表中指定 key 的所有字段和值
127.0.0.1:6379>  HGETALL runoobkey
1) "name"
2) "redis tutorial"
3) "description"
4) "redis basic commands for caching"
5) "likes"
6) "20"
7) "visitors"
8) "23000"
```

### Sorted Set

Redis 有序集合和集合一样也是 string 类型元素的集合,且不允许重复的成员。

不同的是每个元素都会关联一个 double 类型的分数。redis 正是通过分数来为集合中的成员进行从小到大的排序。

有序集合的成员是唯一的,但分数(score)却可以重复。

```shell
redis 127.0.0.1:6379> ZADD runoobkey 1 redis
(integer) 1
redis 127.0.0.1:6379> ZADD runoobkey 2 mongodb
(integer) 1
redis 127.0.0.1:6379> ZADD runoobkey 3 mysql
(integer) 1
redis 127.0.0.1:6379> ZADD runoobkey 3 mysql
(integer) 0
redis 127.0.0.1:6379> ZADD runoobkey 4 mysql
(integer) 0
redis 127.0.0.1:6379> ZRANGE runoobkey 0 10 WITHSCORES

1) "redis"
2) "1"
3) "mongodb"
4) "2"
5) "mysql"
6) "4"
```

```shell
ZCOUNT key min max
计算在有序集合中指定区间分数的成员数

ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight [weight ...]] [AGGREGATE SUM|MIN|MAX]
计算给定的一个或多个有序集的交集，其中给定 key 的数量必须以 numkeys 参数指定，并将该交集(结果集)储存到 destination 。
默认情况下，结果集中某个成员的分数值是所有给定集下该成员分数值之和。

ZRANGEBYLEX key min max [LIMIT offset count]
redis 127.0.0.1:6379> ZADD myzset 0 a 0 b 0 c 0 d 0 e 0 f 0 g
(integer) 7
redis 127.0.0.1:6379> ZRANGEBYLEX myzset - [c
1) "a"
2) "b"
3) "c"
redis 127.0.0.1:6379> ZRANGEBYLEX myzset - (c
1) "a"
2) "b"
redis 127.0.0.1:6379> ZRANGEBYLEX myzset [aaa (g
1) "b"
2) "c"
3) "d"
4) "e"
5) "f"
redis> 

```

### HyperLogLog

比如数据集 {1, 3, 5, 7, 5, 7, 8}， 那么这个数据集的基数集为 {1, 3, 5 ,7, 8}, 基数(不重复元素)为5。 基数估计就是在误差可接受的范围内，快速计算基数。

## Springboot

教程->
<https://docs.spring.io/spring-data/redis/reference/redis.html>

### 初步使用

1. 创建项目

Make sure to include the Spring Web and Spring Data Redis dependencies in your project.

If you’re using Spring Initializr, select the dependencies as follows:

Web -> Spring Web
SQL -> Spring Data Redis

2. 配置

Open the application.properties or application.yml file and add the following properties:

```properties
# Redis Server Configuration
spring.redis.host=127.0.0.1     # Redis server host (default: localhost)
spring.redis.port=6379          # Redis server port (default: 6379)
```

3. Create a Redis Configuration Bean

Create a Java class that contains the configuration and annotate it with @Configuration:

```java

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName("localhost");
        jedisConFactory.setPort(6379);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
```

This code sets up a JedisConnectionFactory and a RedisTemplate, which allows you to interact with Redis.

4. Using Redis in Your Spring Boot Application

```java
@Autowired
private RedisTemplate<String, Object> redisTemplate;

public void saveData(String key, Object data) {
    redisTemplate.opsForValue().set(key, data);
}
To retrieve data from Redis:

public Object getData(String key) {
    return redisTemplate.opsForValue().get(key);
}
```

@Resourse

```java
public class Example {

  // inject the actual operations
  @Autowired
  private RedisOperations<String, String> operations;

  // inject the template as ListOperations
  @Resource(name="redisTemplate")
  private ListOperations<String, String> listOps;

  public void addLink(String userId, URL url) {
    listOps.leftPush(userId, url.toExternalForm());
  }
}
```

### Repository

@Entity: This annotation is used to mark the class as a JPA entity.
@Document: This annotation is used to mark the class as a document in a MongoDB database.

Entity:

```java
@RedisHash("people")
public class Person {

  @Id String id;
  String firstname;
  String lastname;
  Address address;
}
```

Note that it has a @RedisHash annotation on its type and a property named id that is annotated with org.springframework.data.annotation.Id. Those two items are responsible for creating the actual key used to persist the hash.  

To now actually have a component responsible for storage and retrieval, we need to define a repository interface, as shown in the following example:  

```java
public interface PersonRepository extends CrudRepository<Person, String> {

}
```

As our repository extends CrudRepository, it provides basic CRUD and finder operations. The thing we need in between to glue things together is the corresponding Spring configuration, shown in the following example:

```java
@Configuration
@EnableRedisRepositories
public class ApplicationConfig {

  @Bean
  public RedisConnectionFactory connectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

    RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
    template.setConnectionFactory(redisConnectionFactory);
    return template;
  }
}
```

Given the preceding setup, we can inject PersonRepository into our components, as shown in the following example:

```java
@Autowired PersonRepository repo;

public void basicCrudOperations() {

  Person rand = new Person("rand", "al'thor");
  rand.setAddress(new Address("emond's field", "andor"));

  repo.save(rand);

  repo.findOne(rand.getId());

  repo.count();

  repo.delete(rand);
}
```

1. Generates a new id if the current value is null or reuses an already set id value and stores properties of type Person inside the Redis Hash with a key that has a pattern of keyspace:id — in this case, it might be people:5d67b7e1-8640-2024-beeb-c666fab4c0e5.
2. Uses the provided id to retrieve the object stored at keyspace:id.
3. Counts the total number of entities available within the keyspace, people, defined by @RedisHash on Person.
4. Removes the key for the given object from Redis.

### Partial Update

In some cases, you need not load and rewrite the entire entity just to set a new value within it. A session timestamp for the last active time might be such a scenario where you want to alter one property. PartialUpdate lets you define set and delete actions on existing objects while taking care of updating potential expiration times of both the entity itself and index structures. The following example shows a partial update:

```java
PartialUpdate<Person> update = new PartialUpdate<Person>("e2c7dcee", Person.class)
  .set("firstname", "mat")
  .set("address.city", "emond's field")
  .del("age");

template.update(update);

update = new PartialUpdate<Person>("e2c7dcee", Person.class)
  .set("address", new Address("caemlyn", "andor"))
  .set("attributes", singletonMap("eye-color", "grey"));

template.update(update);

update = new PartialUpdate<Person>("e2c7dcee", Person.class)
  .refreshTtl(true);
  .set("expiration", 1000);

template.update(update)
```

### Object Mapping

