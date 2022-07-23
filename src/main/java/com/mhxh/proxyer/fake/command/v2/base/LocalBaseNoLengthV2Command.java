package com.mhxh.proxyer.fake.command.v2.base;

import com.mhxh.proxyer.decode.EncryptDictionary;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.Charset;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/22
 * @project proxyer
 **/
public abstract class LocalBaseNoLengthV2Command extends LocalBaseV2Command {

    public LocalBaseNoLengthV2Command(String pattern) {
        super(pattern);
    }

    public LocalBaseNoLengthV2Command(String header, String pattern) {
        super(header, pattern);
    }

    @Override
    public String format(String time, String verify) {
        String format = innerFormat(time);
        String result = EncryptDictionary.encodeEncryptString(format);
        final byte[] gbkBytes = result.getBytes(Charset.forName("GBK"));
        return getHeader() + ByteBufUtil.hexDump(gbkBytes);
    }
}
