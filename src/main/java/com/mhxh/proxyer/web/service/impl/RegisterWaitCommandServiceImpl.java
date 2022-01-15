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
    private MapConstants mapConstants;

    @Resource
    private FakeCommandRegisterFactory registerFactory;

    @Override
    public void flyToMap(String mapId) {
        String serialNo = mapConstants.CODE_TO_SERIAL_NO.get(mapId);

        if (StringUtils.hasText(serialNo)) {
            registerFactory.registerFlyTicketItemFlyToMap(serialNo);
        }
    }
}
