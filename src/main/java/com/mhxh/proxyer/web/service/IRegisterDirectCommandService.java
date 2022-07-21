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

    void changeMap(int dst, String id);
}
