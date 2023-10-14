package com.rippleinfo.geek.util.http;

import com.rippleinfo.geek.model.TestMessage;

import java.util.*;

public class LimitedSizeMessageMap extends LinkedHashMap<String, List<TestMessage>> {
    private final int maxSize;

    public LimitedSizeMessageMap(int maxSize) {
        super(16, 0.75f, true); // Access order set to true for custom removal logic
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, List<TestMessage>> eldest) {
        List<TestMessage> list = eldest.getValue();
        if (list.size() > maxSize) {
            list.remove(0); // Remove the oldest list
        }
        return false; // Never automatically remove entries
    }

    public void addMessage(String key, TestMessage message) {
        List<TestMessage> list = get(key);
        if (list == null) {
            list = new LinkedList<>();
            put(key, list);
        }
        list.add(message);
    }

    public static void main(String[] args) {
        LimitedSizeMessageMap map = new LimitedSizeMessageMap(10);

        // 添加数据
        TestMessage message1 = new TestMessage();
        message1.setRole("User1");
        message1.setContent("hello");
        TestMessage message2 = new TestMessage();
        message1.setRole("User1");
        message1.setContent("howareyou");
        TestMessage message3 = new TestMessage();
        message1.setRole("User2");
        message1.setContent("Hi there!");

        map.addMessage("User1", message1);
        map.addMessage("User1", message2);
        map.addMessage("User2", message3);

        // 获取指定 key 的 List
        String keyToRetrieve = "User1";
        List<TestMessage> messageList = map.get(keyToRetrieve);

        if (messageList != null) {
            System.out.println("List for key " + keyToRetrieve + ":");
            for (TestMessage message : messageList) {
                System.out.println(message);
            }
        } else {
            System.out.println("List not found for key " + keyToRetrieve);
        }
    }
}
