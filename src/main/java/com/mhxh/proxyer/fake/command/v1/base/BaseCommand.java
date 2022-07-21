package com.mhxh.proxyer.fake.command.v1.base;


import lombok.Data;

@Data
public abstract class BaseCommand implements IFormatCommand , IType{

    public BaseCommand(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    private String time;

    private String verify;

}
