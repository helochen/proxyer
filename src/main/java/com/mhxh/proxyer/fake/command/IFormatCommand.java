package com.mhxh.proxyer.fake.command;

public interface IFormatCommand extends IType {

    String format();

    String format(String time, String verify);

    @Override
    int type();
}
