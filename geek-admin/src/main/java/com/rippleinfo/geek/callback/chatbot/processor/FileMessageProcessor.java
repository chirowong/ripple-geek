package com.rippleinfo.geek.callback.chatbot.processor;

import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadRequest;
import com.aliyun.dingtalkrobot_1_0.models.RobotMessageFileDownloadResponse;
import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.dingtalk.open.app.api.models.bot.MessageContent;
import com.rippleinfo.geek.service.RobotMessagesService;
import com.rippleinfo.geek.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileMessageProcessor implements MessageProcessor {

    public void process(ChatbotMessage message) {
        // 下载文件
        MessageContent content = message.getContent();
        String downloadCode = content.getDownloadCode();

        Client robotClient = SpringUtils.getBean(Client.class);

        RobotMessageFileDownloadRequest request = new RobotMessageFileDownloadRequest();
        request.setDownloadCode(downloadCode);
        RobotMessageFileDownloadResponse response = null;
        try {
            response = robotClient.robotMessageFileDownload(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // String conversationType = message.getConversationType();
        log.error(response.toString());

        /*switch (conversationType) {
            case "1": // 单聊
                if (text != null) {
                    String msg = text.getContent();
                    log.info("receive bot message from user={}, msg={}", message.getSenderId(), msg);
                    String userId = message.getSenderStaffId();
                    try {
                        //发送机器人消息
                        RobotMessagesService robotMessagesService = SpringUtils.getBean(RobotMessagesService.class);
                        robotMessagesService.send(userId, "hello");
                    } catch (Exception e) {
                        log.error("send group message by robot error:" + e.getMessage(), e);
                    }
                }
                break;
            case "2": // 群聊
                if (text != null) {
                    String msg = text.getContent();
                    log.info("receive bot message from user={}, msg={}", message.getSenderId(), msg);
                    String openConversationId = message.getConversationId();
                    try {
                        RobotGroupMessagesService robotGroupMessagesService = SpringUtils.getBean(RobotGroupMessagesService.class);
                        //发送机器人消息
                        robotGroupMessagesService.send(openConversationId, "hello");
                    } catch (Exception e) {
                        log.error("send group message by robot error:" + e.getMessage(), e);
                    }
                }
                break;
            default:
        }*/
    }
}
