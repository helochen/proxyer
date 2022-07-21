package com.mhxh.proxyer.fake.command.v2.base;

import com.mhxh.proxyer.decode.EncryptDictionary;
import com.mhxh.proxyer.fake.command.v1.base.LocalBaseCommand;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.Charset;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/21
 * @project proxyer
 **/
public abstract class LocalBaseV2Command extends LocalBaseCommand {

    private String header;

    public LocalBaseV2Command(String header, String pattern) {
        super(pattern);
        this.header = header;
    }

    @Override
    public String format(String time, String verify) {
        String format = innerFormat(time);
        String result = EncryptDictionary.encodeEncryptString(format);
        final byte[] gbkBytes = result.getBytes(Charset.forName("GBK"));
        final String gbk = ByteBufUtil.hexDump(gbkBytes);
        return Integer.toHexString(gbkBytes.length + 4) + this.header + gbk;
    }

    protected abstract String innerFormat(String time);
}
