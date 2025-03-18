package org.itmo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MagicCalendar {

    public Map<String, List<Meeting>> meetings = new HashMap<>();

    // Перечисление типов встреч
    public enum MeetingType {
        WORK, PERSONAL
    }

    /**
     * Запланировать встречу для пользователя.
     *
     * @param user имя пользователя
     * @param time временной слот (например, "10:00")
     * @param type тип встречи (WORK или PERSONAL)
     * @return true, если встреча успешно запланирована, false если:
     *         - в этот временной слот уже есть встреча, и правило замены не выполняется,
     *         - лимит в 5 встреч в день уже достигнут.
     */
    public boolean scheduleMeeting(String user, String time, MeetingType type) {
        var meeting = new Meeting(type, time);
        if (!meetings.containsKey(user)) {
            meetings.put(user, new ArrayList<>());
            meetings.get(user).add(meeting);
            return true;
        }
        var mt = meetings.get(user);
        int id = -1;
        for (int i = 0; i < mt.size(); ++i) {
            if (mt.get(i).time.equals(meeting.time) && mt.get(i).meetingType.equals(MeetingType.WORK) && meeting.meetingType.equals(MeetingType.PERSONAL)) {
                id = i;
            } else if (mt.get(i).time.equals(meeting.time)) {
                return false;
            }

        }
        if (id == -1) {
            mt.add(meeting);
        } else {
            mt.add(id, meeting);
        }
        if (mt.size() <= 5) {
            meetings.put(user, mt);
        }
        return mt.size() <= 5;
    }

    /**
     * Получить список всех встреч пользователя.
     *
     * @param user имя пользователя
     * @return список временных слотов, на которые запланированы встречи.
     */
    public List<String> getMeetings(String user) {
        if (!meetings.containsKey(user)) {
            return new ArrayList<>();
        }
        return meetings.get(user).stream().map((Meeting m) -> m.time).toList();
    }

    /**
     * Отменить встречу для пользователя по заданному времени.
     *
     * @param user имя пользователя
     * @param time временной слот, который нужно отменить.
     * @return true, если встреча была успешно отменена; false, если:
     *         - встреча в указанное время отсутствует,
     *         - встреча имеет тип PERSONAL (отменять можно только WORK встречу).
     */
    public boolean cancelMeeting(String user, String time) {
        // Реализация метода
        if (!meetings.containsKey(user)) {
            throw new IllegalArgumentException("no user found");
        }
        var mt = meetings.get(user);
        int id = 0;
        Meeting m = null;
        for (int i = 0; i < mt.size(); ++i) {
            if (mt.get(i).time.equals(time)) {
                m = mt.get(i);
                id = i;
            }
        }
        if (m == null) {
            return false;
        }
        if (m.meetingType == MeetingType.WORK) {
            return false;
        }
        mt.remove(id);
        meetings.put(user, mt);
        return true;
    }

    public class Meeting {
        public MeetingType meetingType;
        public String time;

        public Meeting(MeetingType meetingType, String time) {
            this.meetingType = meetingType;
            this.time = time;
        }
    }
}
