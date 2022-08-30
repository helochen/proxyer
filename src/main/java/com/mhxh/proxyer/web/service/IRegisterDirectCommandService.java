package com.mhxh.proxyer.web.service;


/**
 * file description:
 *
 * @author chenqi@sunwayword.com
 * @date 2022/7/20
 * @project proxyer
 **/
public interface IRegisterDirectCommandService {

    void gotoXY(int x, int y, String id);

    void takeMoney(int dst, String id);

    void catchGhost(String id);

    String fightGhost(String id);

    String autoGhost(String id);

    String autoLunhui(String id);
}
