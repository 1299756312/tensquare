package com.tensquare.search.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

@Document(indexName = "tensquare",type = "article")
@Data
public class Article implements Serializable {
    @Id
    private String id;  //文章id
    @Field(index= true ,type = FieldType.keyword ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
    private String title;//标题
    @Field(index= true ,type = FieldType.keyword ,analyzer="ik_max_word",searchAnalyzer="ik_max_word")
     private String content;//文章正文
     private String state;//审核状态

}
