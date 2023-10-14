package com.rippleinfo.geek.callback.chatbot;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.app.api.callback.OpenDingTalkCallbackListener;
import com.dingtalk.open.app.api.models.bot.ChatbotMessage;
import com.rippleinfo.geek.callback.chatbot.processor.MessageProcessor;
import com.rippleinfo.geek.callback.chatbot.processor.MessageProcessorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 机器人消息回调
 *
 * @author ansel.wang
 */
@Slf4j
@Component
public class ChatBotCallbackListener implements OpenDingTalkCallbackListener<ChatbotMessage, JSONObject> {
    /**
     * https://open.dingtalk.com/document/orgapp/the-application-robot-in-the-enterprise-sends-group-chat-messages
     *
     * @param message
     * @return
     */
    @Override
    public JSONObject execute(ChatbotMessage message) {
        try {
            String msgType = message.getMsgtype();
            MessageProcessor messageProcessor = MessageProcessorFactory.getProcessor(msgType);
            messageProcessor.process(message);
        } catch (Exception e) {
            log.error("receive group message by robot error:" + e.getMessage(), e);
        }
        return new JSONObject();
    }
}