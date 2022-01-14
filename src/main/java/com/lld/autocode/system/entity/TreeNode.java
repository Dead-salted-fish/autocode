package com.lld.autocode.system.entity;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2022/1/13 17:04
 */
@Data
public class TreeNode {
    private Boolean selectable;
    private Boolean checkable;
    private Boolean disableCheckbox;
    private Boolean disabled;
    private Boolean isLeaf;
    private Long key;
    private String title;
    private String value;
    private Object scopedSlots;
    private List<TreeNode> children;
}
