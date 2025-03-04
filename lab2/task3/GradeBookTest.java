package lab2.task3;

import java.util.*;

public class GradeBookTest {
    public static void main(String[] args) {
        int numGroups = 3;
        int studentsPerGroup = 10;

        AcademicRecord gradeBook = new AcademicRecord(numGroups, studentsPerGroup);

        Thread lecturer = new Thread(new FacultyMember(gradeBook, "Lecturer", true));
        lecturer.start();

        List<Thread> assistants = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            Thread assistant = new Thread(new FacultyMember(gradeBook, "Assistant-" + (i + 1), false, i));
            assistants.add(assistant);
            assistant.start();
        }

        try {
            lecturer.join();
            for (Thread assistant : assistants) {
                assistant.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        gradeBook.displayRecords();
    }
}

