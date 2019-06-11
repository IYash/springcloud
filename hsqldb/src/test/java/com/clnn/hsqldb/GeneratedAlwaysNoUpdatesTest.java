/**
 *    Copyright 2006-2016 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.clnn.hsqldb;

import com.clnn.hsqldb.entity.Pkonly;
import com.clnn.hsqldb.mapper.PkonlyMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.assertEquals;


public class GeneratedAlwaysNoUpdatesTest extends AbstractTest {

    @Test
    public void testInsert() {
        SqlSession sqlSession = sqlSessionFactory.openSession();

        try {
            PkonlyMapper mapper = sqlSession.getMapper(PkonlyMapper.class);
            
            Pkonly pkTest = new Pkonly();
            pkTest.setId(1);
            pkTest.setSeqNum(2);
            int rows = mapper.insert(pkTest);
            assertEquals(1, rows);
            
            List<Pkonly> returnedRecords = mapper.selectAll();

            assertEquals(1, returnedRecords.size());

            Pkonly returnedRecord = returnedRecords.get(0);
            assertEquals(1, returnedRecord.getId());
            assertEquals(2,returnedRecord.getSeqNum());
        } finally {
            sqlSession.close();
        }
    }
}
