# 🪖 Query-Counter

Query-Counter is a library for counting queries for Spring Data JPA application, currently supporting Hibernate only. It aims to help beginners figure out how many queries are generated within a transaction or any specific range. There are several use-cases for test as follows:

* Counting the exact number of queries generated within a transaction or any specific range.
* Understanding lazy loading fetch strategy of ORM framework.
* Detecting N + 1.

[You can report bug or request new features via Issue tab.](https://github.com/xlffm3/query-counter/issues)

<br>

## Requirements

* JDK 1.8 or higher.

<br>

## Installation

> Sorry for the inconvenience. There is a dependency issue and I'm currently working on it.

### Maven

> pom.xml

```xml
<dependency>
  <groupId>io.github.xlffm3</groupId>
  <artifactId>query-counter</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Gradle

> build.gradle

```groovy
implementation 'io.github.xlffm3:query-counter:1.0.0'
```

<br>

## Features

> application.properties

```properties
query-counter.enabled=true
query-counter.count-level=read_only
```

You can choose whether to enable Query-Counter or not. false is default. You can also specify count level. Query-Counter supports three count-level as follows:

* READ_ONLY (default)
* WRITE_ONLY
* ALL

> QueryTest.java

```java
@SpringBootTest
public class QueryTest {

    @Autowired
    private QueryCounter queryCounter;
    
    @Autowired
    private HibernateQueryCounter hibernateQueryCounter;
    
    @Autowired
    private UserService userService;
    
    @Test
    void test() {
        queryCounter.startCountingQuery();
        userService.findAllWithNPlusOne();
        Long queryCounts = queryCounter.getQueryCounts();
        
        assertThat(queryCounts).isEqualTo(15L);
    }
}
```

The only implementation of QueryCounter interface Query-Counter provides is HibernateQueryCounter yet. HibernateQueryCounter is registered as a Spring Bean.

<br>

---
