package com.exams.microservices.appexammicroservicecourses.models.repositories;

import com.exams.microservices.appexammicroservicecourses.models.entities.Course;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {

  @Query("SELECT c FROM Course c JOIN FETCH c.courseStudents cs WHERE cs.studentId = ?1")
  public Course findCourseByStudentId(Long id);

  @Modifying
  @Query("DELETE FROM CourseStudent cs WHERE cs.studentId = ?1")
  public void deleteCourseStudentById(Long studentId);
}
