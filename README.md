# proxyer
# 快乐西游的框架，可以自定义开发很多功能


### 物理环境：

  ### 路由器：构建内网
  ### 电脑A：需要有线网卡（接外网），无线网卡（接内网）
  ### 电脑B：接入内网
  
  #### 路由器IP：49.232.73.* 
  #### 电脑A的IP对接内网需要配置ip为49.232.73.205
  #### 电脑B连入路由器，使电脑A与B处于同一个网域下可以互相访问
  
  
### 软件环境：
   #### 1.启动java包，启动java成功后将通过电脑A代理B的网路数据,自动抓鬼等功能就是再这里进行数据伪装
   #### 2.电脑A访问http://49.232.73.205/GG.txt 看到的是游戏的宣传内容(#95#R更新公告#85#G★★★★★★★纯属单机娱乐,禁止外发商业使用,谢谢)，说明电脑A能够访问外网服务器地址正常
   #### 3.电脑B访问http://49.232.73.205/GG.txt 看到Java服务器端的宣传内容（IF YOU SEE IT， IT WORKS!）
   #### 4.电脑B上启动游戏当队长，队长的背包第一个格子需要放飞行符，最下面一排（第四排）背包的第一个格子需要放境外飞行棋用于抓境外鬼
   #### 5.电脑A开号进入B的队伍
   #### 6.浏览器访问：http://127.0.0.1/local/catch-ghost 如果浏览器返回success，表明已经准备好进行自动抓鬼
   #### 7.电脑B开个按键精灵，找到好友列表的按钮，Alt+F这个功能，把鼠标移动上去，不断点击好友列表按钮，最好间隔0.5秒以上，就可以完成自动抓鬼
   
