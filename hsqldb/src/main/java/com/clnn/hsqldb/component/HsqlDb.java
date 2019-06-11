package com.clnn.hsqldb.component;

import com.clnn.hsqldb.util.SqlScriptRunner;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HsqlDb implements InitializingBean {

    @Value("${innersql.url}")
    private String url;
    @Value("${innersql.username}")
    private String username;
    @Value("${innersql.password}")
    private String password;
    @Value("${innersql.sourceFile}")
    private String sourceFile;
    @Value("${innersql.driver}")
    private String driver;


    @Override
    public void afterPropertiesSet() throws Exception {
        createDatabase();
    }
    private void createDatabase(){
        SqlScriptRunner runner = null;
        try {
            runner = new SqlScriptRunner(
                    sourceFile,driver,url,username,password);
            runner.executeScript();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}