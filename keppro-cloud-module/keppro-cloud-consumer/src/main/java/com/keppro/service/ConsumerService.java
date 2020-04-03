package com.keppro.service;

import com.keppro.response.ObjectRestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("keppro-provider")
@RequestMapping("/provider/provider")
public interface ConsumerService {
    @GetMapping("/get")
    ObjectRestResponse post();
}
