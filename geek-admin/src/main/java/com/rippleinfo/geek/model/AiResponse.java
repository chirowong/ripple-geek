package com.rippleinfo.geek.model;

import lombok.Data;

import java.awt.*;
import java.util.List;

@Data
public class AiResponse {
    private String id;
    private String object;
    private Long created;
    private String model;
    private List<AiChoice> choices;
    private Usage usage;

    @Data
    public static class AiChoice{
        private Long index;
        private AiMessage message;
        private String finish_reason;
    }

    @Data
    public static class AiMessage{
        private String role;
        private String content;
        private String name;
        private String functions;
        private String function_call;
    }

    @Data
    public static class Usage{
        private Integer prompt_tokens;
        private Integer total_tokens;
        private Integer completion_tokens;
    }

}
