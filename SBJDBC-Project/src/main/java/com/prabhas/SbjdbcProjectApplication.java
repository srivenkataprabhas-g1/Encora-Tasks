package com.prabhas;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.prabhas.dao.StudentDao;
import com.prabhas.model.Student;

@SpringBootApplication
public class SbjdbcProjectApplication implements CommandLineRunner{

	@Autowired
	private StudentDao studentdao;
	public static void main(String[] args) {
		SpringApplication.run(SbjdbcProjectApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter ID:");
		int id=sc.nextInt();
		System.out.println("Enter Name:");
		String name=sc.nextLine();
		System.out.println("Enter City:");
		String city=sc.nextLine();
		Student student=new Student(id,name,city);
		studentdao.update(student);
		sc.close();
	}
}
