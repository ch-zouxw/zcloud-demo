# zcloud-demo
<p align="center">
  <img src='https://img.shields.io/github/license/matevip/matecloud' alt='License'/>
  <img src="https://img.shields.io/github/stars/matevip/matecloud" alt="Stars"/>
  <img src="https://img.shields.io/badge/Spring%20Boot-2.3.7.RELEASE-green" alt="SpringBoot"/>
  <img src="https://img.shields.io/badge/Spring%20Cloud-Hoxton.SR8-blue" alt="SpringCloud"/>
  <img src="https://img.shields.io/badge/Spring%20Cloud%20Alibaba-2.2.3.RELEASE-brightgreen" alt="Spring Cloud Alibaba"/>
</p>

## 如果您觉得有帮助，请点右上角 "Star" 支持一下谢谢

### 功能特点
- 主体框架：采用最新的Spring Cloud Hoxton SR8, Spring Boot 2.3.7.RELEASE, Spring Cloud Alibaba 2.2.3.RELEASE版本进行系统设计；

- 统一注册：支持nacos作为注册中心，实现多配置、分群组、分命名空间、多业务模块的注册和发现功能；

- 统一认证：统一Oauth2认证协议，采用jwt的方式，实现统一认证，并支持自定义grant_type实现手机号码登录，第三方登录正在开发中；

- 业务监控：利用Spring Boot Admin 来监控各个独立Service的运行状态；利用Hystrix Dashboard来实时查看接口的运行状态和调用频率等。

- 内部调用：集成了feign和dubbo两种模式支持内部调用，并且可以实现无缝切换，适合新老程序员，快速熟悉项目；

- 业务熔断：采用Sentinel实现业务熔断处理，避免服务之间出现雪崩;

- 代码生成：基于Mybatis-plus-generator自动生成代码，提升开发效率，生成模式不断优化中，暂不支持前端代码生成；

- 注解式动态数据源切换(读写分离、日志)

- 注解式日志服务

- 钉钉群机器人消息推送
  
- 注解式缓存、锁、限流

### Maven仓库配置
```xml
    <repositories>
        <repository>
            <id>zclod</id>
            <name>zcloud nexus</name>
            <url>http://maven.zoujy.com/repository/zcloud_group/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
        </repository>
    </repositories>

    <pluginRepositories>
        <pluginRepository>
            <id>public</id>
            <name>zcloud nexus</name>
            <url>http://maven.zoujy.com/repository/zcloud_group/</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </pluginRepository>
    </pluginRepositories>
```

### 注解式缓存、锁、限流

如何引入依赖
```xml
<dependency>
    <groupId>com.zxw.zcloud</groupId>
    <artifactId>cache-spring-boot-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

Redis配置YML
```yml
zcloud:
  redis:
    redisson:
      # ip:port，有多个用半角逗号分隔
      address: 127.0.0.1:6379
      # 支持standalone-单机节点，sentinel-哨兵，cluster-集群，masterslave-主从
      type: standalone
      password:
      database: 1
```

代码实现
```java
@RestController
public class TestRedisController {
    
    /**
    * 注解式限流
    * @param paramEntity
    * @return
    * 60秒 根据IP限制最大访问次数2次
    */
    @RedisRateLimit(period = 60, count = 2, limitType = RateLimitType.IP)
    @PostMapping("/limit")
    public Result limit(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        return Result.succeedWith(paramEntity,"");
    }
}

@Service
public class RedisServiceImpl implements RedisService {

    /**
     * 注解式缓存
     * @param entity
     * @return
     * 30秒 失效
     */
    @RedisCache(expire=30)
    @Override
    public String testCache(ParamEntity entity){
        return "Hello Cache：" + entity.toString();
    }

    /**
     * 注解式分布式锁
     * @param entity
     * @return
     * 是否阻塞，有效时间秒
     */
    @RedisLock(block = false, expire = 0L)
    @Override
    public String testLock(ParamEntity entity) {
        return "Hello Lock：" + entity.toString();
    }
}

```

### 钉钉群推送

如何引入依赖
```xml
<dependency>
    <groupId>com.zxw.zcloud</groupId>
    <artifactId>alibaba-dingtalk-boot-starter</artifactId>
    <version>1.0.0</version>
 </dependency>
```

Redis配置YML
```yml
zcloud:
  aliyun:
    dingtalk:
      serverUrl: https://oapi.dingtalk.com/robot/send?access_token=
      accessToken: XXXXXXXXXXXXXXXXXXXXXXX
```

代码实现
```java
@RestController
public class TestDingtalkController {

    @Autowired
    DingtalkService dingtalkService;

    /**
     * 测试钉钉推送
     * @param paramEntity
     * @return
     * 
     * 支持的消息格式
     * DingtalkRequest(Text) 
     * DingtalkMarkdownRequest(Markdown)  
     * DingtalkLinkRequest(Link)
     */
    @ApiOperation("测试钉钉推送")
    @PostMapping("/send")
    public Result testDingtalk(ParamEntity paramEntity){
        log.info("入参：{}", paramEntity);
        DingtalkMarkdownRequest markdownRequest = new  DingtalkMarkdownRequest();
        markdownRequest.setKeyword("服务器通知");
        markdownRequest.setTitle("服务异常");
        markdownRequest.setContent("测试失败");
        return Result.succeedWith(dingtalkService.send(markdownRequest),"");
    }
}


```