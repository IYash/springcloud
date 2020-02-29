package com.clnn.elastic;

import com.clnn.elastic.domain.RepoUtil;
import com.clnn.elastic.entity.Item;
import com.clnn.elastic.service.EsService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticApplication.class)
public class ElasticTest {

    @Autowired
    EsService esService;

    @Autowired
    RepoUtil repoUtil;
    @Test
    public void createIndex() {
        esService.createIndex(Item.class);
        //esService.createIndexByName("demotem");
    }

    @Test
    public void deleteIndex(){
        esService.deleteIndexByName("ditem");
    }

    //修改跟新增是同一个接口
    @Test
    public void addItem(){
        Item item = new Item();
        item.setId(1L);
        item.setTitle("小米手机1");
        item.setCategory("手机");
        item.setBrand("小米");
        item.setPrice(3499.00);
        item.setImages("http://image.baidu.com/13123.jpg");
        repoUtil.save(item);
    }
    @Test public  void addItemList(){
        List<Item> lst = Lists.newArrayList();
        Item item1 = new Item(2L,"小米手机2","手机","小米",3599.00,"http://image.baidu.com/13124.jpg");
        Item item2 = new Item(3L,"华为手机1","手机","华为",3699.00,"http://image.baidu.com/13125.jpg");
        lst.add(item1);
        lst.add(item2);
        repoUtil.saveList(lst);
    }
    @Test public void testSearch(){
        String key ="title";
        String value = "小米";
        repoUtil.search(key,value);
    }


}
