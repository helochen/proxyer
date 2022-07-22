package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;
import com.mhxh.proxyer.tcp.game.cmdfactory.LocalSendV2CommandRuleConstants;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/22
 * @project proxyer
 **/
public class LocalTalkToNpcV2Command extends LocalBaseV2Command {

    private int mpaId;

    private int no;

    private int idx;

    public LocalTalkToNpcV2Command(int mpaId , int no , int idx) {
        super(LocalSendV2CommandRuleConstants.ROLE_TALK_TO_NPC_GBK_V2);
        this.mpaId = mpaId;
        this.no = no;
        this.idx = idx;
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), mpaId, no, idx, time);
    }
}
