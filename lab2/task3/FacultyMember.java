package lab2.task3;

import java.util.Random;

class FacultyMember implements Runnable {
    private final AcademicRecord record;
    private final String facultyMemberName;
    private final boolean isLecturer; 
    private final int assignedGroup; 
    private final Random random = new Random();

    public FacultyMember(AcademicRecord record, String facultyMemberName, boolean isLecturer) {
        this(record, facultyMemberName, isLecturer, -1);
    }

    public FacultyMember(AcademicRecord record, String facultyMemberName, boolean isLecturer, int assignedGroup) {
        this.record = record;
        this.facultyMemberName = facultyMemberName;
        this.isLecturer = isLecturer;
        this.assignedGroup = assignedGroup;
    }

    @Override
    public void run() {
        int numGroups = record.getTotalGroups();
        int studentsPerGroup = record.getStudentsPerGroup();

        for (int week = 1; week <= 4; week++) {
            if (isLecturer) { 
                for (int group = 0; group < numGroups; group++) {
                    for (int student = 0; student < studentsPerGroup; student++) {
                        int grade = random.nextInt(71) + 30; 
                        record.recordGrade(group, student, grade, facultyMemberName, week);
                    }
                }
            } else { 
                for (int student = 0; student < studentsPerGroup; student++) {
                    int grade = random.nextInt(71) + 30; 
                    record.recordGrade(assignedGroup, student, grade, facultyMemberName, week);
                }
            }
            try {
                Thread.sleep(random.nextInt(100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
