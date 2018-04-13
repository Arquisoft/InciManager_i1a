package com.app.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.entities.Incident;

@Repository
public interface IncidentRepository extends MongoRepository<Incident,Long>{
    @Query("{'agent.id' : '?0'} }")
    List<Incident> findByAgentID(String id);
}
