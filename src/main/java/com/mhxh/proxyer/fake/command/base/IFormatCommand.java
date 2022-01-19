package com.mhxh.proxyer.fake.command.base;

public interface IFormatCommand extends IType {

    String format();

    String format(String time, String verify);

    @Override
    int type();
}
