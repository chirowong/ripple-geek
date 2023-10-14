package com.rippleinfo.geek.callback.chatbot.processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageProcessorFactory {
    private final static Map<String, MessageProcessor> processorMap = new ConcurrentHashMap<>();
    public static MessageProcessor getProcessor(String type) {
        synchronized (MessageProcessorFactory.class) {
            if (!processorMap.containsKey(type)) {
                processorMap.put(type, createProcessor(type));
            }
        }
        return processorMap.get(type);
    }

    private static MessageProcessor createProcessor(String type) {
        MessageProcessor messageProcessor = null;
        switch (type) {
            case "text":
                messageProcessor = new TextMessageProcessor();
                break;
            case "file":
                messageProcessor = new FileMessageProcessor();
                break;
            default:
                throw new UnsupportedOperationException("Unsupported message type: " + type);
        }
        return messageProcessor;
    }
}
