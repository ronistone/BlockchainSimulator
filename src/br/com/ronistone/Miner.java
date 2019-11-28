package br.com.ronistone;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class Miner {

    private UUID id;
    private long power;
    private Block chain;
    private Set<Miner> pairs;

    public Miner(long power) {
        this.id = UUID.randomUUID();
        this.power = power;
        this.chain = null;
        this.pairs = new HashSet<>();
    }

    public Boolean mine(Oracle oracle){
        Optional<Block> lastBlock = oracle.iWillMine(power, chain);
        lastBlock.ifPresent( block -> chain = block );
        return lastBlock.isPresent();
    }



    public Block getChain() {
        return chain;
    }

    public Set<Miner> getPairs() {
        return pairs;
    }

    public void addPair(Miner miner) {
        if(pairs != null && !getId().equals(miner.getId())) {
            pairs.add(miner);
        }
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Miner)) return false;
        Miner miner = (Miner) o;
        return getId().equals(miner.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
