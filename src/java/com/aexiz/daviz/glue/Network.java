package com.aexiz.daviz.glue;

import java.io.Serializable;
import java.util.ArrayList;

import com.aexiz.daviz.sim.Set;
import com.aexiz.daviz.sim.Set.TSet;

import frege.prelude.PreludeBase.TTuple2;
import frege.run8.Lazy;
import frege.run8.Thunk;
/**
 * The network class which holds the actual network BEFORE being executed.
 * @author Hans-Dieter
 *
 */
public class Network{
	
	private ArrayList<Viewpoint.Node> processes = new ArrayList<Viewpoint.Node>();
	private ArrayList<Viewpoint.Channel> channels = new ArrayList<Viewpoint.Channel>();
	
	// Simulation ownership
	transient Simulation simulation;
	
	// Haskell dependence
	transient TSet<TTuple2<Integer, Integer>> hNetwork;
	/**
	 * Adds a valid node into the network.
	 * @param p The node which is going to be added into the network
	 * @return returns the same node added to the network
	 * @throws The current node is already in use by another network
	 */
	public Viewpoint.Node addNode(Viewpoint.Node p) {
		if (p.network != null) throw new Error("Process already owned by other network");
		p.network = this;
		if (processes.contains(p)) return p;
		processes.add(p);
		return p;
	}
	/**
	 * Adds a valid channel/edge into the network
	 * @param c A channel/edge inbetween two nodes to be added into the network
	 * @return Returns the same channel/edge added
	 * @throws The current channel is already in use by another network
	 */
	public Viewpoint.Channel addChannel(Viewpoint.Channel c) {
		if (c.network != null) throw new Error("Channel already owned by other network");
		c.network = this;
		int i = channels.indexOf(c);
		if (i == -1) {
			channels.add(c);
			return c;
		} else {
			return channels.get(i);
		}
	}
	/**
	 * Checks if a certain channel/edge is present inside the network
	 * @param from The starting node of the channel/edge 
	 * @param to The end node of the channel/edge
	 * @return A boolean value if the presence of that particular node is present
	 */
	public boolean hasChannel(Viewpoint.Node from, Viewpoint.Node to) {
		for (Viewpoint.Channel c : channels) {
			if (c.from == from && c.to == to) return true;
		}
		return false;
	}
	/**
	 * Returns all the valid node currently inside the network.
	 * @return returns all the nodes as a node array of all the nodes currently inside the network.
	 */
	public Viewpoint.Node[] getNodes() {
		return processes.toArray(new Viewpoint.Node[processes.size()]);
	}
	/**
	 * Returns all the valid channel/edges currently inside the network.
	 * @return returns all the channel/edges as a channel array of all the channel/edge currently inside the network.
	 */
	public Viewpoint.Channel[] getChannels() {
		return channels.toArray(new Viewpoint.Channel[channels.size()]);
	}
	
	public void makeUndirected() {
		// Symmetric closure, loop over copy to prevent CME
		for (Viewpoint.Channel c : getChannels()) {
			addChannel(new Viewpoint.Channel(c.to, c.from));
		}
	}
	
	public boolean isWeighted() {
		for (Viewpoint.Channel c : channels) {
			if (!c.hasWeight()) return false;
		}
		return true;
	}
	
	public boolean isStronglyConnected() {
		for (Viewpoint.Node n : processes) {
			n.marked = false;
		}
		if (processes.size() > 0) {
			Viewpoint.Node start = processes.get(0);
			floodFill(start);
		}
		for (Viewpoint.Node n : processes) {
			if (!n.marked) return false;
		}
		return true;
	}
	/**
	 * Finds and returns a certain node by the ID in the network
	 * @param hId The integer ID of the node that needs to be found inside the network
	 * @return The requested node by ID
	 * @throws No such node found
	 */
	public Viewpoint.Node getNodeById(int hId) {
		for (Viewpoint.Node p : processes) {
			if (p.hId == hId)
				return p;
		}
		throw new Error("Haskell processes out-of-sync");
	}
	private void floodFill(Viewpoint.Node node) {
		if (node.marked) return;
		node.marked = true;
		for (Viewpoint.Channel c : channels) {
			if (c.from == node)
				floodFill(c.to);
		}
	}

	void load() {
		// 1. Construct unique Integers for processes
		int last = 1;
		for (Viewpoint.Node p : processes) {
			p.hId = last++;
		}
		// 2. Construct set of edge tuples
		// 2.1. Empty set
		hNetwork = Set.<TTuple2<Integer, Integer>>emptyS().call();
		for (Viewpoint.Channel c : channels) {
			// 2.2. Construct one element
			Lazy<Integer> from = Thunk.lazy(c.from.hId);
			Lazy<Integer> to = Thunk.lazy(c.to.hId);
			TTuple2<Integer, Integer> ch = TTuple2.<Integer,Integer>mk(from, to);
			// 2.3. Add one element to set
			hNetwork = Set.addS(hNetwork, ch);
		}
	}


	
}
