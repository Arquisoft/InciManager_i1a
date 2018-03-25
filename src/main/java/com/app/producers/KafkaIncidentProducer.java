package com.app.producers;

import java.util.Properties;

import javax.annotation.ManagedBean;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.log4j.Logger;

import com.app.entities.Incident;
@ManagedBean
public class KafkaIncidentProducer {
	 private static final Logger logger = Logger.getLogger(KafkaIncidentProducer.class);

    public void sendIncident(Incident incident){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:2851");
        properties.put("key.serializer","com.app.producers.AgentInfoSerializer");
        properties.put("value.serializer", "com.app.producers.AgentInfoSerializer");
        Producer<String, Incident> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        producer.send(new ProducerRecord<>(incident.getTopic(), incident));
        logger.info("Success on sending incident \"" + incident.getIncidentName() + "\" to topic " + incident.getTopic());
        producer.close();
    }

}
