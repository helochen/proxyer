package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseNoLengthV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/22
 * @project proxyer
 **/
public class LocalRequestCatchGhostV2Command extends LocalBaseNoLengthV2Command {

    private final String mapId;
    private final String no;
    private final String id;
    private final String idx;

    public LocalRequestCatchGhostV2Command(String mapId, String no, String id, String idx, String startHeader, String endHeader) {
        super(startHeader, endHeader, LocalSendV2CommandRuleConstants.COMMEND_PROTO_GET_HEADER,
                LocalSendV2CommandRuleConstants.ROLE_REQUEST_CATCH_GHOST_GBK_V2);
        this.mapId = mapId;
        this.no = no;
        this.id = id;
        this.idx = idx;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), mapId, no, id, idx, time);
    }
}
