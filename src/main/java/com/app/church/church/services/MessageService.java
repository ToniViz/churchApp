package com.app.church.church.services;

import java.io.IOException;
import java.sql.SQLException;

import javax.management.InvalidAttributeValueException;
import javax.sql.rowset.serial.SerialException;

import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.entities.users.Message;


public interface MessageService {

    public Message addMessage(String title, String content, MultipartFile file, Long idSender, Long idReceiver) throws InvalidAttributeValueException, SerialException, SQLException, IOException;

    public Message findMessageByID(Long id) throws InvalidAttributeValueException;

    public Message deleteMessage(Long idUser, Long idMessage) throws InvalidAttributeValueException;

    public void deleteMessagesByConversation(Long idUser, Long idMessage) throws InvalidAttributeValueException;

    public void deleteAllMessagesByUserId(Long userId) throws InvalidAttributeValueException;
}
