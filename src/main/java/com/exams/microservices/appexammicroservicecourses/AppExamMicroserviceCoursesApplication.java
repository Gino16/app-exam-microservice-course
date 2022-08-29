package com.exams.microservices.appexammicroservicecourses;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.exams.microservices.libcommonstudents.models.entities",
    "com.exams.microservices.appexammicroservicecourses.models.entities"})
public class AppExamMicroserviceCoursesApplication {

  public static void main(String[] args) {
    SpringApplication.run(AppExamMicroserviceCoursesApplication.class, args);
  }

}
