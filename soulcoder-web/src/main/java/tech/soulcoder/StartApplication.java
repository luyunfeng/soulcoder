package tech.soulcoder;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yunfeng.lu
 * @date 2018年05月18日17:31:56
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@RestController
@ComponentScan("tech.soulcoder.**")
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy=true)
@MapperScan("tech.soulcoder.dao")
public class StartApplication {
    private static final Logger logger = LoggerFactory.getLogger(StartApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @GetMapping("/health")
    public String health(){
        logger.info("健康检查");
        return "success";
    }
}
