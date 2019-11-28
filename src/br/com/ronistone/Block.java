package br.com.ronistone;

import java.util.UUID;

public class Block {

    private UUID id;
    private int height;
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

    public int getHeight() {
        return height;
    }

    public Block getLastBlock() {
        return lastBlock;
    }
}