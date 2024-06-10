package com.app.church.church.controllers;

import java.util.List;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.church.church.entities.sections.Inscription;
import com.app.church.church.services.InscriptionService;

@RestController
@RequestMapping("/api/inscription/v1/")
public class InscriptionController {

    @Autowired
    private InscriptionService inscriptionService;

    @GetMapping("{name}")
    public ResponseEntity<List<Inscription>> getAllInscriptionsByCourseName(@PathVariable(name = "name")String name){
        List<Inscription> inscriptions = null;
        try {
            inscriptions = this.inscriptionService.findAllByCourse(name);
            return ResponseEntity.ok(inscriptions);
        } catch (InvalidAttributeValueException e) {
           return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("{idStudent}/{idUser}")
    public ResponseEntity<?> getInscriptionbyID(@PathVariable Long idUser,
    @PathVariable Long idStudent){
        Inscription inscription = null;
        try {
            inscription = this.inscriptionService.getInscriptionById(idUser, idStudent);
            return ResponseEntity.ok(inscription);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("add/{idUser}/{idInscription}")
    public ResponseEntity<?> addInscription(@RequestBody Inscription inscription,
    @PathVariable(name = "idUSer")Long idUser, @PathVariable(name = "idInscription")Long isCourse){
        Inscription inscriptionResponse = null;
        try {
            inscriptionResponse = this.inscriptionService.addInscription(inscriptionResponse, idUser, isCourse);
            return ResponseEntity.ok(inscriptionResponse);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @PutMapping("update/{value}/{idInscription}/{idUser}")
    public ResponseEntity<?> updateInscription(@PathVariable(name = "value")
    Boolean value, @PathVariable(name = "idInscription")Long id,
    @PathVariable Long idUser){
        Inscription inscription = null;
        try {
            inscription = this.inscriptionService.updateInscription(value, id, idUser);
            return ResponseEntity.ok(inscription);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("delete/{idInscription}/{idUser}")
    public ResponseEntity<?> deleteInscription(@PathVariable(name = "idInscription")
    Long id, @PathVariable Long idUser){
        Inscription inscription = null;
        try{
            inscription = this.inscriptionService.deleteInscription(id, idUser);
            return ResponseEntity.noContent().build();
        }catch(InvalidAttributeValueException e){
            return ResponseEntity.badRequest().body(inscription);
        }
    }

}
