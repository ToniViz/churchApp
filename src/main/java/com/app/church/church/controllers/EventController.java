package com.app.church.church.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.management.InvalidAttributeValueException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.church.church.entities.sections.Event;
import com.app.church.church.services.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/event/v1/")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping("add")
    public ResponseEntity<?> addEvent(@Valid @RequestBody Event event, BindingResult result,
            @RequestParam(name = "id") Long id) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        Event eventOptional = null;
        try {
            eventOptional = this.eventService.addEvent(eventOptional, id);
            return ResponseEntity.ok(eventOptional);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("")
    public ResponseEntity<Page<Event>> findAll(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(this.eventService.findAllEvents(page, size));
    }

    @GetMapping("{idEvent}")
    public ResponseEntity<?> findById(@PathVariable(name = "idEvent") Long id) {
        Event event = null;
        try {
            event = this.eventService.findById(id);
            return ResponseEntity.ok(event);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update/{idEvent}")
    public ResponseEntity<?> updateEvent(@Valid @RequestBody Event event, BindingResult result,
            @PathVariable(name = "idEvent") Long id) {
        if (result.hasFieldErrors()) {
            return this.validation(result);
        }
        Event eventResponse = null;
        try {
            eventResponse = this.eventService.updateEvent(event, id);
            return ResponseEntity.ok(eventResponse);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @DeleteMapping("delete/{idEvent}")
    public ResponseEntity<?> deleteEvent(@PathVariable(name = "idEvent") Long id) {
      
        try {
            this.eventService.deleteEvent(id);
            return ResponseEntity.noContent().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("update-organizer/{idUser}/{idEvent}")
    public ResponseEntity<?> updateOrganizer(@PathVariable(name = "idUser") Long idUser,
            @PathVariable(name = "idEvent") Long idEvent) {
        
        try {
            this.eventService.updateOrganizer(idUser, idEvent);
            return ResponseEntity.noContent().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("add-assistent/{idUser}/{idEvent}")
    public ResponseEntity<?> addEventAssistent(@PathVariable(name = "idUser") Long idUser,
            @PathVariable(name = "idEvent") Long idEvent) {
        Event event = null;
        try {
            event = this.eventService.addAssistent(idUser, idEvent);
            return ResponseEntity.ok(event);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("delete-assistent/{idUser}/{idEvent}")
    public ResponseEntity<?> deleteAsistent(@PathVariable(name = "idUser")Long idUser,
    @PathVariable(name = "idEvent")Long idEvent){
        Event event = null;
        try {
            event = this.eventService.deleteAssistent(idUser, idEvent);
            return ResponseEntity.ok(event);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }



    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " no puede estar vacío.");
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
