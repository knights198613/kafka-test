package com.jiangwei.kafkatest.msproducer;

import org.apache.kafka.clients.producer.Callback;

import java.util.concurrent.Future;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc: 消息系统的生产接口
 */
public interface MsProducer <T>{
    /**
     * 发送消息的接口
     * @param message
     * @return
     */
    public Future<? extends T> sendMessage(Object message);

    /**
     * 异步发送消息，
     * @param message
     * @param callback
     * @return
     */
    public Future<? extends T> sendMessage(Object message, Callback callback);


}
