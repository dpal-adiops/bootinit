package com.adiops.init.boot.freemarker.entity;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

public class EntityFieldType {

	String name;
	String initCapName;
	String snakeCase;
	String camelCase;
	String type;
	int position;
	
	public EntityFieldType(String name ,String type ,int position) {
		super();
		this.name = name;
		this.camelCase= CaseUtils.toCamelCase(name, false, '_');
		this.initCapName=StringUtils.capitalize(camelCase); 
		this.snakeCase=name;
		this.type=type;
		this.position=position;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInitCapName() {
		return initCapName;
	}

	public void setInitCapName(String initCapName) {
		this.initCapName = initCapName;
	}

	public String getSnakeCase() {
		return snakeCase;
	}

	public void setSnakeCase(String snakeCase) {
		this.snakeCase = snakeCase;
	}

	public String getCamelCase() {
		return camelCase;
	}

	public void setCamelCase(String camelCase) {
		this.camelCase = camelCase;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	
}
