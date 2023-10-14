package com.rippleinfo.geek.callback.chatbot.processor;

import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.rippleinfo.geek.service.RobotFileMessagesService;
import com.rippleinfo.geek.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileMessageProcessor implements MessageProcessor {

    public void process(ChatbotMessage message) {

        try {
            String downloadUrl = SpringUtils.getBean(RobotFileMessagesService.class).download(message.getContent());
            String fileName = message.getContent().getFileName();
            log.error(downloadUrl);
            log.error(fileName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // String conversationType = message.getConversationType();
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
