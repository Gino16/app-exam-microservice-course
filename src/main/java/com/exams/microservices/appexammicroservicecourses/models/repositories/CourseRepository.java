package com.exams.microservices.appexammicroservicecourses.models.repositories;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {
  @Query("SELECT c FROM Course c JOIN FETCH c.students s WHERE s.id = ?1")
  public Course findCourseByStudentId(Long id);
}
