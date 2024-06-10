package com.app.church.church.controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.entities.users.Message;
import com.app.church.church.services.MessageService;



@RestController
@RequestMapping("/api/message/v1/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Siempre se debe mapear desde los campos
     * 
     * @param message
     * @param result
     * @param idSender
     * @param idReceiver
     * @return
     */
    @PostMapping("add/{idSender}/{idReceiver}")
    public ResponseEntity<?> addMessage(@RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "content", required = true) String content,
            @RequestParam(name = "file", required = false) MultipartFile file,
            @PathVariable(name = "idSender") Long idSender, @PathVariable(name = "idReceiver") Long idReceiver) {
        Message messageSaved = null;

        try {
            messageSaved = this.messageService.addMessage(title, content, file, idSender, idReceiver);
            return ResponseEntity.ok(messageSaved);
        } catch (InvalidAttributeValueException | SQLException | IOException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("{id}")
    public ResponseEntity<?> getMessageById(@PathVariable(name = "id") Long id) {
        Message message = null;
        try {
            message = this.messageService.findMessageByID(id);
            return ResponseEntity.ok(message);
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("delete/{idUser}/{idMessage}")
    public ResponseEntity<?> deleteMessage(@PathVariable(name = "idUser") Long idUser,
    @PathVariable(name = "idMessage")Long idMessage) {
        
        try {
            this.messageService.deleteMessage(idUser, idMessage);
            return ResponseEntity.noContent().build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("delete-dialog/{idUser}/{idMessage}")
    public ResponseEntity<?> deleteDialog(@PathVariable(name = "idUser")Long idUser,
    @PathVariable(name = "idMessage")Long idMessage){
        try {
            this.messageService.deleteMessagesByConversation(idUser, idMessage);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("delete-all/{idUser}")
    public ResponseEntity<?> deletebyUser(@PathVariable(name = "idUser")Long id){
        try {
            this.messageService.deleteAllMessagesByUserId(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (InvalidAttributeValueException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
