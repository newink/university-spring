package com.smarterama.university.controllers;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DisciplineController {

    @RequestMapping(value = "/discipline", method = RequestMethod.POST)
    public ResponseEntity<Void> createDiscipline(@RequestBody Discipline discipline, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        discipline.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/discipline/{id}").buildAndExpand(discipline.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Discipline> updateDiscipline(@PathVariable("id") int id, @RequestBody Discipline discipline) throws PersistenceException {
        discipline.setId(id);
        discipline.persist();

        return new ResponseEntity<>(discipline, HttpStatus.OK);
    }
}
