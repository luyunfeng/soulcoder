package tech.soulcoder.mytest;


import com.alibaba.fastjson.JSON;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import tech.soulcoder.SqlHelper;

import java.util.HashMap;
import java.util.Map;

public class MyTest extends BaseTest {

    @Autowired
    private SqlHelper sqlHelper;

    @Test
    public void test(){
        System.out.println(testService.testDB());
    }

    @Test
    public  void redisTest(){
        redisOperation.setValue("haha","lala");
        System.out.println(redisOperation.getValue("haha"));

    }

    /**
     * sqlHelp 测试
     */
    @Test
    public void sqlSessionFactoryTest(){
        Map<String,String> map=new HashMap<>();
        sqlHelper.exec("select id,pwd from soul_user",row ->{
            map.put(row.getString("id"),row.getString("pwd"));
        });
        System.out.println(JSON.toJSONString(map));
    }

}

