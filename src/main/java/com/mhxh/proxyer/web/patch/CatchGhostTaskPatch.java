package com.mhxh.proxyer.web.patch;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/2/17
 * @project proxyer
 **/
public class CatchGhostTaskPatch {

    private static CatchGhostTaskPatch instance;

    public static CatchGhostTaskPatch getInstance() {
        if (instance == null) {
            synchronized (CatchGhostTaskPatch.class) {
                if (instance == null) {
                    instance = new CatchGhostTaskPatch();
                }
            }
        }
        return instance;
    }

    private CatchGhostTaskPatch() {
    }

    private final long taskTimeOut = 30000L;

    private final AtomicLong time = new AtomicLong(0);

    private volatile AtomicInteger reset = new AtomicInteger(0);

    public long getTime() {
        return time.get();
    }

    public void setTime(long time) {
        this.time.set(time);
    }

    public void start() {
        this.time.set(System.currentTimeMillis());
    }

    public boolean timeout() {
        if (this.getTime() > 0 && System.currentTimeMillis() - this.getTime() > taskTimeOut) {
            this.setTime(System.currentTimeMillis());
            this.reset.incrementAndGet();
            return true;
        }
        return false;
    }

    public boolean isBlock() {
        return this.reset.get() > 0;
    }

    public void reset() {
        reset.set(0);
    }

    public void forceBlock() {
        this.reset.set(1);
    }

    public boolean jumpToAddGhostTask() {
        return this.reset.get() > 20;
    }
}
