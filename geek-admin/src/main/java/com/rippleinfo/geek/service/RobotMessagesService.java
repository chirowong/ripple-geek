package com.rippleinfo.geek.service;


import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOHeaders;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTORequest;
import com.aliyun.dingtalkrobot_1_0.models.BatchSendOTOResponse;
import com.aliyun.tea.TeaException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author ansel.wang
 */
@Slf4j
@Service
public class RobotMessagesService {
    private final Client robotClient;
    private final AccessTokenService accessTokenService;

    @Value("${robot.code}")
    private String robotCode;

    @Autowired
    public RobotMessagesService(AccessTokenService accessTokenService, Client robotClient) {
        this.accessTokenService = accessTokenService;
        this.robotClient = robotClient;
    }

    /**
     * send message to group with openConversationId
     *
     * @param userId userId
     * @return messageId
     * @throws Exception e
     */
    public String send(String userId, String text) throws Exception {
        BatchSendOTOHeaders batchSendOTOHeaders = new BatchSendOTOHeaders();
        batchSendOTOHeaders.setXAcsDingtalkAccessToken(accessTokenService.getAccessToken());

        BatchSendOTORequest batchSendOTORequest = new BatchSendOTORequest();
        batchSendOTORequest.setMsgKey("sampleText");
        batchSendOTORequest.setRobotCode(robotCode);
        JSONObject msgParam = new JSONObject();
        msgParam.put("content", "java-getting-start say : " + text);
        batchSendOTORequest.setMsgParam(msgParam.toJSONString());
        batchSendOTORequest.setUserIds(java.util.Arrays.asList(userId));

        try {
            BatchSendOTOResponse batchSendOTOResponse = robotClient.batchSendOTOWithOptions(batchSendOTORequest,
                    batchSendOTOHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            if (Objects.isNull(batchSendOTOResponse) || Objects.isNull(batchSendOTOResponse.getBody())) {
                log.error("RobotGroupMessagesService_send orgGroupSendWithOptions return error, response={}",
                        batchSendOTOResponse);
                return null;
            }
            return batchSendOTOResponse.getBody().getProcessQueryKey();
        } catch (TeaException e) {
            log.error("RobotGroupMessagesService_send orgGroupSendWithOptions throw TeaException, errCode={}, " +
                    "errorMessage={}", e.getCode(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("RobotGroupMessagesService_send orgGroupSendWithOptions throw Exception", e);
            throw e;
        }
    }
}
