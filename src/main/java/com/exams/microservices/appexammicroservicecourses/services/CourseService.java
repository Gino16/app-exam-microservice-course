package com.exams.microservices.appexammicroservicecourses.services;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.libcommonmicroservices.services.GenericService;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface CourseService extends GenericService<Course> {

  public Course findCourseByStudentId(Long id);

  public Iterable<Student> getStudentsByCourse(List<Long> ids);

  public void deleteCourseStudentById(Long studentId);

  public Iterable<Long> getExamsIdsWithAnswersByStudent(@PathVariable Long studentId);
}
