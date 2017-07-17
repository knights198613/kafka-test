package com.jiangwei.kafkatest.msproducer;

import org.apache.kafka.clients.producer.Callback;

import java.util.concurrent.Future;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc: 生产端的抽象类
 */
public abstract class AbstractMsProducer implements MsProducer {

    public abstract Future sendMessage(Object message);

    public abstract Future sendMessage(Object message, Callback callback);


}
