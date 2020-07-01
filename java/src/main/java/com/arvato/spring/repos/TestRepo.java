package com.arvato.spring.repos;

import com.arvato.spring.models.Test;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;


public interface TestRepo extends ReactiveCrudRepository<Test, Long> {


}
