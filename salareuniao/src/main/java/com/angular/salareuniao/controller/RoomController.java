package com.angular.salareuniao.controller;
import com.angular.salareuniao.exception.ResourceNotFoundException;
import com.angular.salareuniao.model.Room;
import com.angular.salareuniao.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1")
public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRooms(){
    return roomRepository.findAll();
    }
    @GetMapping("rooms/id")
    public ResponseEntity<Room> getRoombyId(@PathVariable(value = "id")long roomId)
        throws ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
        .orElseThrow(()-> new ResourceNotFoundException("Root not found:: "+roomId));
    return ResponseEntity.ok().body(room);
    }
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("romms/{id}")
    public ResponseEntity<Room> updateRoom (@PathVariable(value = "id")Long roomId,
                                            @Valid @RequestBody Room roomDetails) throws ResourceNotFoundException{
                                            Room room  = roomRepository.findById(roomId)
        .orElseThrow(()-> new ResourceNotFoundException("Root not found for this id:: "+roomId));
        room.setName(roomDetails.getName());
        room.setDate(roomDetails.getDate());
        room.setStartHour(roomDetails.getStartHour());
        room.setEndHour(roomDetails.getEndHour());
        final Room updateroom = roomRepository.save(room);
        return ResponseEntity.ok (updateroom);
    }

    public Map<String , Boolean> deleteRoom (@PathVariable(value = "id")Long roomId) throws ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new ResourceNotFoundException("Room not found for this id::"+roomId));
        roomRepository.delete(room);
        Map<String, Boolean>response  = new HashMap<>();
        response.put ("deleted", Boolean.TRUE);
        return response;
    }
}
