package com.exams.microservices.appexammicroservicecourses.models.entities;

import com.exams.microservices.appexamlibcommonexams.models.entities.Exam;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "courses")
@Getter
@Setter
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty
  private String name;

  @Column(name = "create_at")
  @Temporal(TemporalType.TIMESTAMP)
  private Date createAt;

  @JsonIgnoreProperties(value = {"course", "handler",
      "hibernateLazyInitializer"}, allowSetters = true)
  @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CourseStudent> courseStudents;

  @Transient
  private List<Student> students;

  @ManyToMany(fetch = FetchType.LAZY)
  private List<Exam> exams;

  public Course() {
    this.students = new ArrayList<>();
    this.exams = new ArrayList<>();
    this.courseStudents = new ArrayList<>();
  }

  @PrePersist
  public void prePersist() {
    this.createAt = new Date();
  }

  public void addStudent(Student student) {
    this.students.add(student);
  }

  public void removeStudent(Student student) {
    this.students.remove(student);
  }

  public void addExams(Exam exam) {
    this.exams.add(exam);
  }

  public void removeExams(Exam exam) {
    this.exams.remove(exam);
  }

  public void addCourseStudent(CourseStudent courseStudent) {
    this.courseStudents.add(courseStudent);
  }

  public void removeCourseStudent(CourseStudent courseStudent) {
    this.courseStudents.remove(courseStudent);
  }
}
