package tech.soulcoder.mytest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.soulcoder.RedisOperation;
import tech.soulcoder.service.TestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BaseTest {
    @Autowired
    public TestService testService;

    @Autowired
    public RedisOperation redisOperation;








}
