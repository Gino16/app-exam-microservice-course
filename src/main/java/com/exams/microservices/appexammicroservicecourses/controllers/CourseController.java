package com.exams.microservices.appexammicroservicecourses.controllers;

import com.exams.microservices.appexamlibcommonexams.models.entities.Exam;
import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.appexammicroservicecourses.models.entities.CourseStudent;
import com.exams.microservices.appexammicroservicecourses.services.CourseService;
import com.exams.microservices.libcommonmicroservices.controllers.GenericController;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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


  @Override
  @GetMapping
  public ResponseEntity<?> list() {
    List<Course> courses = ((List<Course>) this.service.findAll()).stream()
        .peek(c -> c.getCourseStudents().forEach(cs -> {
          Student student = new Student();
          student.setId(cs.getStudentId());
          c.addStudent(student);
        })).toList();
    return ResponseEntity.ok(courses);
  }

  @Override
  @GetMapping("/pageable")
  public ResponseEntity<?> list(Pageable pageable) {
    Page<Course> coursePages = this.service.findAll(pageable).map(c -> {
      c.getCourseStudents().forEach(cs -> {
        Student student = new Student();
        student.setId(cs.getStudentId());
        c.addStudent(student);
      });
      return c;
    });
    return ResponseEntity.ok(coursePages);
  }

  @Override
  @GetMapping("/{id}")
  public ResponseEntity<?> findById(@PathVariable Long id) {
    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course course = o.get();
    if (!course.getCourseStudents().isEmpty()) {
      List<Long> ids = course.getCourseStudents().stream().map(CourseStudent::getStudentId)
          .toList();
      List<Student> students = (List<Student>) this.service.getStudentsByCourse(ids);
      course.setStudents(students);
    }
    return ResponseEntity.ok(course);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> edit(@Valid @RequestBody Course course, BindingResult result,
      @PathVariable Long id) {

    if (result.hasErrors()) {
      return this.validate(result);
    }

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

    students.forEach(student -> {
      CourseStudent courseStudent = new CourseStudent();
      courseStudent.setStudentId(student.getId());
      courseStudent.setCourse(courseDb);
      courseDb.addCourseStudent(courseStudent);
    });

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

    students.forEach(student -> {
      CourseStudent courseStudent = new CourseStudent();
      courseStudent.setStudentId(student.getId());
      courseStudent.setCourse(courseDb);
      courseDb.removeCourseStudent(courseStudent);
    });

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @GetMapping("/student/{id}")
  public ResponseEntity<?> findCourseByStudentId(@PathVariable Long id) {
    Course course = this.service.findCourseByStudentId(id);
    if (course == null) {
      return ResponseEntity.notFound().build();
    }

    List<Long> examIds = (List<Long>) this.service.getExamsIdsWithAnswersByStudent(id);
    if (Objects.isNull(examIds) || examIds.isEmpty()) {
      return ResponseEntity.ok(course);
    }
    List<Exam> exams = course.getExams().stream().peek(exam -> {
      if (examIds.contains(exam.getId())) {
        exam.setAnswered(true);
      }
    }).toList();

    course.setExams(exams);

    return ResponseEntity.ok().body(course);
  }

  @PutMapping("/{id}/assign-exams")
  public ResponseEntity<?> assignExams(@RequestBody List<Exam> exams, @PathVariable Long id) {
    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course courseDb = o.get();

    exams.forEach(courseDb::addExams);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @PutMapping("/{id}/delete-exams")
  public ResponseEntity<?> deleteExams(@RequestBody List<Exam> exams, @PathVariable Long id) {

    Optional<Course> o = this.service.findById(id);
    if (o.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Course courseDb = o.get();

    exams.forEach(courseDb::removeExams);

    return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(courseDb));
  }

  @DeleteMapping("/delete-student/{idStudent}")
  public ResponseEntity<?> deleteStudent(@PathVariable Long idStudent) {
    this.service.deleteCourseStudentById(idStudent);
    return ResponseEntity.noContent().build();
  }

}

