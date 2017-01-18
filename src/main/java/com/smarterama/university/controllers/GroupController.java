package com.smarterama.university.controllers;

import com.smarterama.university.domain.DomainObject;
import com.smarterama.university.domain.Group;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class GroupController {
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ResponseEntity<Void> createObject(@RequestBody Group group, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        group.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/group/{id}").buildAndExpand(group.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{type}/{id}", method = RequestMethod.PUT)
    public ResponseEntity<? extends DomainObject> updateObject(@PathVariable("id") int id, @RequestBody Group group) throws PersistenceException {
        group.setId(id);
        group.persist();

        return new ResponseEntity<>(group, HttpStatus.OK);
    }
}
