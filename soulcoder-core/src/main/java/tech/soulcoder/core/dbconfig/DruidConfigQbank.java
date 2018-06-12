package tech.soulcoder.core.dbconfig;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author yunfeng.lu
 * @date 2018年06月11日16:19:32
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.qbank")
public class DruidConfigQbank {
    public String onoff;
    public String url;
    public String username;
    public String password;
    public int initialSize;
    public int maxActive;
    public int minIdle;
    public long maxWait;
    public long timeBetweenEvictionRunsMillis;
    public long minEvictableIdleTimeMillis;
    public boolean poolPreparedStatements;
    public int maxPoolPreparedStatementPerConnectionSize;
    public String filters;
    public String connectionProperties;

    @Bean(name="druidDataSourceQbank")
    public DataSource druidDataSourceQbank() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        druidDataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        druidDataSource.setConnectionProperties(connectionProperties);
        try {
            druidDataSource.setFilters("stat,slf4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            druidDataSource.setFilters(filters);
            druidDataSource.init();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

}
