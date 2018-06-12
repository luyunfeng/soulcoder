package tech.soulcoder.core.dbconfig;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
/**
 * @author yunfeng.lu
 * @create 2017/12/28.
 */
@MapperScan(basePackages = "tech.soulcoder.dao",
        sqlSessionFactoryRef = "sqlSessionFactorySoul",
        sqlSessionTemplateRef = "sqlSessionTemplateSoul")
@Configuration
public class MyBatisConfigSoul {
///    如果不集成  德鲁伊数据库连接池，需要使用普通的数据源，也在这里配置
//    @Autowired
//    @Qualifier("Soul")
//    private DataSource dataSourceSoul;

    /**
     * 德鲁伊数据源
     */
    private final DataSource druidDataSourceSoul;

    private final PageInterceptor pageHelper;

    @Autowired
    @Qualifier("druidDataSourceQbank")
    private DataSource druidDataSourceQbank;

    @Autowired
    public MyBatisConfigSoul(@Qualifier("druidDataSourceSoul") DataSource druidDataSourceSoul, PageInterceptor pageHelper) {
        this.druidDataSourceSoul = druidDataSourceSoul;
        this.pageHelper = pageHelper;
    }

    @Bean("sqlSessionFactorySoul")
    public SqlSessionFactory sqlSessionFactorySoul() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSourceSoul);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:mapper/**/*.xml"));

        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * Qbank 的 sqlSessionFactory
     * @return
     * @throws Exception
     */
    @Bean("sqlSessionFactoryQbank")
    public SqlSessionFactory sqlSessionFactoryQbank() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(druidDataSourceQbank);
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(
                resolver.getResources("classpath*:mapper/**/*.xml"));

        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactory = sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
        configuration.setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    @Bean("soulTransaction")
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(druidDataSourceSoul);
    }
    @Bean
    public SqlSessionTemplate sqlSessionTemplateSoul() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactorySoul());
    }
}
