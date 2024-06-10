package com.app.church.church.services;


import javax.management.InvalidAttributeValueException;

import org.springframework.data.domain.Page;

import com.app.church.church.entities.sections.Event;


public interface EventService {

    public Page<Event> findAllEvents(int page, int size);

    public Event addEvent(Event event, Long id) throws InvalidAttributeValueException;

    public Event findById(Long id) throws InvalidAttributeValueException;

    public Event addAssistent(Long idUser, Long idEvent) throws InvalidAttributeValueException;

    public Event deleteAssistent(Long idUser, Long idEvent) throws InvalidAttributeValueException;

    public Event updateOrganizer(Long idUser, Long idEvent) throws InvalidAttributeValueException;

    public Event updateEvent(Event event, Long id) throws InvalidAttributeValueException;

    public boolean deleteEvent(Long id) throws InvalidAttributeValueException;
}
