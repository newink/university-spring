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
@RequestMapping("/api/room")
public class RoomController {

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity<List<Room>> listRooms() throws PersistenceException {
        List<Room> roomList = new Room().collectAll();
        if (roomList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(roomList, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Room> getRoom(@PathVariable("id") int id) throws PersistenceException {
        Room room = new Room();
        room.setId(id);
        room.retrieve();
        if (room == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public ResponseEntity<Void> createRoom(@RequestBody Room room, UriComponentsBuilder componentsBuilder) throws PersistenceException {
        room.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(componentsBuilder.path("/room/{id}").buildAndExpand(room.getId()).toUri());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Room> updateRoom(@PathVariable("id") int id, @RequestBody Room room) throws PersistenceException {
        Room updatedRoom = new Room();
        updatedRoom.setId(id);
        updatedRoom.retrieve();
        updatedRoom.setCapacity(room.getCapacity());
        updatedRoom.setRoomNumber(room.getRoomNumber());

        updatedRoom.persist();
        return new ResponseEntity<Room>(updatedRoom, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Room> deleteRoom(@PathVariable("id") int id) throws PersistenceException {
        Room room = new Room();
        room.setId(id);
        room.delete();

        return new ResponseEntity<Room>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse> handleExceptions(HttpRequest request, Exception e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), request.getURI());
        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.OK);
    }
}
