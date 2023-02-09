package com.alvin.boot.student;

import com.alvin.boot.exception.ApiRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentDataAccessService studentDataAccessService;

    @Autowired
    public StudentService(StudentDataAccessService studentDataAccessService) {
        this.studentDataAccessService = studentDataAccessService;
    }

     List<Student> getAllStudents() {
       return studentDataAccessService.selectAllStudents();
    }

    void addNewStudent(Student student) {
        addNewStudent(null, student);
    }

     void addNewStudent(UUID studentId, Student student) {
        //if the client does not send studentId we have to generate one ourselves
         UUID newStudentId = Optional.ofNullable(studentId).orElse(UUID.randomUUID());

         //TODO: Verify that email is not taken
         if (studentDataAccessService.isEmailTaken(student.getEmail())) {
             throw new ApiRequestException(" The Email " + student.getEmail() + " is already taken");
         }

         studentDataAccessService.insertStudent(newStudentId, student);
     }

    public List<StudentCourse> getAllCoursesForSudent(UUID studentID) {
        return studentDataAccessService.selectAllStudentsCourses(studentID);
    }
}
