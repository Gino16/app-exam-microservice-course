package com.exams.microservices.appexammicroservicecourses.clients;

import com.exams.microservices.libcommonstudents.models.entities.Student;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "student-service")
public interface StudentFeignClient {

  @GetMapping("/students-by-course")
  public Iterable<Student> getStudentsByCourse(@RequestParam List<Long> ids);
}
