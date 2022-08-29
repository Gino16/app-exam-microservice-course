package com.exams.microservices.appexammicroservicecourses.services;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.libcommonmicroservices.services.GenericService;

public interface CourseService extends GenericService<Course> {

  public Course findCourseByStudentId(Long id);
}
