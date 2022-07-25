package com.aexiz.daviz.ui.swing;

import java.awt.Component;
/**
 * Public Cell Renderer usefull for the manipulation of the Carousel list that is present within the choice frame that is below the Executions window
 * @author Hans-Dieter
 *
 */
public interface CarouselCellRenderer {
	/**
	 * This method directly renders the components inside the choice frame within the Executions window
	 * @param list The actual JCarousel which is the list of elements inside the choice window
	 * @param value The elements that need to be manipulated
	 * @param index The starting index of that list 
	 * @param isSelected Checks if the choice list has been selected or not
	 * @param cellHasFocus Checks if the entire window is in focus or not
	 * @return
	 */
	Component getCarouselCellRendererComponent(JCarousel list, Object value, int index, boolean isSelected, boolean cellHasFocus);
	
}
