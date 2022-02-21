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
        return registerWaitCommandService.flyToSect(sect);
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
     *
     * @return
     */
    @RequestMapping("catch-ghost")
    public String catchGhost() {
        registerWaitCommandService.catchGhost();
        return "success";
    }

    /**
     * 测试下移动功能
     */
    @RequestMapping("move-to")
    public String moveTo(@RequestParam("x") int x, @RequestParam("y") int y) {
        registerWaitCommandService.moveTo(x, y);
        return "success";
    }

    /**
     * 测试购买物品
     */
    @RequestMapping("buy-fly")
    public String buyFlyTicket(@RequestParam("num") int num) {
        registerWaitCommandService.buyFlyTicket(num);
        return "success";
    }

    /**
     * 测试提交物品
     */
    @RequestMapping("finish-ql")
    public String finishQingLong() {
        registerWaitCommandService.finishQinglongTask();
        return "success";
    }

    /**
     * 领取青龙任务
     */
    @RequestMapping("get-ql")
    public String getQinglongTask() {
        registerWaitCommandService.getQinglongTask();
        return "success";
    }

    /**
     * 通知抓鬼被拦截任务
     */
    @RequestMapping("block")
    public String catchGhostTaskBlock() {
        registerWaitCommandService.catchGhostBlocked();
        return "block success";
    }

    /**
     * 打工一次的逻辑
     */
    @RequestMapping("work")
    public String workHard(@RequestParam(value = "type", defaultValue = "0") int type,
                           @RequestParam(value = "sum", defaultValue = "5000") int sum) {
        registerWaitCommandService.workForNpc(type, sum);
        return "success";
    }

    @RequestMapping("buy-haima")
    public String buyHaiMa() {
        registerWaitCommandService.buySystemItemHaiMa();
        return "success";
    }

    @RequestMapping("clear")
    public String clear() {
        registerWaitCommandService.clearAllCommand();
        return "success";
    }
}
