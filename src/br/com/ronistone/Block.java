package br.com.ronistone;

import java.util.UUID;

public class Block {

    private UUID id;
    private long height;
    private Block lastBlock;

    public Block(Block lastBlock) {
        this.id = UUID.randomUUID();
        this.lastBlock = lastBlock;
        this.height = lastBlock.getHeight() + 1;
    }

    public Block() {
        this.id = UUID.randomUUID();
        this.height = 1;
    }

    public UUID getId() {
        return id;
    }

    public long getHeight() {
        return height;
    }

    public Block getLastBlock() {
        return lastBlock;
    }
}