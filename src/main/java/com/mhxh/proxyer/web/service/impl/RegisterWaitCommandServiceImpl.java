package com.mhxh.proxyer.web.service.impl;

import com.mhxh.proxyer.fake.FakeCommandRegisterFactory;
import com.mhxh.proxyer.tcp.game.constants.MapConstants;
import com.mhxh.proxyer.tcp.game.constants.SectMapConstants;
import com.mhxh.proxyer.web.patch.CatchGhostTaskPatch;
import com.mhxh.proxyer.web.service.IRegisterWaitCommandService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service
public class RegisterWaitCommandServiceImpl implements IRegisterWaitCommandService {


    @Resource
    private FakeCommandRegisterFactory registerFactory;

    @Override
    public void flyToMap(String mapId) {
        String serialNo = MapConstants.NAME_TO_SERIAL_NO.get(mapId);

        if (StringUtils.hasText(serialNo)) {
            registerFactory.registerFlyTicketItemFlyToMap(serialNo);
        }
    }

    @Override
    public String flyToSect(String sect) {
        if (SectMapConstants.SECT_NAMES.contains(sect)) {
            registerFactory.registerFlyToSectByName(sect);
            return "success";
        }
        return "fail";
    }

    @Override
    public void flyToJiangNanMap() {
        registerFactory.registerFlyToJiangNanMap();
    }

    @Override
    public void useFlagFlyToMap(String mapIdx, String mapId) {
        registerFactory.registerFlyFlagToMap(mapIdx, mapId);
    }

    @Override
    public void catchGhost() {
        registerFactory.registerCatchGhost(1);
    }

    @Override
    public void moveTo(int x, int y) {
        registerFactory.registerMoveTo(x, y);
    }

    @Override
    public void buyFlyTicket(int num) {
        registerFactory.registerBuyFlyTicket(num);
    }

    @Override
    public void finishQinglongTask() {
        registerFactory.registerFinishQinglongTask();
    }

    @Override
    public void getQinglongTask() {
        registerFactory.registerGetQinglongTask();
    }

    @Override
    public void catchGhostBlocked() {
        CatchGhostTaskPatch.getInstance().forceBlock();
    }

    @Override
    public void workForNpc(int type, int sum) {
        registerFactory.registerWorkForNpc(type, sum);
    }

    @Override
    public void buySystemItemHaiMa() {
        registerFactory.registerBuySystemItemHaiMa();
    }

    @Override
    public void clearAllCommand() {
        registerFactory.registerClearCommand();
    }


}
