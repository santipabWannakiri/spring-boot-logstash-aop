package com.logstash.aop.controller;


import com.logstash.aop.model.Instructor;
import com.logstash.aop.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
public class InstructorController {
    private InstructorService instructorService;
    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping("/instructor")
    public ResponseEntity<?> getInstructorById(@RequestParam("id") int id){
        Optional<Instructor> instructorOptional = instructorService.findById(id);

        if (instructorOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(instructorOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[]");
        }
    }

    @GetMapping("/instructors")
    public ResponseEntity<?> getAllInstructor(){
        List<Instructor> listResult = instructorService.findAll();
        if(listResult.size() != 0 ){
            return ResponseEntity.status(HttpStatus.OK).body(listResult);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("[]");
        }
    }
}
