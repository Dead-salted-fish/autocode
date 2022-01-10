package com.lld.autocode.system.entity;


import com.lld.autocode.utils.BaseAnnotation;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/7 10:17
 */
@Data
public class MenuInfo extends BaseAnnotation {
    private Long id;

    private String title;

    private String menuName;

    private String menuType;

    private Long parentMenuId;

    private String routerPath;

    private String componentPath;

    private Integer sort;

    private String ban;

    private String remark;

    private List<MenuInfo> children;

}
