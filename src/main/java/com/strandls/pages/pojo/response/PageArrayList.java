package com.strandls.pages.pojo.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PageArrayList extends ArrayList<PageTree> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204312711077181561L;

	@Override
	public boolean add(PageTree arg0) {
		int index = Collections.binarySearch(this, arg0, new Comparator<PageTree>() {
			@Override
			public int compare(PageTree o1,
					PageTree o2) {
				return o2.getPageIndex().compareTo(o1.getPageIndex());
			}
		});

		index = index < 0 ? ~index : index;
		super.add(index, arg0);
		return true;
	}
}
