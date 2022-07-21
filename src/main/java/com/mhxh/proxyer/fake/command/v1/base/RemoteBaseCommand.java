package com.mhxh.proxyer.fake.command.v1.base;

public abstract class RemoteBaseCommand extends LocalBaseCommand implements IType {

    public RemoteBaseCommand(String pattern) {
        super(pattern);
    }

    @Override
    public int type() {
        return 2;
    }
}
