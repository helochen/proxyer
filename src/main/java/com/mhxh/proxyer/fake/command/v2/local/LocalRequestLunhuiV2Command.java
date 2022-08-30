package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseNoLengthV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/8/30
 * @project proxyer
 **/
public class LocalRequestLunhuiV2Command extends LocalBaseNoLengthV2Command {

    private final String idx;
    private final String id;

    public LocalRequestLunhuiV2Command(String idx, String id, String startHeader, String endHeader) {
        super(startHeader, endHeader, LocalSendV2CommandRuleConstants.COMMEND_PROTO_GET_HEADER,
                LocalSendV2CommandRuleConstants.ROLE_REQUEST_FIGHT_WITH_LUNHUI_GBK_V2);
        this.idx = idx;
        this.id = id;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), idx, id, idx, time);
    }
}
