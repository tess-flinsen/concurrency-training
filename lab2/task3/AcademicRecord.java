package lab2.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.*;

class AcademicRecord {
    private final List<List<StudentGrades>> records;
    private final ReentrantLock lock = new ReentrantLock();

    public AcademicRecord(int totalGroups, int studentsPerGroup) {
        records = new ArrayList<>();
        for (int i = 0; i < totalGroups; i++) {
            List<StudentGrades> group = new ArrayList<>();
            for (int j = 0; j < studentsPerGroup; j++) {
                group.add(new StudentGrades());
            }
            records.add(group);
        }
    }

    public void recordGrade(int groupNumber, int studentNumber, int grade, String instructor, int week) {
        lock.lock();
        try {
            records.get(groupNumber).get(studentNumber).addGrade(grade);
            System.out.printf("%s recorded %d points for Student %d in Group %d for Week %d%n", 
                              instructor, grade, studentNumber + 1, groupNumber + 1, week);
        } finally {
            lock.unlock();
        }
    }

    public void displayRecords() {
        System.out.println("\n-------------------------------------------------------------------");
        System.out.println("\nAcademic Records:");
        for (int i = 0; i < records.size(); i++) {
            System.out.println("Group " + (char) ('A' + i) + ":");
            for (int j = 0; j < records.get(i).size(); j++) {
                System.out.printf("  Student %d: %s%n", j + 1, records.get(i).get(j).getGrades());
            }
        }
    }

    public int getTotalGroups() {
        return records.size();
    }

    public int getStudentsPerGroup() {
        return records.isEmpty() ? 0 : records.get(0).size();
    }
}