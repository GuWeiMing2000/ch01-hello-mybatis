package com.miaoli;

import com.miaoli.domain.Student;
import com.miaoli.utils.MyBatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MyApp2 {

    public static void main(String[] args) throws IOException {

        //获取SqlSession对象，从SqlSessionFactory中获取SqlSession
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        //【重要】指定要执行的sql语句的标识。  sql映射文件中的namespace + "." + 标签的id值
        String sqlId = "com.miaoli.dao.StudentDao.selectStudents";
        //【重要】执行sql语句，通过sqlId找到语句
        List<Student> studentList = sqlSession.selectList(sqlId);
        //输出结果
        studentList.forEach( stu -> System.out.println(stu));
        //关闭SqlSession对象
        sqlSession.close();

    }

    @Test
    public void testInsert() throws IOException {
        //1.mybatis 主配置文件
        String config = "mybatis.xml";
        //2.读取配置文件
        InputStream in = Resources.getResourceAsStream(config);
        //3.创建 SqlSessionFactory 对象
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //4.获取 SqlSession
        SqlSession session = factory.openSession();
        //5.创建保存数据的对象
        Student student = new Student();
        student.setId(1005);
        student.setName("张丽");
        student.setEmail("zhangli@163.com");
        student.setAge(20);
        //6.执行插入 insert
        int rows = session.insert(
                "com.miaoli.dao.StudentDao.insertStudent",student);
        //7.提交事务
        session.commit();
        System.out.println("增加记录的行数:"+rows);
        //8.关闭 SqlSession
        session.close();

    }
}
