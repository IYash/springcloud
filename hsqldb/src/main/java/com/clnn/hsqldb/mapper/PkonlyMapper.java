package com.clnn.hsqldb.mapper;

import com.clnn.hsqldb.entity.Pkonly;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PkonlyMapper {

   @Insert("insert into PKonly (id,seq_num) values(#{id},#{seqNum})")
    int insert(Pkonly pkonly);
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "seqNum", column = "seq_num")
    })
    @Select("select * from PKonly")
    List<Pkonly> selectAll();
}
