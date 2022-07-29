package com.mhxh.proxyer.fake;

import org.springframework.stereotype.Component;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/27
 * @project proxyer
 **/
@Component
public class FakeCommandV2RegisterManager {

    private volatile String leaderId;

    public synchronized void setLeaderId(String leaderId) {
        this.leaderId = leaderId;
    }

    public synchronized String getLeaderId() {
        return leaderId;
    }
}
