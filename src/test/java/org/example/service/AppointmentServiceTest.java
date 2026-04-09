package org.example.service;

import org.example.domain.appointment.UrgentAppointment;
import org.example.domain.entity.Appointment;
import org.example.domain.entity.Schedule;
import org.example.domain.entity.User;
import org.example.domain.enums.AppointmentStatus;
import org.example.domain.valueobject.TimeSlot;
import org.example.repository.AppointmentRepository;
import org.example.repository.TxtTimeSlotRepository;
import org.example.strategy.BookingRuleStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentServiceTest {

    private FakeAppointmentRepository fakeRepo;
    private FakeScheduleService fakeScheduleService;
    private ReminderService fakeReminderService;
    private FakeBookingRuleStrategy fakeRule;
    private AppointmentService appointmentService;

    @BeforeEach
    void setUp() {
        fakeRepo = new FakeAppointmentRepository();
        fakeScheduleService = new FakeScheduleService();
        fakeReminderService = new ReminderService();
        fakeRule = new FakeBookingRuleStrategy();
        
        appointmentService = new AppointmentService(fakeRepo, fakeScheduleService, fakeReminderService, Arrays.asList(fakeRule));
    }

    @Test
    void testBookAppointment_validAppointment_savesCalled() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(10,0), true);
        Appointment appointment = new UrgentAppointment(1, null, slot, AppointmentStatus.PENDING, 1);
        
        fakeRule.setValid(true);
        appointmentService.bookAppointment(appointment);
        
        assertEquals(AppointmentStatus.CONFIRMED, appointment.getStatus());
        assertTrue(fakeRepo.isSaveCalled());
        assertTrue(fakeScheduleService.isBookSlotCalled());
    }

    @Test
    void testBookAppointment_ruleViolated_throwsAndDoesNotSave() {
        TimeSlot slot = new TimeSlot(1, LocalDate.now(), LocalTime.of(9,0), LocalTime.of(10,0), true);
        Appointment appointment = new UrgentAppointment(1, null, slot, AppointmentStatus.PENDING, 1);
        
        fakeRule.setValid(false);
        fakeRule.setErrorMessage("Rule violated");
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            appointmentService.bookAppointment(appointment);
        });
        
        assertEquals("Rule violated", ex.getMessage());
        assertFalse(fakeRepo.isSaveCalled());
        assertFalse(fakeScheduleService.isBookSlotCalled());
    }

    // --- Fake Implementations to bypass Mockito/JDK23 Agent Restrictions ---

    static class FakeAppointmentRepository implements AppointmentRepository {
        private boolean saveCalled = false;
        public boolean isSaveCalled() { return saveCalled; }

        @Override public void save(Appointment appointment) { saveCalled = true; }
        @Override public void update(Appointment appointment) {}
        @Override public void delete(int id) {}
        @Override public Appointment findById(int id) { return null; }
        @Override public List<Appointment> findByUserId(int userId) { return new ArrayList<>(); }
        @Override public List<Appointment> findAll() { return new ArrayList<>(); }
    }

    static class FakeScheduleService extends ScheduleService {
        private boolean bookSlotCalled = false;
        public FakeScheduleService() {
            super(new Schedule(), new FakeTxtTimeSlotRepository());
        }
        public boolean isBookSlotCalled() { return bookSlotCalled; }
        @Override public void bookSlot(int slotId) { bookSlotCalled = true; }
    }

    static class FakeTxtTimeSlotRepository extends TxtTimeSlotRepository {
        @Override public List<TimeSlot> findAll() { return new ArrayList<>(); }
    }

    static class FakeBookingRuleStrategy implements BookingRuleStrategy {
        private boolean valid = true;
        private String errorMessage = "";

        public void setValid(boolean valid) { this.valid = valid; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

        @Override
        public boolean isValid(Appointment appointment) { return valid; }
        @Override
        public String getErrorMessage() { return errorMessage; }
    }
}
