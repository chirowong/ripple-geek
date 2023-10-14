package com.rippleinfo.geek.model;
import lombok.Data;

import java.util.List;

@Data
public class CompletionRequest {
    private String model;
    private List<TestMessage> messages;

}

