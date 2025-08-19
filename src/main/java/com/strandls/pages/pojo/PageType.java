package com.strandls.pages.pojo;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "pageType")
@XmlEnum
public enum PageType {

	@XmlEnumValue("Content")
	CONTENT("CONTENT"), @XmlEnumValue("Redirect")
	REDIRECT("REDIRECT");

	private String value;

	PageType(String value) {
		this.value = value;
	}

	public static PageType fromValue(String value) {
		for (PageType pageType : PageType.values()) {
			if (pageType.value.equals(value))
				return pageType;
		}
		throw new IllegalArgumentException(value);
	}
}
