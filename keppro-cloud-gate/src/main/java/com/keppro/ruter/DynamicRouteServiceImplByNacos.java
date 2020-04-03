package com.keppro.ruter;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.logging.Logger;

@Slf4j
@Component
public class DynamicRouteServiceImplByNacos {


    @Value("${nacos.dataId}")
    private String dataId;
    @Value("${nacos.group}")
    private String group;
    @Value("${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;


    @Autowired
    private DynamicRouteServiceImpl dynamicRouteService;

    @Bean
    public String routeServiceInit() {
        log.info("-------------------------------------------------------------------------------");
        log.info("dataId:{}", dataId);
        log.info("group:{}", group);
        log.info("serverAddr:{}", serverAddr);
        dynamicRouteByNacosListener(dataId, group, serverAddr);
        return "success";
    }


    /**
     * 监听Nacos Server下发的动态路由配置
     *
     * @param dataId
     * @param group
     */
    public void dynamicRouteByNacosListener(String dataId, String group, String serverAddr) {
        try {
            if (group == null || group == "") {
                log.info("路由group配置为null");
                return;
            }
            if (dataId == null || dataId == "") {
                log.info("路由dataId配置为null");
                return;
            }
            ConfigService configService = NacosFactory.createConfigService(serverAddr);
            String content = configService.getConfig(dataId, group, 5000);
            log.info("nacos初始化监听,{}", content);

            try {
                updateRouters(content);
            } catch (Exception e) {
                log.error("更新配置出错:", e);
            }

            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    try {
                        updateRouters(configInfo);
                    } catch (Exception e) {
                        log.error("更新配置出错:", e);
                    }
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("初始化nacos监听出错:", e);
        }
    }


    private void updateRouters(String configInfo) {
        List<RouteDefinition> gatewayRouteDefinitions = JSONObject.parseArray(configInfo, RouteDefinition.class);
        for (RouteDefinition routeDefinition : gatewayRouteDefinitions) {
            log.info("遍历:" + routeDefinition.toString());
            dynamicRouteService.update(routeDefinition);
        }
    }

}