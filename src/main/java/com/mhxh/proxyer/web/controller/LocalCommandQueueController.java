package com.mhxh.proxyer.web.controller;


import com.mhxh.proxyer.web.service.IRegisterWaitCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("local")
@RestController
public class LocalCommandQueueController {


    @Autowired
    private IRegisterWaitCommandService registerWaitCommandService;

    @RequestMapping("test-fly")
    public String testFlyToMap(@RequestParam("mapId") String mapId) {
        registerWaitCommandService.flyToMap(mapId);
        return "success";
    }
}
