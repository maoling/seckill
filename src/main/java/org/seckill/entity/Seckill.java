/*
* Copyright (c) 2014 Javaranger.com. All Rights Reserved.
*/
package org.seckill.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Desc ϵͳ��Ʒ���ʵ����
 * @Author feizi
 * @Date 2016/6/4 19:02
 * @Package_name com.feizi.entity
 */
public class Seckill implements Serializable{

    private static final long serialVersionUID = -7147150587493857431L;

    //��ɱ��Ʒid
    private long seckillId;

    //��Ʒ����
    private String name;

    //�������
    private int number;

    //��ɱ��ʼʱ��
    private Date startTime;

    //��ɱ����ʱ��
    private Date endTime;

    //����ʱ��
    private Date createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                '}';
    }
}