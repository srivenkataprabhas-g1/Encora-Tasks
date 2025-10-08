package com.prabhas.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.prabhas.model.Student;

@Repository
public class StudentDao {
	@Autowired
	private JdbcTemplate jdbctemplate;
	public void update(Student student) {
		try {
			String sql="INSERT INTO emp(id,password,first_name,last_name,phone_number) values(?,?,?)";
			int c=jdbctemplate.update(sql,student.getId(),student.getName(),student.getCity());
			if(c>0) {
				System.out.println("Insertion Successful");
			}else {
				System.out.println("Insertion failed");
			}
		}catch(Throwable e) {
			System.out.println(e.getLocalizedMessage());
		}
	}
}
