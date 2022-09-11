package com.exams.microservices.appexammicroservicecourses.services.impl;

import com.exams.microservices.appexammicroservicecourses.clients.AnswerFeignClient;
import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.appexammicroservicecourses.models.repositories.CourseRepository;
import com.exams.microservices.appexammicroservicecourses.services.CourseService;
import com.exams.microservices.libcommonmicroservices.services.impl.GenericServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl extends GenericServiceImpl<Course, CourseRepository> implements
    CourseService {

  private final AnswerFeignClient client;

  public CourseServiceImpl(CourseRepository repository, AnswerFeignClient client) {
    super(repository);
    this.client = client;
  }

  @Transactional(readOnly = true)
  public Course findCourseByStudentId(Long id) {
    return this.repository.findCourseByStudentId(id);
  }

  @Override
  public Iterable<Long> getExamsIdsWithAnswersByStudent(Long studentId) {
    return client.getExamsIdsWithAnswersByStudent(studentId);
  }

}

