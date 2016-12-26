package com.jiangwei.kafkatest.msconsumer.impl;

import com.jiangwei.kafkatest.msconsumer.AbstractMsConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.WakeupException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by weijiang
 * Date: 2016/12/20
 * Desc: kafka消息的消费端
 */
public class KafkaMsConsumerRuner extends AbstractMsConsumer implements Runnable {
    //private KafkaConsumer kafkaConsumer;
    private static final AtomicBoolean closed = new AtomicBoolean(false);
    private static final int MAX_POLL_TIMEOUT = 100000;
    private static final int MIN_BATCH_SIZE = 0;
    List<ConsumerRecord<String,String>> bufferList = new ArrayList<ConsumerRecord<String, String>>();



    public void init() {
        prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaServers());
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, getConsumerGroup());
        prop.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, getEnableAutoCommit());
    }

    public void consumerMessage() {

    }

    public void run() {
        KafkaConsumer<String, String> kafkaConsumer;
        kafkaConsumer = new KafkaConsumer(this.getProp());
        kafkaConsumer.subscribe(Arrays.asList(getTopic()));
        try {

            while (!this.closed.get()) {
                ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(this.MAX_POLL_TIMEOUT);
                /*for(TopicPartition topicPartition : consumerRecords.partitions()) {
                    int partition = topicPartition.partition();
                    System.out.println("消费端消费的partion:"+partition);
                }*/
                for(ConsumerRecord<String,String> consumerRecord : consumerRecords) {
                    bufferList.add(consumerRecord);
                }

                if(bufferList.size() > MIN_BATCH_SIZE) {
                    for(ConsumerRecord<String,String> record : bufferList) {
                        System.out.println("结果为："+record.key()+"__"+record.value());
                    }
                    kafkaConsumer.commitSync();
                    bufferList.clear();
                }
            }
        }catch (WakeupException e) {
            if(!this.closed.get())
                throw e;
        }finally {
            kafkaConsumer.close();
        }
    }

    public void shutDown() {
        closed.set(true);
    }


}
