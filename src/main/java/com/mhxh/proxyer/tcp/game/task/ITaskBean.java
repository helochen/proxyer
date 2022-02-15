package com.mhxh.proxyer.tcp.game.task;

public interface ITaskBean {

    void initNpcName(String npc);

    void initNpcTargetMapName(String targetMapName);

    ITaskBean initNpcXY(int x, int y);

    String getNpcName();

    ITaskBean setSerialNo(String serialNo);

    ITaskBean setMapId(String mapId);

    ITaskBean setId(String id);

    String getMapName();

    String getMapId();

    String getSerialNo();

    String getId();

    int getX();

    int getY();

    int getTaskType();
}
