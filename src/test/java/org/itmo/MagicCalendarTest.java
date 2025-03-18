package org.itmo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MagicCalendarTest {

    @Test
    public void testReplaceWorkWithPersonal() {
        MagicCalendar calendar = new MagicCalendar();

        // Планируем рабочую встречу на "10:00"
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));

        // Попытка добавить вторую рабочую встречу на "10:00" должна вернуть false
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));

        // Добавление личной встречи на "10:00" заменяет рабочую встречу
        assertTrue(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.PERSONAL));

        // После замены, попытка добавить рабочую встречу снова должна вернуть false
        assertFalse(calendar.scheduleMeeting("Alice", "10:00", MagicCalendar.MeetingType.WORK));
    }

    @Test
    public void testDailyMeetingLimit() {
        MagicCalendar calendar = new MagicCalendar();

        // Планируем 5 встреч для пользователя "Bob" в разные временные слоты
        assertTrue(calendar.scheduleMeeting("Bob", "09:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "10:00", MagicCalendar.MeetingType.PERSONAL));
        assertTrue(calendar.scheduleMeeting("Bob", "11:00", MagicCalendar.MeetingType.WORK));
        assertTrue(calendar.scheduleMeeting("Bob", "12:00", MagicCalendar.MeetingType.PERSONAL));
        assertTrue(calendar.scheduleMeeting("Bob", "13:00", MagicCalendar.MeetingType.WORK));

        // Попытка добавить 6-ю встречу должна вернуть false
        assertFalse(calendar.scheduleMeeting("Bob", "14:00", MagicCalendar.MeetingType.WORK));
    }
}