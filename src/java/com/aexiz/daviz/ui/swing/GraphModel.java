package com.aexiz.daviz.ui.swing;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.ChangeListener;

public interface GraphModel {
	
	public interface NodeModel {
		
		public void addChangeListener(ChangeListener l);
		
		public void removeChangeListener(ChangeListener l);
		/**
		 * Sets the X coordinate of the node
		 * @param x A float value for the X coordinate 
		 * 
		 */
		public void setX(float x);
		/**
		 * Sets the delta X coordinate of the node
		 * @param dx A float value for the X coordinate 
		 */
		public void setDeltaX(float dx);
		/**
		 * Returns the value of the current X coordinate of the node
		 * @return The value of DeltaX + X as a float
		 */
		public float getX();
		/**
		 * Returns the value of the current X coordinate of the node
		 * @return The value of just X as a float
		 * 
		 */
		public float getXWithoutDelta();
		/**
		 * Sets the Y coordinate of the node
		 * @param y A float value for the X coordinate 
		 * 
		 */
		public void setY(float y);
		/**
		 * Sets the delta Y coordinate of the node
		 * @param dy A float value for the y coordinate 
		 * 
		 */
		public void setDeltaY(float dy);
		/**
		 * Returns the value of the current Y coordinate of the node
		 * @return The value of DeltaY + Y as a float
		 * 
		 */
		public float getY();
		/**
		 * Returns the value of the current Y coordinate of the node without the Delta value
		 * @return The value of just Y as a float
		 */
		public float getYWithoutDelta();
		
		public GraphModel getParent();
		/**
		 * Checks if the node is currently pressed by the user
		 * @return boolean value if the node is currently pressed
		 */
		public boolean isPressed();
		/**
		 * Changes the boolean value of the class if the node is currently pressed
		 * @param pressed Is the node currently being pressed by the user?
		 */
		public void setPressed(boolean pressed);
		
		public void validate();

		/**
		 * Sets the name/label for a node
		 * @param label the exact label or node you want to give a node
		 */
		public void setLabel(String label);
		/**
		 * Retrieves the name/label of a node
		 * @return returns a string value of the name/label of the node
		 */
		public String getLabel();
		/**
		 * Checks if 2 nodes have equal coordiantes
		 * @return boolean value if the nodes are overlapping or not
		 */
		public boolean isOverlapping();
		
		public void remove();
		/**
		 * Sets the incoming or the from node for an edge
		 * @param node The label/node of the incoming node
		 */
		public void addIncomingNode(String node);
		/**
		 * Removes the incoming or the from node for an edge
		 * @param node The label/name of the incoming node
		 */
		public void removeIncomingNode(String node);
		/**
		 * Retrieves the label of the incoming node
		 * @return An arraylist of string of the incoming node
		 */
		public ArrayList<String> getIncomingNodes();
		/**
		 * Sets the outgoing or the to node for an edge
		 * @param node The label/node of the outgoing node
		 */
		public void addOutgoingNode(String node);
		/**
		 * Removes the outgoing or the to node for an edge
		 * @param node The label/node of the outgoing node
		 */
		public void removeOutgoingNode(String node);
		/**
		 * Retrieves the label of the outgoing node
		 * @return An arraylist of string of the outgoing node
		 */
		public ArrayList<String> getOutgoingNodes();
		
	}
	
	public interface EdgeModel {
		
		public void addChangeListener(ChangeListener l);
		
		public void removeChangeListener(ChangeListener l);
		/**
		 * Checks if the edge is directed or not
		 * @return boolean value if the edge is directed or not
		 */
		public boolean isDirected();
		/**
		 * Sets the direction of an edge
		 * @param directed Sets if the edge is directed or not
		 */
		public void setDirected(boolean directed);
		/**
		 * Sets the from node for the edge
		 * @return The starting node an edge is coming from
		 */
		public NodeModel getFrom();
		/**
		 * Sets the to node for the edge
		 * @return The end node an edge is going to
		 */
		public NodeModel getTo();
		
		public GraphModel getParent();
		
		public void validate();
		
		public void remove();
		
	}
	
	interface ModeEventListener extends EventListener {
		
		void modeChanged(EventObject e);
		
	}
	
	public void addChangeListener(ChangeListener l);
	
	public void removeChangeListener(ChangeListener l);
	
	public void addModeEventListener(ModeEventListener l);
	
	public void removeModeEventListener(ModeEventListener l);
	/**
	 * Creates a node with 2 params, this does NOT add it into the graph automatically
	 * @param x float value for the x coordinate 
	 * @param y float value for the y coordinate
	 * @return returns a node in NodeModel format does NOT ADD it into the graph
	 */
	public NodeModel createNode(float x, float y);
	/**
	 * Creates an edge with 2 params, this does NOT add it into the graph automatically
	 * @param from
	 * @param to
	 * @return
	 */
	public EdgeModel createEdge(NodeModel from, NodeModel to);
	/**
	 * Returns the number of nodes present in a graph, not all nodes may be valid
	 * @return The number of nodes in a graph
	 */
	public int getNodeCount();
	/**
	 * Precisly find a node at an index
	 * @param index Int value of the index of the node you are looking for
	 * @return The node at that index as NodeModel
	 */
	public NodeModel getNode(int index);
	/**
	 * All the nodes present in the graph, not the int value
	 * @return An array of all the nodes as a Nodemodel Array
	 */
	public NodeModel[] getNode();
	/**
	 * Returns a random node present in the graph
	 * @return a random node present in the graph
	 */
	public NodeModel getRandomNode();
	/**
	 * Adds a valid node into the graph/network
	 * @param n node of Nodemodel 
	 */
	public void addNode(NodeModel n);
	/**
	 * Removes an exact node out of the graph
	 * @param n The node you would like removed
	 */
	public void removeNode(NodeModel n);
	/**
	 * The number of edges present in the graph
	 * @return int value of the number of edges in the graph
	 */
	public int getEdgeCount();
	/**
	 * Returns a certain edge at a given index
	 * @param index The index of the edge from an Arraylist of edges
	 * @return returns an exact edge
	 */
	public EdgeModel getEdge(int index);
	/**
	 * Addes an valid edge to the network, an invalid will throw an expection
	 * @param e The edge to be added to the network
	 */
	public void addEdge(EdgeModel e);
	/**
	 * Removes an edge from the Network
	 * @param e The exact edge that needs to be removed
	 */
	public void removeEdge(EdgeModel e);
	/**
	 * Retrieves all edges present in the network
	 * @return returns all edges as an edge array
	 */
	public EdgeModel[] getEdge();
	/**
	 * Returns only valid nodes present in the network
	 * @return returns all valid nodes as an edge array
	 */
	public NodeModel[] getValidNode();
	/**
	 * Retrieves only and all valid edges in the network
	 * @return returns all valid edges as an array
	 */
	public EdgeModel[] getValidEdge();
	
	public void setSnapToGrid(boolean snap);
	
	public boolean isSnapToGrid();
	
	static final int MODE_SELECTION = 0;
	static final int MODE_VERTEX = 1;
	static final int MODE_EDGE = 2;
	static final int MODE_ERASE = 3;
	
	public void setEditMode(int mode);
	
	public int getEditMode();
	
	public void setTemporaryNode(NodeModel n);
	
	public NodeModel getTemporaryNode();
	
	public NodeModel findNearestValidNodeToTemporary();
	
	public NodeModel findNearestValidNode(float fx, float fy);
	
	public void clearTemporaryNode();
	
	public void setTemporaryEdge(EdgeModel e);
	
	public EdgeModel getTemporaryEdge();
	
	public void clearTemporaryEdge();
	
	public void validate();
	/**
	 * Clears the entire network frame of any nodes and edges.
	 */
	public void clear();
	/**
	 * Set the zoom level of the network frame
	 * @param zoom float value of zoom, negative is zoom out, positive is zoom in
	 */
	public void setZoomLevel(float zoom);
	/**
	 * Retrieves current zoom level
	 * @return float value of current zoom level, 0.0 is default
	 */
	public float getZoomLevel();

	public void setReadOnly(boolean read);
	
	public boolean isReadOnly();
}
