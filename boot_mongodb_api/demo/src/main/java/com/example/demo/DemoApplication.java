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
		String email = "mac2394q@gmail.com";
		return args -> {
			address adress = new address( "colombia",
					"tulua",
					"057000");


			Student student = new Student(
					"miguel",
					"claros",
					email,
					Gender.MALE,
					adress,
					List.of("senior enginer"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			//usingMongoTemplateAndQuery( repository, mongoTemplate,email,student );
			repository.findStudentByEmail(email).ifPresentOrElse( s -> {
				System.out.println( "already exists "+s );
			}, () -> {
				System.out.println ( "inserting student "+student );
				repository.insert( student );
			});
		};
	}

	private void usingMongoTemplateAndQuery(  StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student ){

		Query query = new Query();
		query.addCriteria( Criteria.where( "email" ).is( email )  );

		List< Student > students = mongoTemplate.find( query, Student.class );
		if(  students.size() > 1 ){
			throw new IllegalStateException(" found many students with email "+email);
		}

		if( students.isEmpty() ){
			System.out.println ( "inserting student "+student );
			repository.insert( student );
		}else{
			System.out.println( "already exists "+student );
		}

	}


}
