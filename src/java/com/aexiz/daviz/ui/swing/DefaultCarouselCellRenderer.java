package com.aexiz.daviz.ui.swing;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

public class DefaultCarouselCellRenderer extends JLabel implements CarouselCellRenderer {

	private static final long serialVersionUID = 6948963333208661930L;
	
	boolean loaded;
	Border selectedAndFocus;
	Border notSelectedAndFocus;
	Border notFocus;
	
	
	private void loadBordersFromListRenderer() {
		
		
		if (loaded) return;
		JList<Object> l = new JList<>();
		ListCellRenderer<Object> lcr = l.getCellRenderer();
		Component c = lcr.getListCellRendererComponent(l, "", 0, true, true);
		if (c instanceof JComponent) {
			selectedAndFocus = ((JComponent) c).getBorder();
		}
		c = lcr.getListCellRendererComponent(l, "", 0, false, true);
		if (c instanceof JComponent) {
			notSelectedAndFocus = ((JComponent) c).getBorder();
		}
		c = lcr.getListCellRendererComponent(l, "", 0, false, false);
		if (c instanceof JComponent) {
			notFocus = ((JComponent) c).getBorder();
		}
		loaded = true;
	}

	public Component getCarouselCellRendererComponent(JCarousel list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		loadBordersFromListRenderer();
		setOpaque(true);
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		if(index == 0) {
			setText(value == null ? "" : list.counter+" : "+value.toString());
		}else {
			setText(value == null ? "" : value.toString());		
		}
		
		setEnabled(list.isEnabled());
		setFont(list.getFont());
		Border border = null;
		if (cellHasFocus) {
			if (isSelected) {
				border = selectedAndFocus;
			}
			if (border == null) {
				border = notSelectedAndFocus;
			}
		} else {
			border = notFocus;
		}
		if (border == null) {
			border = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		}
		setBorder(border);
		return this;
	}
	
	public void validate() {}
	public void invalidate() {}
	public void repaint() {}
	public void repaint(long tm, int x, int y, int width, int height) {}
	public void repaint(Rectangle r) {}
	

}
