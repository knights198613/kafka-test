package com.jiangwei.kafkatest.msproducer.impl;

import com.jiangwei.kafkatest.msproducer.AbstractMsProducer;
import com.jiangwei.kafkatest.msproducer.MsProducer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.internals.ProduceRequestResult;

import javax.security.auth.callback.Callback;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc: kafka生产者
 */
public class KafkaMsProducer extends AbstractMsProducer{

    private String kafkaServers;
    private String topic;
    private KafkaProducer kafkaProducer;
    private ProducerRecord producerRecord;


    @Override
    public Future sendMessage(Object message) {
        producerRecord = new ProducerRecord(getTopic(), message);
        return kafkaProducer.send(producerRecord);
    }

    @Override
    public Future sendMessage(Object message, Callback callback) {
        return super.sendMessage(message, callback);
    }

    /**
     * 初始化方法
     */
    public void init() {
        Properties prop = new Properties();
        prop.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaServers());
        prop.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        prop.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        kafkaProducer = new KafkaProducer(prop);
    }


    public String getKafkaServers() {
        return kafkaServers;
    }

    public void setKafkaServers(String kafkaServers) {
        this.kafkaServers = kafkaServers;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
