package com.rippleinfo.geek.callback.chatbot.processor;

import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.dingtalk.open.app.api.models.bot.MessageContent;
import com.rippleinfo.geek.service.RobotGroupMessagesService;
import com.rippleinfo.geek.service.RobotMessagesService;
import com.rippleinfo.geek.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TextMessageProcessor implements MessageProcessor {
    public void process(ChatbotMessage message) {
        String conversationType = message.getConversationType();
        MessageContent text = message.getText();
        switch (conversationType) {
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
        }
    }
}
