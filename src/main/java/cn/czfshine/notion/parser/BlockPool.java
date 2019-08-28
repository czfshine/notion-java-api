package cn.czfshine.notion.parser;

import cn.czfshine.notion.model.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Optional;

/**
 * 所有下载过的block
 */
public class BlockPool {
    private final HashMap<String, Block> allBlocks = new HashMap<>();
    private static BlockPool thePool;

    static {
        thePool = new BlockPool();
    }

    private BlockPool() {

    }

    public static BlockPool getThePool() {
        return thePool;
    }

    public void put(@NotNull Block block) {
        //todo version!!
        allBlocks.put(block.getId(), block);
    }

    @NotNull
    public Optional<Block> get(@NotNull String id) {
        return Optional.ofNullable(allBlocks.get(id));
    }


}
