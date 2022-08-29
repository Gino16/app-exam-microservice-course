package com.exams.microservices.appexammicroservicecourses.controllers;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.appexammicroservicecourses.services.CourseService;
import com.exams.microservices.libcommonmicroservices.controllers.GenericController;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController extends GenericController<CourseService, Course> {

  public CourseController(CourseService service) {
    super(service);
  }


  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@RequestBody Course course, @PathVariable Long id) {
    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course courseDb = o.get();
    courseDb.setName(course.getName());

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @PutMapping("/{id}/assign-students")
  public ResponseEntity<?> assignStudents(@RequestBody List<Student> students,
      @PathVariable Long id) {
    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course courseDb = o.get();

    students.forEach(courseDb::addStudent);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @PutMapping("/{id}/delete-students")
  public ResponseEntity<?> deleteStudents(@RequestBody List<Student> students,
      @PathVariable Long id) {

    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course courseDb = o.get();

    students.forEach(courseDb::removeStudent);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @GetMapping("/student/{id}")
  public ResponseEntity<?> findCourseByStudentId(@PathVariable Long id) {
    return ResponseEntity.ok().body(this.service.findCourseByStudentId(id));
  }
}

