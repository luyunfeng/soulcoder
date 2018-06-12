package tech.soulcoder.core.dbconfig;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
/**
 * @author yunfeng.lu
 * @create 2017/12/28.
 */
@Configuration
public class DruidServletConfig {
    @Bean
    @Order
    public ServletRegistrationBean statViewServlet() {
        StatViewServlet servlet = new StatViewServlet();
        ServletRegistrationBean bean = new ServletRegistrationBean(servlet, "/druid/*");
        bean.addInitParameter("loginUsername", "lucode");
        bean.addInitParameter("loginPassword", "lucode123");
        return bean;
    }
}
