package InciManager.producers;

import java.util.Properties;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import InciManager.entities.Incident;

public class KafkaIncidentProducer {
    public void sendIncident(Incident incident){
        Properties properties = new Properties();
        properties.setProperty("value.serializer","InciManager.producers.AgentInfoSerializer");
        Producer<String, Incident> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>(incident.getTopic(), incident));
        producer.close();
    }
}
