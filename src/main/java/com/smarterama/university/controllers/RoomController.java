package com.smarterama.university.controllers;

import com.smarterama.university.domain.Room;
import com.smarterama.university.exceptions.ErrorResponse;
import com.smarterama.university.exceptions.PersistenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoomController {

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public ResponseEntity<Void> createRoom(@RequestBody Room room, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        room.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/room/{id}").buildAndExpand(room.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/room/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int id, @RequestBody Room room) throws PersistenceException {
        room.setId(id);
        room.persist();
        return new ResponseEntity<>(room, HttpStatus.OK);
    }
}
