package com.liu.data_source;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


import java.io.IOException;
import java.io.Reader;
import java.util.List;
import java.util.Map;

/**
 * @Author: Gavin
 * @Date: 2021/12/3 9:55
 */
public class DataSource {



    public static void main(String[] args) throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Subject mapper = sqlSession.getMapper(Subject.class);
            List<Map<String, Object>> subjects = mapper.getSubjects();
        }
    }


}
