import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable;

    public Timetable() {
        this.timetable = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            daySchedule = new TreeMap<>();
            timetable.put(dayOfWeek, daySchedule);
        }

        List<TrainingSession> sessionsAtTime = daySchedule.get(timeOfDay);

        if (sessionsAtTime == null) {
            sessionsAtTime = new ArrayList<>();
            daySchedule.put(timeOfDay, sessionsAtTime);
        }

        sessionsAtTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);
        return daySchedule != null ? daySchedule : new TreeMap<>();
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(dayOfWeek);

        if (daySchedule == null) {
            return new ArrayList<>();
        }

        List<TrainingSession> sessions = daySchedule.get(timeOfDay);
        return sessions != null ? new ArrayList<>(sessions) : new ArrayList<>();
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> coachCounts = new HashMap<>();

        for (DayOfWeek day : timetable.keySet()) {
            TreeMap<TimeOfDay, List<TrainingSession>> daySchedule = timetable.get(day);

            for (TimeOfDay time : daySchedule.navigableKeySet()) {
                List<TrainingSession> sessions = daySchedule.get(time);

                for (TrainingSession session : sessions) {
                    Coach coach = session.getCoach();
                    coachCounts.put(coach, coachCounts.getOrDefault(coach, 0) + 1);
                }
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();
        for (Coach coach : coachCounts.keySet()) {
            result.add(new CounterOfTrainings(coach, coachCounts.get(coach)));
        }

        Collections.sort(result);

        return result;
    }
}