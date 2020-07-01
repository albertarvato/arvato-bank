package com.arvato.spring.repos;

import com.arvato.spring.models.Test;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestRepo extends ReactiveCrudRepository<Test, Long> {


}
