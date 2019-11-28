package br.com.ronistone;


public class Main {

    public static void main(String[] args) {

        Simulation simulation = new Simulation(2);
        simulation.run((long) 10e3);
    }
}
