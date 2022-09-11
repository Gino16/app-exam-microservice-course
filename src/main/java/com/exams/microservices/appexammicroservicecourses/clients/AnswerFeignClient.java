package com.exams.microservices.appexammicroservicecourses.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "answer-service")
public interface AnswerFeignClient {
  @GetMapping("/student/{studentId}/exams-answered")
  public Iterable<Long> getExamsIdsWithAnswersByStudent(@PathVariable Long studentId);
}
