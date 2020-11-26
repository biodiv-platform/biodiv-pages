package com.strandls.pages.pojo.response;

import java.util.ArrayList;
import java.util.Collections;

public class PageArrayList extends ArrayList<PageTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204312711077181561L;

	@Override
	public boolean add(PageTree arg0) {
		int index = Collections.binarySearch(this, arg0, (o1, o2) -> o1.getPageIndex().compareTo(o2.getPageIndex()));
		index = index < 0 ? ~index : index;
		super.add(index, arg0);
		return true;
	}
}
