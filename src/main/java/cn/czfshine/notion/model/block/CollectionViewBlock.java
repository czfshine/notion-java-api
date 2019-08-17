package cn.czfshine.notion.model.block;

import cn.czfshine.notion.model.Collection;
import cn.czfshine.notion.model.View;
import lombok.Data;

@Data
public class CollectionViewBlock extends Block {

    private View view;
    private Collection collection;
}
