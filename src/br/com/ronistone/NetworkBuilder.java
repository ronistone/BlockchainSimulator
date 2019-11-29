package br.com.ronistone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.LongStream;

public class NetworkBuilder {

    private List<Miner> miners;
    private Oracle oracle;

    public NetworkBuilder(Oracle oracle){
        this.oracle = oracle;
    }

    public List<Miner> buildMiners(long numOfMiners) {
        miners = new ArrayList<>();
        LongStream.range(0, numOfMiners)
            .forEach(i -> {
                        Miner miner = new Miner(oracle.calculateProcessPower(), oracle.getStatistics());
                        mountConnections(miner, oracle.calculatePairsToConnect(miners.size()));
                        miners.add(miner);
            }
        );

        ensureMinersIsConneted();
        return miners;
    }

    private void ensureMinersIsConneted() {

        Map<UUID, Boolean> visited = new HashMap<>();
        Optional<Miner> actual = Optional.of(miners.get(0));

        while(actual.isPresent() && visited.size() < miners.size()){
            visitePairs(visited, actual);
            actual = miners.stream()
                    .filter( miner -> !visited.containsKey(miner.getId()))
                    .findFirst();
            actual.ifPresent(
                    miner -> mountConnections(miner, oracle.calculatePairsToConnect(miners.size()))
            );
        }
    }

    private void visitePairs(Map<UUID, Boolean> visited, Optional<Miner> miner) {
        if(!miner.isPresent()){
            return;
        }

        visited.put(miner.get().getId(), true);

        miner.get().getPairs()
                .forEach(pair -> {
                    if(!visited.containsKey(pair.getId())){
                        visitePairs(visited, Optional.of(pair));
                    }
                });
    }

    private void mountConnections(Miner miner, List<Integer> calculatePairsToConnect) {
        calculatePairsToConnect
                .forEach( pair -> {
                    miners.get(pair).addPair(miner);
                    miner.addPair(miners.get(pair));
                });
    }

}
