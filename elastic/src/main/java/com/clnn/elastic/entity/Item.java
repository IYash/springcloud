package com.clnn.elastic.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "ditem",type="docs",shards = 1,replicas =0 )
public class Item {
    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;
    @Field(type=FieldType.Keyword)
    private String category;
    @Field(type=FieldType.Keyword)
    private String brand;
    @Field(type=FieldType.Double)
    private Double price;
    @Field(index=false,type=FieldType.Keyword)
    private String images;

    public Item() {
    }

    public Item(Long id, String title, String category, String brand, Double price, String images) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.brand = brand;
        this.price = price;
        this.images = images;
    }
}
