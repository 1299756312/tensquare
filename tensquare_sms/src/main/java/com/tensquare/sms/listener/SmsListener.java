package com.tensquare.sms.listener;

import com.tensquare.sms.util.AliyunSmsUtil;
import darabonba.core.exception.ClientException;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@Component
@RabbitListener(queues = "sms")
@Transactional
public class SmsListener {
     @Autowired
    AliyunSmsUtil aliyunSmsUtil;
    @Value("${aliyun.sms.signName}")
    private String signName;
    @Value("${aliyun.sms.templateCode}")
    private String templateCode;

    @RabbitHandler
    public void receiveSms(Map<String,String> message){
        System.out.println("手机号："+message.get("mobile"));
        System.out.println("验证码："+message.get("code"));
        try {
            aliyunSmsUtil.sendSms(message,templateCode,signName);
        }catch (ClientException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
