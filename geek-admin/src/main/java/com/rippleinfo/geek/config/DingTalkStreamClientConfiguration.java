package com.rippleinfo.geek.config;

import com.aliyun.dingtalkrobot_1_0.Client;
import com.dingtalk.open.app.api.OpenDingTalkClient;
import com.dingtalk.open.app.api.OpenDingTalkStreamClientBuilder;
import com.dingtalk.open.app.api.callback.DingTalkStreamTopics;
import com.dingtalk.open.app.api.security.AuthClientCredential;
import com.rippleinfo.geek.callback.ai.AIGraphPluginCallbackListener;
import com.rippleinfo.geek.callback.chatbot.ChatBotCallbackListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ansel.wang
 */
@Configuration
public class DingTalkStreamClientConfiguration {

    @Value("${app.appKey}")
    private String clientId;
    @Value("${app.appSecret}")
    private String clientSecret;

    /**
     * 配置OpenDingTalkClient客户端并配置初始化方法(start)
     *
     * @param chatBotCallbackListener
     * @param aiGraphPluginCallbackListener
     * @return
     * @throws Exception
     */
    @Bean(initMethod = "start")
    public OpenDingTalkClient configureStreamClient(@Autowired ChatBotCallbackListener chatBotCallbackListener,
                                                    @Autowired AIGraphPluginCallbackListener aiGraphPluginCallbackListener) throws Exception {
        // init stream client
        return OpenDingTalkStreamClientBuilder.custom()
                //配置应用的身份信息, 企业内部应用分别为appKey和appSecret, 三方应用为suiteKey和suiteSecret
                .credential(new AuthClientCredential(clientId, clientSecret))
                //注册机器人回调
                .registerCallbackListener(DingTalkStreamTopics.BOT_MESSAGE_TOPIC, chatBotCallbackListener)
                //注册graph api回调
                .registerCallbackListener(DingTalkStreamTopics.GRAPH_API_TOPIC, aiGraphPluginCallbackListener).build();
    }

    @Bean
    public Client robotClient() throws Exception{
        com.aliyun.teaopenapi.models.Config config = new com.aliyun.teaopenapi.models.Config();
        config.protocol = "https";
        config.regionId = "central";
        return new Client(config);
    }
}