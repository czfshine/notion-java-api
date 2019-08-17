package cn.czfshine.notion.model;

import lombok.Data;

@Data
public class CollectionViewBlock extends Block {

    private View view;
    private Collection collection;
}
