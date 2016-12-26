package com.jiangwei.kafkatest.bootstrap;

import com.jiangwei.kafkatest.msconsumer.AbstractMsConsumer;
import com.jiangwei.kafkatest.msconsumer.impl.KafkaMsConsumerRuner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc: 消费端启动类
 */
public class ConsumerBootStrap {
    private ApplicationContext context;

    public static void main(String[] args) {
        ConsumerBootStrap consumerBootStrap = new ConsumerBootStrap();
        consumerBootStrap.doConsumer();
    }

    public void doConsumer() {
        context = new ClassPathXmlApplicationContext("classpath:spring.xml");
        KafkaMsConsumerRuner consumerRuner = (KafkaMsConsumerRuner) context.getBean("kafkaMsConsumer");

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
        for(int i=1; i>0; i--) {
            fixedThreadPool.execute(consumerRuner);
        }
    }
}
