package com.smarterama.university.controllers;

import com.smarterama.university.domain.DomainObject;
import com.smarterama.university.domain.Lesson;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class LessonController {
    @RequestMapping(value = "/lesson", method = RequestMethod.POST)
    public ResponseEntity<Void> createObject(@RequestBody Lesson object, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        object.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/lesson/{id}").buildAndExpand(object.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/lesson/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends DomainObject> updateObject(@PathVariable("id") int id, @RequestBody Lesson lesson) throws PersistenceException {
        lesson.setId(id);
        lesson.persist();

        return new ResponseEntity<>(lesson, HttpStatus.OK);
    }
}
