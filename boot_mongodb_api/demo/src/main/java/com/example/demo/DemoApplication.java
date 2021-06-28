package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(
			StudentRepository repository,
			MongoTemplate mongoTemplate
	){
		return args -> {
			address adress = new address( "colombia",
					"tulua",
					"057000");

			String email = "mac2394q@gmail.com";
			Student student = new Student(
					"miguel",
					"claros",
					"mac2394q@gmail.com",
					Gender.MALE,
					adress,
					List.of("senior enginer"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			Query query = new Query();
			query.addCriteria( Criteria.where( "email" ).is( email )  );

			List< Student > students = mongoTemplate.find( query, Student.class );
			if(  students.size() > 1 ){
				throw new IllegalStateException(" found many students with email "+email);
			}

			if( students.isEmpty() ){
				repository.insert( student );
			}
	

		};
	}


}
