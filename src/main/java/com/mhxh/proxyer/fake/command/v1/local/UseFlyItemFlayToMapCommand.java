package com.mhxh.proxyer.fake.command.v1.local;

import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;

/**
 * 使用飞行符的信息
 */

public class UseFlyItemFlayToMapCommand extends LocalBaseCommand {


    public UseFlyItemFlayToMapCommand(String pattern, String mapSerialId) {
        super(pattern);
        this.mapSerialId = mapSerialId;
    }

    private String mapSerialId;

    @Override
    public String format() {
        return String.format(super.getPattern(), super.getTime(), mapSerialId, super.getVerify());
    }

    @Override
    public String format(String time, String verify) {
        return String.format(super.getPattern(), time, mapSerialId, verify);
    }
}
