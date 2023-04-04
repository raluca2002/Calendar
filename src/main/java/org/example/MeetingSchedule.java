package org.example;
import java.util.*;

public class MeetingSchedule {

    public static List<String[]> findAvailableMeetingTimes(List<String[]> calendar1, String[] range1,
                                                           List<String[]> calendar2, String[] range2,
                                                           int meetingTime) {
        List<String[]> availableTimes = new ArrayList<>();
        int rangeStart = Math.max(getMinutes(range1[0]), getMinutes(range2[0]));
        int rangeEnd = Math.min(getMinutes(range1[1]), getMinutes(range2[1]));
        int prevEndTime = rangeStart;
        for (String[] event : mergeCalendars(calendar1, calendar2)) {
            int eventStart = getMinutes(event[0]);
            int eventEnd = getMinutes(event[1]);
            if (eventStart - prevEndTime >= meetingTime) {
                availableTimes.add(new String[] {getTimeString(prevEndTime), getTimeString(eventStart)});
            }
            prevEndTime = Math.max(prevEndTime, eventEnd);
        }
        if (rangeEnd - prevEndTime >= meetingTime) {
            availableTimes.add(new String[] {getTimeString(prevEndTime), getTimeString(rangeEnd)});
        }
        return availableTimes;
    }

    private static List<String[]> mergeCalendars(List<String[]> calendar1, List<String[]> calendar2) {
        List<String[]> merged = new ArrayList<>();
        merged.addAll(calendar1);
        merged.addAll(calendar2);
        Collections.sort(merged, Comparator.comparing(e -> getMinutes(e[0])));
        return merged;
    }

    private static int getMinutes(String timeString) {
        String[] parts = timeString.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
        return hours * 60 + minutes;
    }

    private static String getTimeString(int minutes) {
        int hours = minutes / 60;
        int mins = minutes % 60;
        return String.format("%02d:%02d", hours, mins);
    }

    public static void main(String[] args) {
        List<String[]> calendar1 = new ArrayList<>();
        calendar1.add(new String[] {"9:00","10:30"});
        calendar1.add(new String[] {"12:00","13:00"});
        calendar1.add(new String[] {"16:00","18:00"});
        String[] range1 = new String[] {"9:00","20:00"};
        List<String[]> calendar2 = new ArrayList<>();
        calendar2.add(new String[] {"10:00","11:30"});
        calendar2.add(new String[] {"12:30","14:30"});
        calendar2.add(new String[] {"14:30","15:00"});
        calendar2.add(new String[] {"16:00","17:00"});
        String[] range2 = new String[] {"10:00","18:30"};
        int meetingTime = 30;
        List<String[]> availableTimes = findAvailableMeetingTimes(calendar1, range1, calendar2, range2, meetingTime);
        for (String[] time : availableTimes) {
            System.out.println(Arrays.toString(time));
        }
    }
}
