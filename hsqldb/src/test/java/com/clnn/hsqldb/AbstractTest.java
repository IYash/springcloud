package com.clnn.hsqldb;

import com.clnn.hsqldb.mapper.PkonlyMapper;
import com.clnn.hsqldb.util.TestUtilities;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = HsqldbApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractTest {
    private static final String JDBC_URL = "jdbc:hsqldb:mem:aname";
    private static final String JDBC_DRIVER = "org.hsqldb.jdbcDriver";

    protected SqlSessionFactory sqlSessionFactory;
    @Before
    public void testConnection() throws Exception{
        TestUtilities.createDatabase();
        UnpooledDataSource ds = new UnpooledDataSource(JDBC_DRIVER, JDBC_URL, "sa", "");
        Environment environment = new Environment("test", new JdbcTransactionFactory(), ds);
        Configuration config = new Configuration(environment);
        config.addMapper(PkonlyMapper.class);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(config);
    }
}
