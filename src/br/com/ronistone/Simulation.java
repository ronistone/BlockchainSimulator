package br.com.ronistone;

import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    private List<Miner> miners;
    private Oracle oracle;
    private Statistics statistics;

    public Simulation(long numOfMiners){
        statistics = new Statistics();
        oracle = new Oracle(statistics);
        NetworkBuilder networkBuilder = new NetworkBuilder(oracle);
        miners = networkBuilder.buildMiners(numOfMiners);
    }


    public void run(long steps){
        assert steps > 0;

        List<Miner> hasMine;

        long lastMine = 0;
        for(long i=1; i <= steps;i++){

            hasMine = miners.stream()
                        .parallel()
                        .filter(miner -> miner.mine(oracle))
                        .collect(Collectors.toList());
            if(!hasMine.isEmpty()){
                statistics.addStepToMine((i - lastMine));
                oracle.recalculateDifficult((i - lastMine));
//                System.out.println("Miners has mine: steps to mine - " + (i - lastMine));
//                hasMine.forEach(miner -> {
//                    System.out.println(miner.getId() + ": I Mine -> " + miner.getChain().getHeight());
//                });
                lastMine = i;
            }
            long finalSteps = i;
            miners.forEach(miner -> miner.propagate(finalSteps));
            if(i%10000==0){
                System.out.println("step " + i + "...");
            }
        }
        statistics.printStatistics();

    }


}
