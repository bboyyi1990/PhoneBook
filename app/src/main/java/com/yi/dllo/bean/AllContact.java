package com.yi.dllo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dllo on 16/1/13.
 */
public class AllContact {
    private List<String> father;
    private List<List<SonInfo>> son;


    public AllContact() { //类的构造方法,在构造方法里直接初始化属性
        father = new ArrayList<>();
        son = new ArrayList<>();


    }


    public int getFatherCount() {
        return father.size();
    }

    public String FatherInfo(int position) {
        return father.get(position);
    }

    public int getSonCount(int fatherPosition) {
        return son.get(fatherPosition).size();
    }

    public List<SonInfo> getSonInfo(int position) {
        return son.get(position);
    }

    public void Info(SonInfo sonInfo) {
        String first = sonInfo.getName().substring(0, 1).toUpperCase();
        for (int i = 0; i < father.size(); i++) {
            if (first.equals(father.get(i))) {
                son.get(i).add(sonInfo);
                return;
            }
        }
        father.add(first);
        List<SonInfo> list = new ArrayList<>();
        list.add(sonInfo);
        son.add(list);
    }


}
