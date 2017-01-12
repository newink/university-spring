package com.smarterama.university.controllers;

import com.smarterama.university.domain.DomainObject;
import com.smarterama.university.domain.Student;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class StudentController {
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity<Void> createObject(@RequestBody Student student, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        student.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/student/{id}").buildAndExpand(student.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends DomainObject> updateObject(@PathVariable("id") int id, @RequestBody Student student) throws PersistenceException {
        student.setId(id);
        student.persist();

        return new ResponseEntity<>(student, HttpStatus.OK);
    }
}
