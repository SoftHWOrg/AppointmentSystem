package org.example.repository;

import org.example.domain.valueobject.TimeSlot;
import java.util.List;

public interface TimeSlotRepository {
    void save(TimeSlot slot);
    List<TimeSlot> findAll();
    void update(TimeSlot slot);
}
