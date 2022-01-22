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

    @RequestMapping("fly-sect")
    public String testFlyToSect(@RequestParam("sect") String sect) {
        registerWaitCommandService.flyToSect(sect);
        return "success";
    }

    @RequestMapping("fly-jiangnan")
    public String testFlyJiangNan() {
        registerWaitCommandService.flyToJiangNanMap();
        return "success";
    }

    @RequestMapping("fly-flag")
    public String flayFlag(@RequestParam("itemIdx") String itemIdx, @RequestParam("mapId") String mapId) {
        registerWaitCommandService.useFlagFlyToMap(itemIdx, mapId);
        return "success";
    }

    /**
     * 走门派飞向抓鬼
     * @return
     */
    @RequestMapping("catch-ghost")
    public String catchGhost() {
        registerWaitCommandService.catchGhost();
        return "success";
    }
}
