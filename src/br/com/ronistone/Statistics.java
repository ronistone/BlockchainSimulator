package br.com.ronistone;

import java.util.ArrayList;
import java.util.List;

public class Statistics {

    private List<Long> stepsToMine = new ArrayList<>();

    public void printStatistics(){
        System.out.println("Steps between block mine:");
        System.out.println("\tMean: " + stepsToMine.stream().mapToDouble(Long::doubleValue).average().orElse(Double.NaN));
        System.out.println("\tMax: " + stepsToMine.stream().max(Long::compareTo).orElse(Long.MIN_VALUE));
        System.out.println("\tMin: " + stepsToMine.stream().min(Long::compareTo).orElse(Long.MAX_VALUE));
    }

    public void addStepToMine(Long step) {
        stepsToMine.add(step);
    }

}
