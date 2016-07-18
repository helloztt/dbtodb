package com.huotu.test;

import java.util.List;

/**
 * Created by helloztt on 2016/5/4.
 */
public class DPModel {
    private String name;
    //价值
    private int value;
    //拥有的资源
    private List<Integer> nums;
    //要提供的资源
    private List<Integer> provoidNums;

    public DPModel(String name, int value, List<Integer> nums) {
        this.name = name;
        this.value = value;
        this.nums = nums;
    }

    @Override
    protected DPModel clone() throws CloneNotSupportedException {
        return new DPModel(this.name,this.value,this.nums);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Integer> getNums() {
        return nums;
    }

    public void setNums(List<Integer> nums) {
        this.nums = nums;
    }

    public List<Integer> getProvoidNums() {
        return provoidNums;
    }

    public void setProvoidNums(List<Integer> provoidNums) {
        this.provoidNums = provoidNums;
    }
}
