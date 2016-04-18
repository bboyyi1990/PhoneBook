package com.yi.dllo.activity;

/**
 * Created by dllo on 16/1/19.
 */
public class UpDataLog {


    /*1月19日更新记录
     LogFragment 获取 通话记录内容 移动到适配器中
     实现通话记录 列表按钮跳转 Activity 动画
     联系人界面-所有界面 设置 自定义 组件索引栏
     BaseActivity 自定义 Activity 待完善
     */

    /*1月20日更新记录
    Message 页面添加 recyclerview 组件
     */

    /*
    1月21日
    添加3个实体类获取短信内容添加到 Message 页面

     */

    /*
    1月22日更新记录
    用自定义接口实现 Message 界面 recyclerView 组件监听事件
    Message 界面 menu 加入发送短信选项 点击弹出 dialog 编辑信实现发送效果
     */

    /*
    1月23日更新记录
    联系人 免费界面 获取数据过程 移至适配器中
    开一个新线程 使数据库写入和读取 有一个时间差 实现短信界实时刷新(位置在 message dialog 积极按钮中)
    !!! 还存在 一个联系人因为号码格式不同而变成陌生人的 BUG
    */

    /*1月26日更新记录
     message 页面recyclerView 布局点击联系人内容可以跳转到 Person 页面显示联系人短信内容
     同时 发送系统广播 广播弹出Notification通知

     选中局部变量 command + Alt + F 使变成全局变量
     */

    /* 1月27日更新记录

    Person 界面 点击发送按钮发出信息, recyclerView 实现实时更新

     */

    /*1月28日更新记录
    增加 message 页面 调用 Startjump 自定义方法的形参 传送 message 页面的适配器.
      person 界面 接收这个适配器 调用 notifychanged 方法使得 message 界面也实时刷新
      person界面标题栏设置 menu 点击延迟发送 弹出 Dialogu 输入时间 利用 Service 实现延时发送效果,存在 BUG只能发送一次,再次延迟发送会崩
     */

    /*1月29日更新记录
      修复延时发送短信BUG  利用 Person 界面的动态广实现刷新 UI 界面
      PersonActivity 界面 输入信息内容跳转出页面实现使用 Sharedpreference请量级数据库保存数据功能 跳转回之后会 把数据库里的内容直接设置到 eidttext 上


     */

}
