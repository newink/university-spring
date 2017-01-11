package com.smarterama.university.controllers;

import com.smarterama.university.domain.Discipline;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discipline")
public class DisciplineController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Discipline>> listDisciplines() throws PersistenceException {
        List<Discipline> disciplineList = new Discipline().collectAll();
        if (disciplineList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(disciplineList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Discipline> getDisciplines(@PathVariable("id") int id) throws PersistenceException {
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.retrieve();
        if (discipline == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(discipline, HttpStatus.OK);
    }
}
