package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "api/v1/student" )
public class StudentController {


    private  StudentService services;

    @GetMapping
    public List< Student > fetchAllStudents(){
         return services.getAllStudents();
    }

}
