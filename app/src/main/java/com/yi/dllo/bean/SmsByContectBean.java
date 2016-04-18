package com.yi.dllo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/1/21.
 */
public class SmsByContectBean {
    String name, number;
    List<SingleSmsBean> singleSmsBeans;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<SingleSmsBean> getSingleSmsBeans() {
        return singleSmsBeans;
    }

    public void setSingleSmsBeans(List<SingleSmsBean> singleSmsBeans) {
        this.singleSmsBeans = singleSmsBeans;
    }

    //自定义添加短信方法
    public void addSingleSmsBean(SingleSmsBean singleSmsBean) {
        if (singleSmsBeans == null) { //这里注意判断条件是集合为空 不是类为空
            singleSmsBeans = new ArrayList<>();
        }
        //短信加到末尾
        singleSmsBeans.add(0, singleSmsBean);
    }

    //自定义添加短信方法
    public void addUser(SingleSmsBean singleSmsBean) {
        if (singleSmsBeans == null) { //这里注意判断条件是集合为空 不是类为空
            singleSmsBeans = new ArrayList<>();
        }
        //短信加到末尾
        singleSmsBeans.add(singleSmsBean);

    }

    //获得当前短信条数
    public int getSize() {
        return singleSmsBeans.size();
    }

}
