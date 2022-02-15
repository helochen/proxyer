package com.mhxh.proxyer.web.service.impl;

import com.mhxh.proxyer.fake.FakeCommandRegisterFactory;
import com.mhxh.proxyer.tcp.game.constants.MapConstants;
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
    public void flyToSect(String sect) {
        String serialNo = MapConstants.NAME_TO_SERIAL_NO.get("1001");
        if (StringUtils.hasText(sect) && StringUtils.hasText(serialNo)) {
            registerFactory.registerFlyToSectByName(serialNo, sect);
        }
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
        registerFactory.registerCatchGhost();
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


}
