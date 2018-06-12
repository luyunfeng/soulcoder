package tech.soulcoder.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tech.soulcoder.service.TestService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyTest {

    @Autowired
    private TestService testService;

    @Test
    public void test(){
        System.out.println(testService.testDB());

    }

}
