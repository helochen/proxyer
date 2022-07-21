package com.mhxh.proxyer.fake.command.v2.local;

import com.mhxh.proxyer.fake.command.v2.base.LocalBaseV2Command;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/21
 * @project proxyer
 **/
public class LocalQueryFriendListCommand extends LocalBaseV2Command {

    public LocalQueryFriendListCommand(String header, String pattern) {
        super(header, pattern);
    }

    @Override
    protected String innerFormat(String time) {
        return String.format(getPattern(), time);
    }
}
