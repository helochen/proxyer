package com.mhxh.proxyer.web.controller;

import com.mhxh.proxyer.fake.FakeCommandV2RegisterManager;
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

    @Autowired
    private FakeCommandV2RegisterManager manager;

    @GetMapping("goto")
    public String gotoXY(@RequestParam("x") int x, @RequestParam("y") int y, @RequestParam("id") String id) {
        directCommandService.gotoXY(x, y, id);
        return "success";
    }

    @GetMapping("change-map")
    public String changeMap(@RequestParam("dst") int dst, @RequestParam("id") String id) {
        directCommandService.takeMoney(dst, id);
        return "success";
    }

    @GetMapping("catch-ghost")
    public String catchGhost(@RequestParam("id") String id) {
        directCommandService.catchGhost(id);
        return "success";
    }

    @GetMapping("fight-ghost")
    public String fightGhost(@RequestParam("id") String id) {
        return directCommandService.fightGhost(id);
    }

    @GetMapping("auto-ghost")
    public String autoGhost(@RequestParam("id") String id) {
        return directCommandService.autoGhost(id);
    }
}
