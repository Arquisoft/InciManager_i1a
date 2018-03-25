package com.app.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.app.entities.AgentInfo;

@Repository
public interface AgentInfoRepository extends MongoRepository<AgentInfo, String>{
}
