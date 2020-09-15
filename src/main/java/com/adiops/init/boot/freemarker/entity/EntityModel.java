package com.adiops.init.boot.freemarker.entity;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

public class EntityModel {

	String parentPackage;
	String packagePath;
	String name;
	String displayName;	
	String codeGeneratePath;
	String initName;
	
	List<EntityFieldType> fields = new ArrayList<>();
	List<EntityModel> oneToManyRelations = new ArrayList<>();
	List<EntityModel> manyToManyRelations = new ArrayList<>();
	List<EntityModel> oneToOneRelations = new ArrayList<>();
	List<EntityModel> manyToOneRelations = new ArrayList<>();
	
	public EntityModel(String packagePath, String name) {
		super();
		this.parentPackage = packagePath;
		this.packagePath = packagePath+StringUtils.replace(name, "_", ".");
		this.name = name;
		this.displayName=StringUtils.capitalize(CaseUtils.toCamelCase(name, false, '_'));
		this.initName=StringUtils.uncapitalize(this.displayName);
	}

	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

		public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public List<EntityFieldType> getFields() {
		return fields;
	}

	public void setFields(List<EntityFieldType> fields) {
		this.fields = fields;
	}
	
	public void setField(EntityFieldType field) {
		this.fields.add(field);
	}

	public String getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(String parentPackage) {
		this.parentPackage = parentPackage;
	}

	public String getCodeGeneratePath() {
		return codeGeneratePath;
	}

	public void setCodeGeneratePath(String codeGeneratePath) {
		this.codeGeneratePath = codeGeneratePath;
	}

	public List<EntityModel> getManyToManyRelations() {
		return manyToManyRelations;
	}

	public void setManyToManyRelations(List<EntityModel> manyToManyRelations) {
		this.manyToManyRelations = manyToManyRelations;
	}
	
	public void addManyToManyRelation(EntityModel manyToManyRelation) {
		this.manyToManyRelations.add(manyToManyRelation);
	}
	
	public String getRelationTable(EntityModel relation) {
		StringWriter tStringWriter= new StringWriter();
		String relName=relation.getName();
		
		
		if(relName.compareTo(this.name)>0)
		{
			tStringWriter.append("@ManyToMany \n");
			tStringWriter.append("@JoinTable( \n");
			tStringWriter.append(" name = \""+this.name+"_"+relName+"\",\n"); 
			tStringWriter.append(" joinColumns = @JoinColumn(name = \""+this.name+"_id\"),\n"); 
			tStringWriter.append(" inverseJoinColumns = @JoinColumn(name = \""+relName+"_id\"))");			
			return tStringWriter.toString();
		}
		else
		{
			tStringWriter.append("	@ManyToMany(mappedBy = ");
			tStringWriter.append("\""+this.name+"s\")");			
			return tStringWriter.toString();	
		}
		
	}

	public void addOneToManyRelation(EntityModel oneToManyRelation) {
		this.oneToManyRelations.add(oneToManyRelation);
	}

	public List<EntityModel> getOneToManyRelations() {
		return oneToManyRelations;
	}

	public void setOneToManyRelations(List<EntityModel> oneToManyRelations) {
		this.oneToManyRelations = oneToManyRelations;
	}

	public void addManyToOneRelation(EntityModel manyToOneRelations) {
		this.manyToOneRelations.add(manyToOneRelations);
	}
	
	public List<EntityModel> getManyToOneRelations() {
		return manyToOneRelations;
	}

	public void setManyToOneRelations(List<EntityModel> manyToOneRelations) {
		this.manyToOneRelations = manyToOneRelations;
	}

	public void addOneToOneRelation(EntityModel oneToOneRelation) {
		this.oneToOneRelations.add(oneToOneRelation);
		
	}

	public List<EntityModel> getOneToOneRelations() {
		return oneToOneRelations;
	}

	public void setOneToOneRelations(List<EntityModel> oneToOneRelations) {
		this.oneToOneRelations = oneToOneRelations;
	}

	public String getInitName() {
		return initName;
	}

	public void setInitName(String initName) {
		this.initName = initName;
	}
	
	
}
