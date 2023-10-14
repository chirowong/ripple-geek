package com.rippleinfo.geek.service;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dingtalkrobot_1_0.Client;
import com.aliyun.dingtalkrobot_1_0.models.*;
import com.aliyun.tea.TeaException;
import com.dingtalk.open.app.api.models.bot.MessageContent;
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
public class RobotFileMessagesService {
    private final Client robotClient;
    private final AccessTokenService accessTokenService;

    @Value("${robot.code}")
    private String robotCode;

    @Autowired
    public RobotFileMessagesService(AccessTokenService accessTokenService, Client robotClient) {
        this.accessTokenService = accessTokenService;
        this.robotClient = robotClient;
    }

    /**
     * send message to group with openConversationId
     *
     * @return messageId
     * @throws Exception e
     */
    public String download(MessageContent content) throws Exception {
        RobotMessageFileDownloadHeaders robotMessageFileDownloadHeaders = new RobotMessageFileDownloadHeaders();
        robotMessageFileDownloadHeaders.setXAcsDingtalkAccessToken(accessTokenService.getAccessToken());
        // 下载文件
        String downloadCode = content.getDownloadCode();
        RobotMessageFileDownloadRequest request = new RobotMessageFileDownloadRequest();
        request.setDownloadCode(downloadCode);
        try {
            RobotMessageFileDownloadResponse robotMessageFileDownloadResponse = robotClient.robotMessageFileDownloadWithOptions(request, robotMessageFileDownloadHeaders, new com.aliyun.teautil.models.RuntimeOptions());
            if (Objects.isNull(robotMessageFileDownloadResponse) || Objects.isNull(robotMessageFileDownloadResponse.getBody())) {
                log.error("RobotFileMessagesService_download robotMessageFileDownloadWithOptions return error, response={}",
                        robotMessageFileDownloadResponse);
                return null;
            }
            return robotMessageFileDownloadResponse.getBody().getDownloadUrl();
        } catch (TeaException e) {
            log.error("RobotFileMessagesService_download robotMessageFileDownloadWithOptions throw TeaException, errCode={}, " +
                    "errorMessage={}", e.getCode(), e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("RobotFileMessagesService_download robotMessageFileDownloadWithOptions throw Exception", e);
            throw e;
        }
    }
}
