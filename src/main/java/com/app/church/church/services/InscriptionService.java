package com.app.church.church.services;

import java.util.List;

import javax.management.InvalidAttributeValueException;

import com.app.church.church.entities.sections.Inscription;

public interface InscriptionService {

    public List<Inscription> findAllByCourse(String name) throws InvalidAttributeValueException;

    public Inscription getInscriptionById(Long idUser, Long idStudent) throws InvalidAttributeValueException;
    
    public Inscription addInscription(Inscription inscription, Long idUser, Long idCourse) throws InvalidAttributeValueException;

    public Inscription updateInscription(Boolean value, Long id, Long idUser) throws InvalidAttributeValueException;

    public Inscription deleteInscription(Long id, Long idUser) throws InvalidAttributeValueException;

}
