package com.app.church.church.services;

import java.util.Optional;

import javax.management.InvalidAttributeValueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.church.church.entities.sections.Event;
import com.app.church.church.entities.users.User;
import com.app.church.church.repository.EventRepository;
import com.app.church.church.repository.UserRepository;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Page<Event> findAllEvents(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.eventRepository.findAll(pageable);
    }

    @Transactional(readOnly = false)
    @Override
    public Event addEvent(Event event, Long id) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findById(id);
        if(user.isPresent()){
            event.setOrganizer(user.get());
            this.eventRepository.save(event);
        }else{
            throw new InvalidAttributeValueException();
        }
        return event;
    }

    @Transactional(readOnly = true)
    @Override
    public Event findById(Long id) throws InvalidAttributeValueException {
        return this.eventRepository.findById(id).orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Event updateEvent(Event event, Long id) throws InvalidAttributeValueException {
        Optional<Event> eventOptional = this.eventRepository.findById(id);
        if(eventOptional.isPresent()){
            Event e = eventOptional.get();
            e.setName(event.getName());
            e.setDescription(event.getDescription());
            e.setDateInit(event.getDateInit());
            e.setDateEnd(event.getDateEnd());
        }
        return eventOptional.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public boolean deleteEvent(Long id) throws InvalidAttributeValueException {
        Optional<Event> event = this.eventRepository.findByIdWithAssistants(id);
        if(event.isPresent()){
            event.get().getAssistants().forEach(element -> {
                element.getEvents().remove(event.get());
                this.userRepository.save(element);
            });
            this.eventRepository.delete(event.get());
            return true;
        }else{
            throw new InvalidAttributeValueException();
        }
        
    }

    @Transactional
    @Override
    public Event addAssistent(Long idUser, Long idEvent) throws InvalidAttributeValueException {
       Optional<User> user = this.userRepository.findByIdWithEvents(idUser);
       Optional<Event> event = this.eventRepository.findByIdWithAssistants(idEvent);
       if(user.isPresent() && event.isPresent()){
        user.get().getEvents().add(event.get());
        this.userRepository.save(user.get());
       }
       return event.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Event deleteAssistent(Long idUser, Long idEvent) throws InvalidAttributeValueException {
        Optional<User> user = this.userRepository.findByIdWithEvents(idUser);
        Optional<Event> event = this.eventRepository.findById(idEvent);
        if(user.isPresent() && event.isPresent()){
            user.get().getEvents().remove(event.get());
            this.userRepository.save(user.get());
        }
        return event.orElseThrow(InvalidAttributeValueException::new);
    }

    @Transactional
    @Override
    public Event updateOrganizer(Long idUser, Long idEvent) throws InvalidAttributeValueException {
        Optional<Event> event = this.eventRepository.findById(idEvent);
        Optional<User> user = this.userRepository.findById(idUser);
        if(user.isPresent() && event.isPresent()){
            event.get().setOrganizer(user.get());
            this.eventRepository.save(event.get());
        }
        return event.orElseThrow(InvalidAttributeValueException::new);
    }

}
