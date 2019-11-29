package br.com.ronistone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Statistics {

    private List<Long> stepsToMine = new ArrayList<>();
    private Set<Long> stepsHaveFork = new HashSet<>();
    private Map<UUID, Boolean> blocksCollision = new HashMap<>();
    private Map<Long, AtomicLong> blocksCollisionByHeight = new HashMap<>();
    private long forks = 0;

    public void printStatistics(){
        printStepsStatistics();
        printForkStatistics();
        printConsensusStatistics();
    }

    private void printStepsStatistics() {
        System.out.println("Steps between block mine:");
        System.out.println("\tAverage: " + stepsToMine.stream().mapToDouble(Long::doubleValue).average().orElse(Double.NaN));
        System.out.println("\tMax: " + stepsToMine.stream().max(Long::compareTo).orElse(Long.MIN_VALUE));
        System.out.println("\tMin: " + stepsToMine.stream().min(Long::compareTo).orElse(Long.MAX_VALUE));
    }

    private void printForkStatistics(){
        System.out.println("Forks Statistics:");
        System.out.println("\tNumber of forks: " + forks);
        System.out.println("\tNumber of blocks forked: " + blocksCollision.size());
//        List<Long> collisionsHeight = new ArrayList<>();
//        blocksCollisionByHeight.keySet().forEach(key -> LongStream.range(0, blocksCollisionByHeight.get(key).get()).forEach(i -> collisionsHeight.add(key)));
        System.out.println("\tAverage: " + blocksCollisionByHeight.values().stream().mapToDouble(AtomicLong::doubleValue).average().orElse(Double.NaN));
        System.out.println("\tMax fork size: " + blocksCollisionByHeight.values().stream().mapToLong(a -> a.get()).max().orElse(Long.MIN_VALUE));
        System.out.println("\tMin fork size: " + blocksCollisionByHeight.values().stream().mapToLong(a -> a.get()).min().orElse(Long.MAX_VALUE));
    }

    private void printConsensusStatistics(){
        System.out.println("Consensus Statistics:");
        System.out.println("\tLast step with fork:" + stepsHaveFork.stream().max(Long::compareTo).orElse(0l));
    }

    public void addStepToMine(Long step) {
        stepsToMine.add(step);
    }

    public void addBlockCollision(Block a, Block b, long step){
        boolean containsA = blocksCollision.containsKey(a.getId());
        boolean containsB = blocksCollision.containsKey(b.getId());
        stepsHaveFork.add(step);
        if(!containsA || !containsB){
            forks++;

            blocksCollisionByHeight.putIfAbsent(a.getHeight(), new AtomicLong(0));
            blocksCollisionByHeight.putIfAbsent(b.getHeight(), new AtomicLong(0));
            if(!containsA) blocksCollisionByHeight.get(a.getHeight()).incrementAndGet();
            if(!containsB) blocksCollisionByHeight.get(b.getHeight()).incrementAndGet();
        }
        blocksCollision.put(a.getId(), true);
        blocksCollision.put(b.getId(), true);
    }

}
