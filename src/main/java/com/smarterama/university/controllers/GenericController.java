package com.smarterama.university.controllers;


import com.smarterama.university.domain.*;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenericController {

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ResponseEntity<List<? extends DomainObject>> listObjects(@PathVariable("type") String type) throws PersistenceException {
        DomainObject domainObject = getObject(type);
        List<? extends DomainObject> domainObjects = domainObject.collectAll();
        if (domainObjects.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(domainObjects, HttpStatus.OK);
    }

    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.GET)
    public ResponseEntity<? extends DomainObject> getObject(@PathVariable("id") int id, @PathVariable("type") String type) throws PersistenceException {
        DomainObject domainObject = getObject(type);
        domainObject.setId(id);
        domainObject = domainObject.retrieve();
        if (domainObject == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(domainObject, HttpStatus.OK);
    }


    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<? extends DomainObject> deleteObject(@PathVariable("id") int id, @PathVariable("type") String type) throws PersistenceException {
        DomainObject domainObject = getObject(type);
        domainObject.setId(id);
        domainObject.delete();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private DomainObject getObject(String name) {
        switch (name) {
            case "student": return new Student();
            case "group": return new Group();
            case "room": return new Room();
            case "discipline": return new Discipline();
            case "lecturer": return new Lecturer();
            case "lesson": return new Lesson();
            default: return null;
        }
    }
}
