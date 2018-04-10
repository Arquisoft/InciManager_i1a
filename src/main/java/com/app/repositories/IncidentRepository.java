package com.app.repositories;

import com.app.entities.Incident;
import com.app.entities.Agent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface IncidentRepository extends MongoRepository<Incident,Long>{
    @Query("{'agent.id' : '?0'} }")
    List<Incident> findByAgentID(String id);
}
