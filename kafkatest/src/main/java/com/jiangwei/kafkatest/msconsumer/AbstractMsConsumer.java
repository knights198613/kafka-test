package com.jiangwei.kafkatest.msconsumer;

import com.jiangwei.kafkatest.bootstrap.ProducerBootStrap;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * Created by weijiang
 * Date: 2016/12/20
 * Desc: 消息消费端的抽象类
 */
public abstract class AbstractMsConsumer implements MsConsumer {
    public String kafkaServers;
    public String topic;
    public String consumerGroup;
    public String enableAutoCommit;
    public Properties prop;




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

    public String getConsumerGroup() {
        return consumerGroup;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public String getEnableAutoCommit() {
        return enableAutoCommit;
    }

    public void setEnableAutoCommit(String enableAutoCommit) {
        this.enableAutoCommit = enableAutoCommit;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }


}
