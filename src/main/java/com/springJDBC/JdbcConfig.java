package com.springJDBC;

import com.springJDBC.dao.StudentDao;
import com.springJDBC.dao.StudentDaoImplementation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages="com.springDao.studentDao")
public class JdbcConfig {

    @Bean("datasource")
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springjdbc");
        dataSource.setUsername("root");
        dataSource.setPassword("Hitmanbau1*@sql");

        return dataSource;
    }

    @Bean("jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }

    @Bean("studentDaoImpl")
    public StudentDao getStudentDao(){
        StudentDaoImplementation studentDao = new StudentDaoImplementation();
        studentDao.setJdbcTemplate(getJdbcTemplate());
        return studentDao;
    }

}
