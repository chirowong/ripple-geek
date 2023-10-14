package com.rippleinfo.geek.callback.chatbot.processor;

import com.dingtalk.open.app.api.models.bot.ChatbotMessage;

public interface MessageProcessor {
    void process(ChatbotMessage message);
}
