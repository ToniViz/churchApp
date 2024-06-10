package com.app.church.church.repository;

import java.util.List;
import java.util.Set;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.app.church.church.entities.users.Message;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long>, CrudRepository<Message, Long> {

    
    @Query("select m from Message m where (m.sender.id=?1 and m.receiver.id=?2) or (m.sender.id=?2 and m.receiver.id=?1)")
    public List<Message> findAllBetweenUsers(Long idSender, Long idReceiver);

    @Query("select m from Message m where m.sender.id=?1 or m.receiver.id=?1")
    public List<Message> findAllByUserId(Long id);

    @Query("select m from Message m where m.sender.id=?1 and m.senderDeleted = false")
    Set<Message> findNonDeletedSendMessages(Long id);

    @Query("select m from Message m where m.receiver.id=?1 and m.receiverDeleted = false")
    Set<Message> findNonDeletedReceiveMessages(Long id);
}
