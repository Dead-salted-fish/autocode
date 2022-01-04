package com.lld.autocode.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/29 14:45
 */
@Data
public class BaseAnnotation {

    private Integer version;
    private Long creator;
    private LocalDateTime createTime;
    private Long updateBy;
    private LocalDateTime updateTime;

    public void initBase() {
        this.version = 0;
        this.creator = this.updateBy;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();

    }

    public void initBase(Long userId) {
        this.version = 0;
        this.updateBy = userId;
        this.creator = userId;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public void updateBase(Integer version) {
        this.version = version+1;
        this.updateTime = LocalDateTime.now();
    }

    public void updateBase(Integer version,Long useid) {
        this.version = version+1;
        this.updateBy = useid;
        this.updateTime = LocalDateTime.now();
    }


}
