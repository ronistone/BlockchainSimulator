package br.com.ronistone;


public class Main {

    public static void main(String[] args) {

        Simulation simulation = new Simulation(100);
        simulation.run((long) 10000);
    }
}
