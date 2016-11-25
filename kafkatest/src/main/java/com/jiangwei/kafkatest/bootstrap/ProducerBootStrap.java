package com.jiangwei.kafkatest.bootstrap;

import com.jiangwei.kafkatest.bean.User;
import com.jiangwei.kafkatest.msproducer.impl.KafkaMsProducer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc: 生产端启动类
 */
public class ProducerBootStrap {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        KafkaMsProducer producer = (KafkaMsProducer)context.getBean("kafkaProducer");
        User user = new User();
        user.setId(1);
        user.setAge(25);
        user.setName("weijiang");
        user.setEmail("weijiang@jd.com");
        Future<RecordMetadata> metadataFuture = producer.sendMessage(user.toString());
        try {
            RecordMetadata recordMetadata = metadataFuture.get();
            String topic = recordMetadata.topic();
            System.out.println("数据发送的topic为："+ topic);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
