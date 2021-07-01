package com.strandls.pages.pojo.response;

import java.util.ArrayList;
import java.util.Collections;

public class NewsletterArrayList extends ArrayList<NewsletterWithParentChildRelationship> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9204312711077181561L;

	@Override
	public boolean add(NewsletterWithParentChildRelationship arg0) {
		int index = Collections.binarySearch(this, arg0,
				(o1, o2) -> o2.getDisplayOrder().compareTo(o1.getDisplayOrder()));

		index = index < 0 ? ~index : index;
		super.add(index, arg0);
		return true;
	}
}
