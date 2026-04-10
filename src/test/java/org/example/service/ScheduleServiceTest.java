package org.example.service;

import org.example.domain.entity.Schedule;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.TxtTimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Schedule Service Tests")
class ScheduleServiceTest {

    private TxtTimeSlotRepository slotRepo;
    private Schedule schedule;
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        slotRepo = mock(TxtTimeSlotRepository.class);
        schedule = new Schedule();
        when(slotRepo.findAll()).thenReturn(new ArrayList<>());
        scheduleService = new ScheduleService(schedule, slotRepo);
    }

    @Test
    @DisplayName("Duplicate Prevention: Should not add a slot if same date/time already exists")
    void testAddSlot_PreventsDuplicates() {
        LocalDate date = LocalDate.of(2026, 1, 1);
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(10, 0);
        
        TimeSlot initial = new TimeSlot(1, date, start, end, true);
        schedule.addSlot(initial); 

        TimeSlot duplicate = new TimeSlot(0, date, start, end, true);

        scheduleService.addSlot(duplicate);

        assertEquals(1, schedule.getTimeSlots().size());
        assertEquals(1, duplicate.getId());
        verify(slotRepo, never()).save(any()); 
    }

    @Test
    @DisplayName("Data Restoration: Should recover and restore missing slots when freed")
    void testFreeSlot_HealsSchedule() {
        TimeSlot orphanedSlot = new TimeSlot(101, LocalDate.of(2027, 2, 2), LocalTime.of(15,0), LocalTime.of(16,0), false);

        scheduleService.freeSlot(orphanedSlot);

        boolean restored = schedule.getTimeSlots().stream()
                .anyMatch(s -> s.getDate().equals(orphanedSlot.getDate()) && s.isAvailable());
        
        assertTrue(restored);
        verify(slotRepo, times(1)).save(any()); 
    }
}
