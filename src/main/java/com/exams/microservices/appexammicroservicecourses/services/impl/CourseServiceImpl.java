package com.exams.microservices.appexammicroservicecourses.services.impl;

import com.exams.microservices.appexammicroservicecourses.clients.AnswerFeignClient;
import com.exams.microservices.appexammicroservicecourses.clients.StudentFeignClient;
import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import com.exams.microservices.appexammicroservicecourses.models.repositories.CourseRepository;
import com.exams.microservices.appexammicroservicecourses.services.CourseService;
import com.exams.microservices.libcommonmicroservices.services.impl.GenericServiceImpl;
import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseServiceImpl extends GenericServiceImpl<Course, CourseRepository> implements
    CourseService {

  private final AnswerFeignClient client;
  private final StudentFeignClient studentClient;

  public CourseServiceImpl(CourseRepository repository, AnswerFeignClient client, StudentFeignClient
      studentClient) {
    super(repository);
    this.client = client;
    this.studentClient = studentClient;
  }

  @Transactional(readOnly = true)
  public Course findCourseByStudentId(Long id) {
    return this.repository.findCourseByStudentId(id);
  }

  @Override
  public Iterable<Student> getStudentsByCourse(List<Long> ids) {
    return studentClient.getStudentsByCourse(ids);
  }

  @Override
  @Transactional
  public void deleteCourseStudentById(Long studentId) {
    this.repository.deleteCourseStudentById(studentId);
  }

  @Override
  public Iterable<Long> getExamsIdsWithAnswersByStudent(Long studentId) {
    return client.getExamsIdsWithAnswersByStudent(studentId);
  }

}

