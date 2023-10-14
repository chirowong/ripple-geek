package com.rippleinfo.geek.callback.chatbot.processor;

import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.dingtalk.open.app.api.models.bot.MessageContent;
import com.rippleinfo.geek.config.OpenAiConfig;
import com.rippleinfo.geek.model.CompletionRequest;
import com.rippleinfo.geek.model.TestMessage;
import com.rippleinfo.geek.service.RobotGroupMessagesService;
import com.rippleinfo.geek.service.RobotMessagesService;
import com.rippleinfo.geek.util.http.LimitedSizeMessageMap;
import com.rippleinfo.geek.util.http.OpenAiUtil;
import com.rippleinfo.geek.util.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TextMessageProcessor implements MessageProcessor {

    public static final LimitedSizeMessageMap roleMessageMap = new LimitedSizeMessageMap(10);

    public void process(ChatbotMessage message) {
        String conversationType = message.getConversationType();
        MessageContent text = message.getText();
        switch (conversationType) {
            case "1": // 单聊
                if (text != null) {
                    String msg = text.getContent();
                    log.info("receive bot message from user={}, msg={}", message.getSenderId(), msg);
                    TestMessage testMessage = new TestMessage();
                    testMessage.setRole("user");
                    testMessage.setContent(msg);
                    roleMessageMap.addMessage(message.getSenderId(),testMessage);
                    String userId = message.getSenderStaffId();
                    try {
                        //发送机器人消息
                        RobotMessagesService robotMessagesService = SpringUtils.getBean(RobotMessagesService.class);
                        OpenAiConfig openAiConfig = SpringUtils.getBean(OpenAiConfig.class);
                        CompletionRequest completionRequest = new CompletionRequest();
                        completionRequest.setModel(openAiConfig.getModel());
                        List<TestMessage> messageList = roleMessageMap.get(message.getSenderId());
                        completionRequest.setMessages(messageList);
                        String response = OpenAiUtil.getTextByAi(openAiConfig.getChatUrl(), completionRequest);
                        TestMessage testMessageAssistant = new TestMessage();
                        testMessageAssistant.setRole("assistant");
                        testMessageAssistant.setContent(response);
                        roleMessageMap.addMessage(message.getSenderId(),testMessageAssistant);
                        robotMessagesService.send(userId, response);
                    } catch (Exception e) {
                        log.error("send group message by robot error:" + e.getMessage(), e);
                    }
                }
                break;
            case "2": // 群聊
                if (text != null) {
                    String msg = text.getContent();
                    log.info("receive bot message from user={}, msg={}", message.getSenderId(), msg);
                    TestMessage testMessage = new TestMessage();
                    testMessage.setRole("user");
                    testMessage.setContent(msg);
                    roleMessageMap.addMessage(message.getSenderId(),testMessage);
                    String openConversationId = message.getConversationId();
                    try {
                        RobotGroupMessagesService robotGroupMessagesService = SpringUtils.getBean(RobotGroupMessagesService.class);
                        OpenAiConfig openAiConfig = SpringUtils.getBean(OpenAiConfig.class);
                        CompletionRequest completionRequest = new CompletionRequest();
                        completionRequest.setModel(openAiConfig.getModel());
                        List<TestMessage> messageList = roleMessageMap.get(message.getSenderId());
                        completionRequest.setMessages(messageList);
                        String response = OpenAiUtil.getTextByAi(openAiConfig.getChatUrl(), completionRequest);
                        TestMessage testMessageAssistant = new TestMessage();
                        testMessageAssistant.setRole("assistant");
                        testMessageAssistant.setContent(response);
                        roleMessageMap.addMessage(message.getSenderId(),testMessageAssistant);
                        //发送机器人消息
                        robotGroupMessagesService.send(openConversationId, response);
                    } catch (Exception e) {
                        log.error("send group message by robot error:" + e.getMessage(), e);
                    }
                }
                break;
            default:
        }
    }
}
