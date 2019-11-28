package br.com.ronistone;

import java.util.List;
import java.util.stream.Collectors;

public class Simulation {

    private List<Miner> miners;
    private Oracle oracle;
    private Statistics statistics;

    public Simulation(long numOfMiners){
        statistics = new Statistics();
        oracle = new Oracle();
        NetworkBuilder networkBuilder = new NetworkBuilder(oracle);
        miners = networkBuilder.buildMiners(numOfMiners);
    }


    public void run(long steps){
        assert steps > 0;

        List<Miner> hasMine;

        long lastMine = steps;
        while(steps > 0){

            hasMine = miners.stream()
                        .parallel()
                        .filter(miner -> miner.mine(oracle))
                        .collect(Collectors.toList());
            if(!hasMine.isEmpty()){
                statistics.addStepToMine((lastMine - steps));
                System.out.println("Miners has mine: steps to mine - " + (lastMine - steps));
                hasMine.forEach(miner -> System.out.println(miner.getId() + ": I Mine -> " + miner.getChain().getHeight()));
                lastMine = steps;
            }
            steps--;
        }
        statistics.printStatistics();

    }


}
