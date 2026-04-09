package org.example.repository;

import org.example.domain.entity.Appointment;

import java.util.List;

public interface AppointmentRepository {

    
    void save(Appointment appointment);

    
    void update(Appointment appointment);

    
    void delete(int id);

    
    Appointment findById(int id);

    
    List<Appointment> findByUserId(int userId);

    
    List<Appointment> findAll();
}
