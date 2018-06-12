package tech.soulcoder.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.soulcoder.RedisOperation;
import tech.soulcoder.dao.generator.SoulUserMapper;
import tech.soulcoder.log.LogAuto;
import tech.soulcoder.service.TestService;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {

    private final SoulUserMapper soulUserMapper;

    private final RedisOperation redisOperation;

    @Autowired
    public TestServiceImpl(RedisOperation redisOperation, SoulUserMapper soulUserMapper) {
        this.redisOperation = redisOperation;
        this.soulUserMapper = soulUserMapper;
    }

    @Override
    @LogAuto
    public Map testDB() {
        redisOperation.setValue("haha","lalal");
        System.out.println(redisOperation.getValue("haha"));

        Map<String,Object> res=new HashMap<>();
        res.put("res",JSON.toJSONString(soulUserMapper.selectByPrimaryKey("1")));
        return res;

    }
}
