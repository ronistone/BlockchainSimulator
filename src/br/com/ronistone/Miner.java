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
    private Boolean needPropagate;
    private Statistics statistics;
    private long lastStepPropagate;

    public Miner(long power, Statistics statistics) {
        this.id = UUID.randomUUID();
        this.power = power;
        this.chain = null;
        this.pairs = new HashSet<>();
        this.needPropagate = false;
        this.statistics = statistics;
        this.lastStepPropagate = Long.MIN_VALUE;
    }

    public Boolean mine(Oracle oracle) {
        Optional<Block> lastBlock = oracle.iWillMine(power, chain);
        lastBlock.ifPresent( block -> {
            chain = block;
            needPropagate = true;
        });
        return lastBlock.isPresent();
    }

    public void propagate(long step){
        if(needPropagate && step > lastStepPropagate){
            pairs.forEach(miner -> miner.receiveChain(chain, step));
            needPropagate = false;
            lastStepPropagate = step;
        }
    }

    public void receiveChain(Block newChain, long step){

        if(chain == null || newChain.getHeight() > chain.getHeight()){
            chain = newChain;
            needPropagate = true;
//            System.out.println("I receive a new chain with height=" + chain.getHeight());
        } else if(newChain.getHeight() == chain.getHeight() && !chain.equals(newChain)){
//            System.out.println("We Have a fork!!!");
            statistics.addBlockCollision(chain, newChain, step);
        }

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
