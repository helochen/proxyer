package com.mhxh.proxyer.tcp.game.task.bean;

import com.mhxh.proxyer.tcp.game.task.ITaskBean;

public abstract class AbstractTaskBean implements ITaskBean {


    private final long expired = System.currentTimeMillis();
    /**
     * 任务是否完成
     */
    protected volatile boolean finish = false;

    protected int taskType = 0;
    /**
     * 地图的中文名
     */
    private String targetMapName;

    /**
     * 地图ID
     */
    private String mapId;

    private final Integer[] xy = new Integer[2];

    /**
     * 怪NPC的中文名
     */
    private String npcName;

    /**
     * 序列号
     */
    private String serialNo;

    /**
     * id标识码
     */
    private String id;

    @Override
    public void initNpcName(String npc) {
        this.npcName = npc;
    }

    @Override
    public void initNpcTargetMapName(String targetMapName) {
        this.targetMapName = targetMapName;
    }

    @Override
    public ITaskBean initNpcXY(int x, int y) {
        xy[0] = x;
        xy[1] = y;
        return this;
    }

    @Override
    public String getNpcName() {
        return npcName;
    }

    @Override
    public ITaskBean setSerialNo(String serialNo) {
        this.serialNo = serialNo;
        return this;
    }

    @Override
    public ITaskBean setMapId(String mapId) {
        this.mapId = mapId;
        return this;
    }

    @Override
    public ITaskBean setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return this.npcName != null && this.npcName.equals(obj.toString());
    }

    @Override
    public String toString() {
        return npcName;
    }

    @Override
    public String getMapName() {
        return targetMapName;
    }

    @Override
    public String getMapId() {
        return mapId;
    }

    @Override
    public String getSerialNo() {
        return serialNo;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public int getX() {
        return xy[0];
    }

    @Override
    public int getY() {
        return xy[1];
    }

    @Override
    public int getTaskType() {
        return taskType;
    }

    @Override
    public void finish() {
        finish = true;
    }

    @Override
    public boolean isFinish() {
        if (finish) {
            logger.info("抓鬼DEBUG信息：DEBUG命令完成抓鬼任务...");
        }
        boolean timeOut = System.currentTimeMillis() - expired > 60000L;
        if (timeOut) {
            logger.info("抓鬼DEBUG信息：时间过期导致完成抓鬼任务....");
        }
        return finish || timeOut;
    }
}
