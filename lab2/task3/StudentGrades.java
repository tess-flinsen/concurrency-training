package lab2.task3;

import java.util.ArrayList;
import java.util.List;

class StudentGrades {
    private final List<Integer> grades = new ArrayList<>();

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }
}
