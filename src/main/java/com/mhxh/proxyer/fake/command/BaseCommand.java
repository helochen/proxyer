package com.mhxh.proxyer.fake.command;


import lombok.Data;

@Data
public abstract class BaseCommand implements IFormatCommand{

    public BaseCommand(String pattern) {
        this.pattern = pattern;
    }

    private String pattern;

    private String time;

    private String verify;

}
