package cn.czfshine.notion.model.block;

import lombok.Data;

@Data
public class EmbedBlock extends AUrlBlock {
    private boolean blockFullWidth;
    private int blockHeight;
    private boolean blockPageWidth;
    private boolean blockPreserveScale;
    private int blockWidth;
}
