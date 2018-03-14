package InciManager.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class AgentInfoSerializer implements Serializer{
    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public byte[] serialize(String s, Object agentInfo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            return objectMapper.writeValueAsBytes(agentInfo);
        }catch(JsonProcessingException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }
}
