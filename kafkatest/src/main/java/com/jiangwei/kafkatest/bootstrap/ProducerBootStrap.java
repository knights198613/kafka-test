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
    private KafkaMsProducer producer = null;
    ApplicationContext context = null;
    int times = 10000;

    public static void main(String[] args) {
        ProducerBootStrap bootStrap = new ProducerBootStrap();
        bootStrap.doProduce();
    }

    public void doProduce() {
        context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        producer = (KafkaMsProducer)context.getBean("kafkaProducer");
        while (times > 0) {
            User user = new User();
            user.setId(times);
            user.setAge(times);
            user.setName("weijiang_"+String.valueOf(times));
            user.setEmail("weijiang_"+String.valueOf(times)+"@jd.com");
            System.out.println(user.toString());
            Future<RecordMetadata> metadataFuture = producer.sendMessage(user.toString());
            try {
                RecordMetadata recordMetadata = metadataFuture.get();
                String topic = recordMetadata.topic();
                int partition =  recordMetadata.partition();
                System.out.println("数据发送的topic为：" + topic+",发送至patition:"+partition);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            times--;
        }
    }
}
