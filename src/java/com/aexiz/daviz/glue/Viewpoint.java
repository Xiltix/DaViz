package com.aexiz.daviz.glue;

public abstract class Viewpoint extends Locus{
	
	public static class Channel extends Viewpoint {
		
		public static final String CLIENT_PROPERTY_EDGEMODEL = "edgemodel";
		public static final String CLIENT_PROPERTY_FIRST_DIRECTED = "first_directed";
		
		public final Node from;
		public final Node to;
		
		float weight = Float.NaN;
		
		transient Network network;
		
		public Channel(Node from, Node to) {
			if (from == null || to == null) throw null;
			this.from = from;
			this.to = to;
		}
		
		public void clearWeight() {
			this.weight = Float.NaN;
		}
		
		public void setWeight(float weight) {
			this.weight = weight;
		}
		
		/**
		 *  Boolean function to check the presence of weight
		 * @return presence of weight
		 */
		public boolean hasWeight() {
			return weight == weight;
		}
		/**
		 *  Function that directly returns the value of weight as a float
		 * @return float of Weight
		 */
		public float getWeight() {
			return weight;
		}
		
		public boolean equals(Object obj) {
			if (obj instanceof Channel) {
				return equals((Channel) obj);
			}
			return false;
		}
		
		public boolean equals(Channel other) {
			if (other == null) return false;
			return other.from.equals(from) && other.to.equals(to);
		}
		
		public int hashCode() {
			return 31 * from.hashCode() + to.hashCode();
		}
		
		@Override
		public String toString() {
			return from.getLabel() + "-" + to.getLabel();
		}
		
	}

	
	// This class should actually be "process", but because Java already has a
	// built-in class called Process, we can not name it so. Next time: prefix
	// model classes with a letter, like they do in Swing.
	public static class Node extends Viewpoint {
		/**
		 * The position of the node on the X-axis
		 */
		public static final String CLIENT_PROPERTY_POSITION_X = "node_pos_x";
		/**
		 * The position of the node on the Y-axis
		 */
		public static final String CLIENT_PROPERTY_POSITION_Y = "node_pos_y";
		public static final String CLIENT_PROPERTY_NODEMODEL = "nodemodel";
		
		private String label;
		
		// Transient fields
		transient Network network;
		
		// Haskell dependencies
		transient int hId;
		
		// Temporary fields
		transient boolean marked;
		
		public Node() {
		}

		public Node(String label) {
			setLabel(label);
		}
		/**
		 * Set the label/name of a node
		 * @param label The desired label/name of the node 
		 */
		public void setLabel(String label) {
			this.label = label;
		}
		/**
		 * Returns a string value for the label/name of the node
		 * @return The label/name of the node if it is present
		 */
		public String getLabel() {
			return this.label == null ? "" : this.label;
		}
		
		@Override
		public String toString() {
			return this.label != null ? getLabel() : super.toString();
		}
		
	}
	
}
