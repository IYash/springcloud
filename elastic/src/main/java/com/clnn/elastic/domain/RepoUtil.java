package com.clnn.elastic.domain;

import com.clnn.elastic.entity.Item;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Component
public class RepoUtil {

    @Autowired
    ItemRepository itemRepository;

    public void save(Item item){
        itemRepository.save(item);
    }

    public void saveList(List<Item> items){
        itemRepository.saveAll(items);
    }
    public void findAll(){
        //查找所有
        Iterable<Item> item = itemRepository.findAll();
        Iterator<Item> it = item.iterator();
        while(it.hasNext()){
            System.out.println(it.next());
        }
        //分页查找
        Page<Item> page = itemRepository.findAll(PageRequest.of(1,5));
        for(Item tmp:page){
            System.out.println(tmp);
        }
        //排序
        Iterable<Item> iterable = itemRepository.findAll(Sort.by("price").descending());
        Iterator<Item> tmp = iterable.iterator();
        while(tmp.hasNext()){
            System.out.println(tmp.next());
        }

    }
    public void search(String key,String value){
        //构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery(key,value));
        //搜索获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());
        //总条数
        long total = items.getTotalElements();
        if(null != items) {
            items.stream().forEach(item -> System.out.println(item));
        }

    }


}
