package com.exams.microservices.appexammicroservicecourses.services;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.libcommonmicroservices.services.GenericService;
import org.springframework.web.bind.annotation.PathVariable;

public interface CourseService extends GenericService<Course> {

  public Course findCourseByStudentId(Long id);

  public Iterable<Long> getExamsIdsWithAnswersByStudent(@PathVariable Long studentId);
}
