package repositories;

import org.springframework.data.repository.CrudRepository;

import entities.AgentInfo;

public interface AgentInfoRepository extends CrudRepository<AgentInfo, String>{
}
