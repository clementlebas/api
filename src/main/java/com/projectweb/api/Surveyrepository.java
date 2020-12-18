package com.projectweb.api;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface Surveyrepository extends CrudRepository<Survey, Long> {
    List<Survey> findByUserid(Long userid);
}
