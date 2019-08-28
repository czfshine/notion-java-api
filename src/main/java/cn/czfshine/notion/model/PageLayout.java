package cn.czfshine.notion.model;

/**
 * 页面布局.
 * page        ::= {block }
 * block       ::= column_list | other block
 * column_list ::= {column}
 * column      ::= {block}
 * <p>
 * 一棵树
 * column_list 是分叉点
 * column 是每个分叉出来的子节点
 * <p>
 * 每个column有个宽度比例 0～1
 */
public class PageLayout {

}
