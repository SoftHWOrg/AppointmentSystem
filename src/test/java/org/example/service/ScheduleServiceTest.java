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
import java.util.List;

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

    @Test
    @DisplayName("Booking: Should mark slot as unavailable and update repo")
    void testBookSlot() {
        TimeSlot slot = new TimeSlot(1, LocalDate.of(2026, 1, 1), LocalTime.of(9, 0), LocalTime.of(10, 0), true);
        schedule.addSlot(slot);

        scheduleService.bookSlot(1);

        assertFalse(slot.isAvailable());
        verify(slotRepo).update(slot);
    }

    @Test
    @DisplayName("Validation: Should fail to add slot if end time is before start time")
    void testAddSlot_ValidationFailure() {
        TimeSlot invalidSlot = new TimeSlot(0, LocalDate.of(2026, 1, 1), LocalTime.of(10, 0), LocalTime.of(9, 0), true);
        
        assertThrows(IllegalArgumentException.class, () -> scheduleService.addSlot(invalidSlot));
    }

    @Test
    @DisplayName("Creation: Should save new slot and add to schedule")
    void testAddSlot_Success() {
        TimeSlot newSlot = new TimeSlot(0, LocalDate.of(2026, 2, 1), LocalTime.of(13, 0), LocalTime.of(14, 0), true);
        
        scheduleService.addSlot(newSlot);
        
        assertTrue(schedule.getTimeSlots().contains(newSlot));
        verify(slotRepo).save(newSlot);
    }

    @Test
    @DisplayName("Retrieval: Should combine repository slots and local schedule slots")
    void testGetAvailableSlots() {
        TimeSlot repoSlot = new TimeSlot(1, LocalDate.now(), LocalTime.of(8,0), LocalTime.of(9,0), true);
        when(slotRepo.findAll()).thenReturn(java.util.Collections.singletonList(repoSlot));
        
        List<TimeSlot> available = scheduleService.getAvailableSlots();
        
        assertFalse(available.isEmpty());
        assertTrue(available.stream().anyMatch(s -> s.getId() == 1));
    }
    @Test
    @DisplayName("Freeing: Should free slot by ID and update repository")
    void testFreeSlotById() {
        TimeSlot slot = new TimeSlot(1, LocalDate.of(2026, 1, 1), LocalTime.of(9, 0), LocalTime.of(10, 0), false);
        schedule.addSlot(slot);

        scheduleService.freeSlot(1);

        assertTrue(slot.isAvailable());
        verify(slotRepo).update(slot);
    }

    @Test
    @DisplayName("Null Safety: Should handle null inputs gracefully")
    void testNullChecks() {
        assertThrows(IllegalArgumentException.class, () -> scheduleService.addSlot(null));
        assertDoesNotThrow(() -> scheduleService.freeSlot(null));
    }

    @Test
    @DisplayName("Freeing Existing: Should free existing slot in schedule")
    void testFreeExistingSlot() {
        TimeSlot slot = new TimeSlot(1, LocalDate.of(2026, 1, 1), LocalTime.of(9, 0), LocalTime.of(10, 0), false);
        schedule.addSlot(slot);
        
        scheduleService.freeSlot(slot); // ID match
        
        assertTrue(slot.isAvailable());
        verify(slotRepo).update(slot);
    }

    @Test
    @DisplayName("Sync: Should synchronize internal schedule with repository data")
    void testSyncWithRepository() {
        TimeSlot newRepoSlot = new TimeSlot(200, LocalDate.now(), LocalTime.of(16, 0), LocalTime.of(17, 0), true);
        when(slotRepo.findAll()).thenReturn(java.util.Collections.singletonList(newRepoSlot));
        
        // This will call syncWithRepository via getAvailableSlots
        List<TimeSlot> slots = scheduleService.getAvailableSlots();
        
        assertTrue(slots.stream().anyMatch(s -> s.getId() == 200));
        assertTrue(schedule.getTimeSlots().stream().anyMatch(s -> s.getId() == 200));
    }
}


