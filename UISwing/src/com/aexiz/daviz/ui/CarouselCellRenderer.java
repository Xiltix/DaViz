package com.aexiz.daviz.ui;

import java.awt.Component;

public interface CarouselCellRenderer {
	
	Component getCarouselCellRendererComponent(JCarousel list, Object value, int index, boolean isSelected, boolean cellHasFocus);
	
}
