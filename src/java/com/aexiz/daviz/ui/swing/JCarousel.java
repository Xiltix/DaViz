package com.aexiz.daviz.ui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComponent;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.aexiz.daviz.ui.swing.plaf.CarouselUI;
import com.aexiz.daviz.ui.swing.plaf.basic.BasicCarouselUI;

// JList only has HORIZONTAL_FLOW, but we need only horizontal items
public class JCarousel extends JComponent {

	private static final long serialVersionUID = -2476657651802981334L;
	
	static final String UICLASSID = "CarouselUI";
	
	int counter;
	
	static {
		UIDefaults def = UIManager.getDefaults();
		if (def.get(UICLASSID) == null)
			def.put(UICLASSID, BasicCarouselUI.class.getName());
	}

	private ListModel<?> listModel;
	private ListSelectionModel listSelectionModel;
	private CarouselCellRenderer listCellRenderer;
	
	protected ListDataListener listDataListener;
	protected ListSelectionListener listSelectionListener;
	
	private transient Handler handler;
	
	public JCarousel() {
		counter = 0;
		setOpaque(true);
		setCellRenderer(new DefaultCarouselCellRenderer());
		setModel(new DefaultListModel<>());
		setSelectionModel(new DefaultListSelectionModel());
		updateUI();
	}
	
	private Color selectionBackground;
	
	public void setSelectionBackground(Color color) {
		Color old = selectionBackground;
		selectionBackground = color;
		firePropertyChange("selectionBackground", old, color);
		repaint();
	}
	
	public Color getSelectionBackground() {
		return selectionBackground;
	}
	
	private Color selectionForeground;
	
	public void setSelectionForeground(Color color) {
		Color old = selectionForeground;
		selectionForeground = color;
		firePropertyChange("selectionForeground", old, color);
		repaint();
	}
	
	public Color getSelectionForeground() {
		return selectionForeground;
	}
	
	public void addListSelectionListener(ListSelectionListener listener) {
		listenerList.add(ListSelectionListener.class, listener);
	}
	
	public void removeListSelectionListener(ListSelectionListener listener) {
		listenerList.remove(ListSelectionListener.class, listener);
	}
	
	protected void fireSelectionValueChanged(int firstIndex, int lastIndex, boolean isAdjusting) {
		Object[] listeners = listenerList.getListenerList();
		ListSelectionEvent e = null;

		
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ListSelectionListener.class) {
				if (e == null)
					e = new ListSelectionEvent(this, firstIndex, lastIndex, isAdjusting);
				((ListSelectionListener) listeners[i+1]).valueChanged(e);
			}
		}
	}
	
	public void setModel(ListModel<?> model) {
		ListModel<?> oldModel = listModel;
		
		if (oldModel != null) {
			oldModel.removeListDataListener(listDataListener);
			listDataListener = null;
		}
		listModel = model;
		if (model != null) {
			listDataListener = createListDataListener();
			model.addListDataListener(listDataListener);
		}
		if (oldModel != model) {
			firePropertyChange("model", oldModel, model);
		}
	}
	
	public ListModel<?> getModel() {
		return listModel;
	}
	
	public void setSelectionModel(ListSelectionModel model) {
		ListSelectionModel oldModel = listSelectionModel;
		if (oldModel != null) {
			oldModel.removeListSelectionListener(listSelectionListener);
			listSelectionListener = null;
		}
		listSelectionModel = model;
		if (model != null) {
			listSelectionListener = createListSelectionListener();
			model.addListSelectionListener(listSelectionListener);
		}
		if (oldModel != model) {
			firePropertyChange("selectionModel", oldModel, model);
		}
	}
	
	public ListSelectionModel getSelectionModel() {
		return listSelectionModel;
	}
	
	public void setSelectedIndex(int index) {
		ListModel<?> model = getModel();
		if (model == null || index >= model.getSize()) {
			return;
		}
		ListSelectionModel selModel = getSelectionModel();
		if (selModel == null) return;
		selModel.setSelectionInterval(index, index);
	}
	
	public int getSelectedIndex() {
		return getMinSelectionIndex();
	}
	
	public Object getSelectedValue() {
		int i = getSelectedIndex();
		if (i < 0) return null;
		ListModel<?> model = getModel();
		if (model == null) return null;
		return model.getElementAt(i);
	}
	
	public int getValueCount() {
		ListModel<?> model = getModel();
		if (model == null) return 0;
		return model.getSize();
	}
	
	public Object getValue(int index) {
		ListModel<?> model = getModel();
		if (model == null) return null;
		return model.getElementAt(index);
	}
	
	public int getAnchorSelectionIndex() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return -1;
		return model.getAnchorSelectionIndex();
	}
	
	public int getLeadSelectionIndex() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return -1;
		return model.getLeadSelectionIndex();
	}
	
	public int getMinSelectionIndex() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return -1;
		return model.getMinSelectionIndex();
	}
	
	public int getMaxSelectionIndex() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return -1;
		return model.getMaxSelectionIndex();
	}
	
	public boolean isSelectedIndex(int index) {
		ListSelectionModel selModel = getSelectionModel();
		if (selModel == null) return false;
		return selModel.isSelectedIndex(index);
	}
	
	public boolean isSelectionEmpty() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return true;
		return model.isSelectionEmpty();
	}
	
	public void clearSelection() {
		ListSelectionModel model = getSelectionModel();
		if (model == null) return;
		model.clearSelection();
	}
	
	public void ensureIndexIsVisible(int index) {
		Rectangle cellBounds = getCellBounds(index, index);
		if (cellBounds != null)
			scrollRectToVisible(cellBounds);
	}
	
	public void setCellRenderer(CarouselCellRenderer renderer) {
		CarouselCellRenderer old = listCellRenderer;
		listCellRenderer = renderer;
		firePropertyChange("cellRenderer", old, renderer);
	}
	
	public CarouselCellRenderer getCellRenderer() {
		return listCellRenderer;
	}
	
	public void updateUI() {
		setUI((CarouselUI) UIManager.getUI(this));
		CarouselCellRenderer renderer = getCellRenderer();
		if (renderer instanceof Component) {
			SwingUtilities.updateComponentTreeUI((Component) renderer);
		}
	}
	
	public void setUI(CarouselUI ui) {
		super.setUI(ui);
	}
	
	public CarouselUI getUI() {
		return (CarouselUI) ui;
	}
	
	public String getUIClassID() {
		return UICLASSID;
	}
	
	public Rectangle getCellBounds(int from, int to) {
		CarouselUI ui = getUI();
		return ui != null ? ui.getCellBounds(this, from, to) : null;
	}
	
	public int locationToIndex(Point location) {
		CarouselUI ui = getUI();
		return ui != null ? ui.locationToIndex(this, location) : -1;
	}
	
	public Point indexToLocation(int index) {
		CarouselUI ui = getUI();
		return ui != null ? ui.indexToLocation(this, index) : null;
	}
	public void incrementCounter() {
		counter++;
	}
	public void decrementCounter() {
		counter--;
	}
	
	
	protected ListDataListener createListDataListener() {
		return getHandler();
	}
	
	protected ListSelectionListener createListSelectionListener() {
		return getHandler();
	}
	
	private Handler getHandler() {
		if (handler == null) {
			handler = new Handler();
		}
		return handler;
	}
	
	class Handler implements ListDataListener, ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			revalidate();
			repaint();
			fireSelectionValueChanged(e.getFirstIndex(), e.getLastIndex(), e.getValueIsAdjusting());
		}
		public void contentsChanged(ListDataEvent e) {
			listSelectionModel.clearSelection(); // TODO
			repaint();
		}
		public void intervalAdded(ListDataEvent e) {
			listSelectionModel.clearSelection(); // TODO
			repaint();
		}
		public void intervalRemoved(ListDataEvent e) {
			listSelectionModel.clearSelection(); // TODO
			repaint();
		}
	}
	
}
