package com.app.church.church.services;

import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.management.InvalidAttributeValueException;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.app.church.church.entities.users.Message;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.MessageRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Message addMessage(String title, String content, MultipartFile file, Long idSender, Long idReceiver)
            throws InvalidAttributeValueException, SerialException, SQLException, IOException {
        Blob data = new SerialBlob(file.getBytes());
        Message message = new Message(title, content, data);
        Optional<User> sender = this.userRepository.findById(idSender);
        Optional<User> receiver = this.userRepository.findById(idReceiver);
        if (sender.isPresent() && receiver.isPresent()) {
            message.setSender(sender.get());
            message.setReceiver(receiver.get());
            return this.messageRepository.save(message);
        } else {
            throw new InvalidAttributeValueException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Message findMessageByID(Long id) throws InvalidAttributeValueException {
        Optional<Message> message = this.messageRepository.findById(id);
        return message.orElseThrow(InvalidAttributeValueException::new);
    }

    @Override
    public Message deleteMessage(Long idUser, Long idMessage) throws InvalidAttributeValueException {
        Optional<Message> message = this.messageRepository.findById(idMessage);
        if(message.isPresent()){
           Long idReceiver = message.get().getReceiver().getId();
           Long idSender = message.get().getSender().getId();
           if(idReceiver!=idUser && idSender!=idUser){
            throw new InaccessibleObjectException();
           }
           if(idReceiver==idUser){
                if(message.get().isSenderDeleted()){
                    this.messageRepository.delete(message.get());
                }else{
                    message.get().setReceiverDeleted(true);
                    this.messageRepository.save(message.get());
                }
           } else if(idSender==idUser){
                if(message.get().isReceiverDeleted()){
                    this.messageRepository.delete(message.get());
                }else{
                    message.get().setSenderDeleted(true);
                    this.messageRepository.save(message.get());
                }
           }
        }
        return message.orElseThrow(InvalidAttributeValueException::new);
    }

    @Override
    public void deleteMessagesByConversation(Long idUser, Long idMessage) throws InvalidAttributeValueException {
        Optional<Message> messageOptional = this.messageRepository.findById(idMessage);
        Long idSender = null;
        Long idReceiver = null;
        if(messageOptional.isPresent()){
            idSender = messageOptional.get().getSender().getId();
            idReceiver = messageOptional.get().getReceiver().getId();
            List<Message> messages = this.messageRepository.findAllBetweenUsers(idSender, idReceiver);
            List<Message> saves = new ArrayList<>();
            List<Message> deletes = new ArrayList<>();
            messages.forEach(message -> {
                if(message.getReceiver().getId()==idUser){
                    if(message.isSenderDeleted()){
                        deletes.add(message);
                    }else{
                        message.setReceiverDeleted(true);
                        saves.add(message);
                    }
               } else if(message.getSender().getId()==idUser){
                    if(message.isReceiverDeleted()){
                        deletes.add(message);
                    }else{
                        message.setSenderDeleted(true);
                        saves.add(message);
                    }
               }
            });
            this.messageRepository.saveAll(saves);
            this.messageRepository.deleteAll(deletes);

        }else{
            throw new InvalidAttributeValueException();
        }

        
    }

    @Override
    public void deleteAllMessagesByUserId(Long userId) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(userId);
        if(user.isPresent()){
            List<Message> messages = this.messageRepository.findAllByUserId(userId);
            List<Message> saves = new ArrayList<>();
            List<Message> deletes = new ArrayList<>();
            messages.forEach(message -> {
                if(message.getReceiver().getId()==userId){
                    if(message.isSenderDeleted()){
                        deletes.add(message);
                    }else{
                        message.setReceiverDeleted(true);
                        saves.add(message);
                    }
               } else if(message.getSender().getId()==userId){
                    if(message.isReceiverDeleted()){
                        deletes.add(message);
                    }else{
                        message.setSenderDeleted(true);
                        saves.add(message);
                    }
               }
        
            });
            this.messageRepository.saveAll(saves);
            this.messageRepository.deleteAll(deletes);
        }else{
            throw new InvalidAttributeValueException();
        }
    }


}
