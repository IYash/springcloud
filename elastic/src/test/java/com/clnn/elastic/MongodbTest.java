package com.clnn.elastic;

import com.clnn.elastic.entity.UserEntity;

import com.mongodb.client.result.UpdateResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
public class MongodbTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testSave(){
        UserEntity user = createUser("abe",2,"male");
        mongoTemplate.save(user);
    }
    private UserEntity createUser(String userName,int age,String gender){
        return new UserEntity(userName,age,gender);
    }
    private Query createQuery(String userName){
        Query query = new Query(Criteria.where("userName").is(userName));
        return query;
    }
    private Query createRegexQuery(String userName){
        Query query = new Query(Criteria.where("userName").regex(userName));
        return query;
    }
    @Test
    public void testRemove(){
        String userName="abc";
        Query query = createQuery(userName);
        mongoTemplate.remove(query,UserEntity.class);
    }
    @Test
    public void update(){
        String userName = "abc";
        Query query = createQuery(userName);
        Update update = new Update().set("userName","bcd");
        UpdateResult result = mongoTemplate.updateFirst(query,update,UserEntity.class);
        System.out.println(result);
    }
    @Test
    public void query(){
        String userName="abc";
        Query query = createQuery(userName);
        UserEntity user = mongoTemplate.findOne(query,UserEntity.class);
        System.out.println(user);
    }
    @Test
    public void pageQuery(){
        String userName="ab";
        Query query = createRegexQuery(userName);
        query.skip(1).limit(2);
        List<UserEntity> entities = mongoTemplate.find(query,UserEntity.class);
        entities.stream().forEach(e-> System.out.println(e));
    }
}
