package com.jiangwei.kafkatest.msproducer;

import javax.security.auth.callback.Callback;
import java.util.concurrent.Future;

/**
 * Created by weijiang
 * Date: 2016/11/25
 * Desc:
 */
public class AbstractMsProducer implements MsProducer {

    public Future sendMessage(Object message) {
        return null;
    }

    public Future sendMessage(Object message, Callback callback) {
        return null;
    }
}
