package com.mhxh.proxyer.fake.command.base;

public abstract class LocalBaseCommand extends BaseCommand implements IType{


    public LocalBaseCommand(String pattern) {
        super(pattern);
    }

    @Override
    public int type() {
        return 1;
    }
}
