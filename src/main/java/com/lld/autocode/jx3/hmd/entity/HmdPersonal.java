package com.lld.autocode.jx3.hmd.entity;

import com.lld.autocode.utils.BaseAnnotation;
import lombok.Data;

/**
 * @description:
 * @author: wzl
 * @date 2021/12/28 14:22
 */
@Data
public class HmdPersonal extends BaseAnnotation {
    private Long id;
    private String serverName;
    private String roleName;
    private String roleType;
    private String uid;
    private String reason;
    private String evaluate;
    private String remark;


}
