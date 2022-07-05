package com.aexiz.daviz.glue;
/**
 * The simulation class which contains all the variables in it's final form,such as the network, the initiator and algorithm, in a valid format ready to be executed.
 * @author Hans-Dieter
 *
 */
public class Simulation {
	
	// Properties
	private Algorithm algorithm;
	private Assumption assumption;
	private Network network;
	
	// Transient fields
	private transient Execution execution;
	
	public Simulation() {
	}
	
	public Simulation(Algorithm alg) {
		setAlgorithm(alg);
	}
	/**
	 *  This function sets the algorithm that the Simulation is going to run, this needs to be set before running the simulation.
	 * 
	 * @param alg The algorithm that needs to be ran.
	 * @throws The algorithm chosen is invalid or not present.
	 */
	public void setAlgorithm(Algorithm alg) {
		algorithm = alg;
		assumption = alg.getAssumption();
		if (assumption == null) throw new Error("Invalid algorithm assumptions");
	}
	
	/**
	 * Returns the algorithm the currently selected algorithm inside the simulation.
	 * @return
	 */
	public Algorithm getAlgorithm() {
		if (algorithm == null) throw new Error("Algorithm is not set");
		return algorithm;
	}
	/**
	 * 
	 * @return
	 */
	public Assumption getAssumption() {
		if (assumption == null) throw new Error("Assumption is not set");
		return assumption;
	}
	/**
	 * Binds a network to the simulation class that will be ran by the simulation
	 * @param net A valid network with nodes and possibily edges.
	 * @throws Network is already being used by another simulation 
	 */
	public void setNetwork(Network net) {
		if (net.simulation != null) throw new Error("Network owned by other simulation");
		network = net;
		network.simulation = this;
	}
	/**
	 * Retrieves a valid network that is currently set to be ran in the simulation.
	 * @return A valid network with valid nodes and valid edges 
	 * @throws No network is present 
	 */
	public Network getNetwork() {
		if (network == null) throw new Error("Network is not set");
		return network;
	}
	
	/**
	 * Sets valid nodes as initiators in the simulation
	 * @param proc sets a valid node as the initiator
	 * @throws No algorithm has been set, set an algorithm first
	 * @throws The type of algorithm is not centralized
	 * @throws No network is present to set initiators on
	 * @throws The initiator's are not part of this network's simulation
	 */
	public void setInitiator(Viewpoint.Node proc) {
		if (assumption == null) throw new Error("No algorithm");
		if (!assumption.centralized_user) throw new Error("Algorithm is not centralized by user-input");
		if (network == null) throw new Error("Network is not set");
		if (proc.network != network) throw new Error("Process not owned by simulation");
		assumption.initiator = proc;
	}
	/**
	 * Retrieves if a initiator has been set in the simulation
	 * @return returns a boolean value if there are inititiators present in the program
	 * @throws No algorithm present, so no initiators had been set
	 */
	public boolean hasInitiator() {
		if (assumption == null) throw new Error("No algorithm");
		return assumption.centralized_user;
	}
	/**
	 * Returns the nodes that are set as initiators in the simulation by the network
	 * @return The nodes that are initiators
	 * @throws No algorithm has been set, set an algorithm first
	 * @throws The type of algorithm is not centralized
	 * @throws No network is present to set initiators on
	 */
	public Viewpoint.Node getInitiator() {
		if (assumption == null) throw new Error("No algorithm");
		if (!assumption.centralized_user) throw new Error("Algorithm is not centralized by user-input");
		if (network == null) throw new Error("Network is not set");
		return assumption.initiator;
	}
	
	/**
	 * Loads the simulation and network to be executed to be ran.
	 */
	public void load() {
		network.load();
		Configuration.InitialConfiguration ic = new Configuration.InitialConfiguration();
		ic.simulation = this;
		ic.load();
		execution = new Execution();
		execution.simulation = this;
		execution.configuration = ic;
		execution.loadFirst();
	}
	
	public Execution getExecution() {
		return execution;
	}
	
}
