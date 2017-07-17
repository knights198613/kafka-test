package com.jiangwei.kafkatest.msconsumer.impl;

import com.jiangwei.kafkatest.msconsumer.AbstractMsConsumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
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
    ArrayBlockingQueue<ConsumerRecord<Object,Object>> bufferList = new ArrayBlockingQueue<ConsumerRecord<Object, Object>>(1024, true);



    public void init() {
        prop = new Properties();
        prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getKafkaServers());
        prop.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.setProperty(ConsumerConfig.GROUP_ID_CONFIG, getConsumerGroup());
        prop.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, getEnableAutoCommit());
    }

    public void consumerMessage() {
        try {
            ConsumerRecord<Object, Object> consumerRecord =  bufferList.take();
            System.out.println(" 消费结果为：key_"+consumerRecord.key()+", value_"+consumerRecord.value()+", partition_"+consumerRecord.partition());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void run() {
        KafkaConsumer<Object, Object> kafkaConsumer;
        kafkaConsumer = new KafkaConsumer(this.getProp());
        kafkaConsumer.subscribe(Arrays.asList(getTopic()));
        List<ConsumerRecord<Object, Object>> consumerRecordList = new ArrayList<ConsumerRecord<Object, Object>>();
        try {

            while (!this.closed.get()) {
                ConsumerRecords<Object, Object> consumerRecords = kafkaConsumer.poll(this.MAX_POLL_TIMEOUT);
                /*for(TopicPartition topicPartition : consumerRecords.partitions()) {
                    int partition = topicPartition.partition();
                    System.out.println("消费端消费的partion:"+partition);
                }*/
                for(ConsumerRecord<Object,Object> consumerRecord : consumerRecords) {
                    consumerRecordList.add(consumerRecord);
                }

                if(consumerRecordList.size() > MIN_BATCH_SIZE) {
                    for (ConsumerRecord<Object,Object> consumerRecord : consumerRecords) {
                        try {
                            bufferList.put(consumerRecord);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    kafkaConsumer.commitSync();
                    consumerRecordList.clear();
                    this.consumerMessage();
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
