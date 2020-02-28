package com.aexiz.daviz.simulation.algorithm.wave.tarry;

import com.aexiz.daviz.simulation.algorithm.information.state.AbstractAlgorithmState;
import com.aexiz.daviz.simulation.algorithm.information.state.PropertyVisitor;
import com.aexiz.daviz.simulation.viewpoint.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TarryState extends AbstractAlgorithmState {
    boolean hasToken;
    PropertyVisitor state;
    List<Channel> neighbors;

    public TarryState(boolean hasToken, PropertyVisitor state, List<Channel> neighbors) {
        this.hasToken = hasToken;
        this.state = state;
        this.neighbors = neighbors;
    }

    public TarryState() {
    }

    @Override
    public String toString() {
        return "(" + hasToken + "," + state + "," + neighbors + ")";
    }

    public void setHasToken(boolean hasToken) {
        this.hasToken = hasToken;
    }

    public void setState(PropertyVisitor state) {
        this.state = state;
    }

    public void setNeighbors(List<Channel> neighbors) {
        this.neighbors = neighbors;
    }

    public boolean isWithToken() {
        return hasToken;
    }

    public PropertyVisitor getState() {
        return state;
    }

    public List<Channel> getNeighbors() {
        return new ArrayList<>(neighbors);
    }

    public boolean hasNeighbors() {
        return neighbors.size() > 0;
    }

    public boolean isInitiator() {
        return state instanceof TarryInitiator;
    }

    @Override
    public void makeProperties() {
        properties = Map.of(
                "Has token?", String.valueOf(hasToken),
                "State", state,
                "Neighbors", makeNodesProperty(neighbors)
        );
    }
}
