package com.mhxh.proxyer.web.controller;

import com.mhxh.proxyer.web.service.IRegisterDirectCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
@RestController
@RequestMapping("v2")
public class LocalCommandV2QueueController {

    @Autowired
    private IRegisterDirectCommandService directCommandService;

    @GetMapping("goto")
    public String gotoXY(@RequestParam("x")int x , @RequestParam("y")int y, @RequestParam("id")String id){
        directCommandService.gotoXY(x, y ,id);
        return "success";
    }

    @GetMapping("change-map")
    public String changeMap() {
        directCommandService.changeMap();
        return "success";
    }
}
