package br.com.ronistone;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class Oracle {

    private double difficult;
    private Statistics statistics;

    public Oracle(Statistics statistics){
        generateDifficult();
        this.statistics = statistics;
    }

    public long calculateProcessPower(){
        return Constants.MIN_POWER + (long) (Math.random() * (Constants.MAX_POWER - Constants.MIN_POWER));
    }
    private void generateDifficult(){
        difficult = Constants.MIN_DIFFICULT + (long) (Math.random() * (Constants.MAX_DIFFICULT - Constants.MIN_DIFFICULT));
    }

    public List<Integer> calculatePairsToConnect(int actualMax) {
        Set<Integer> pairs = new HashSet<>();
        int numberOfPairs = Math.max(getRandomWithMax(actualMax), Math.min(actualMax, 1));

        while(pairs.size() < numberOfPairs) {
            pairs.add(getRandomWithMax(actualMax));
        }
        return new ArrayList<>(pairs);
    }

    private int getRandomWithMax(long maxValue){
        return (int) (Math.random() * (maxValue - 1));
    }

    public Optional<Block> iWillMine(long power, Block chain) {
        Random random = new Random();
        if(random.nextDouble() < ((double) power / difficult)){
            if(chain == null){
                return Optional.of(new Block());
            } else {
                return Optional.of(new Block(chain));
            }
        }

        return Optional.empty();
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void recalculateDifficult(long stepsBetweenMine) {
        difficult = difficult * ((double)Constants.STEPS_BETWEEN_MINE / ((double)stepsBetweenMine)) * ((double)Constants.STEPS_BETWEEN_MINE / ((double)stepsBetweenMine));
//        System.out.println("new Difficult: " + difficult);
    }
}
