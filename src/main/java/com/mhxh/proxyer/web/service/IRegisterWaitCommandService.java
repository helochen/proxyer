package com.mhxh.proxyer.web.service;

public interface IRegisterWaitCommandService {

    void flyToMap(String mapId);

    void flyToSect(String sect);

    void flyToJiangNanMap();

    void useFlagFlyToMap(String itemIdx, String mapId);

    void catchGhost();

    void moveTo(int x, int y);

    void buyFlyTicket(int num);

    void finishQinglongTask();

    void getQinglongTask();
}
