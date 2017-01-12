package com.smarterama.university.controllers;

import com.smarterama.university.domain.DomainObject;
import com.smarterama.university.domain.Lecturer;
import com.smarterama.university.domain.Lesson;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class LecturerController {
    @RequestMapping(value = "/lecturer", method = RequestMethod.POST)
    public ResponseEntity<Void> createObject(@RequestBody Lecturer lecturer, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        lecturer.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/lecturer/{id}").buildAndExpand(lecturer.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lecturer/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends DomainObject> updateObject(@PathVariable("id") int id, @RequestBody Lecturer lecturer) throws PersistenceException {
        lecturer.setId(id);
        lecturer.persist();

        return new ResponseEntity<>(lecturer, HttpStatus.OK);
    }
}
