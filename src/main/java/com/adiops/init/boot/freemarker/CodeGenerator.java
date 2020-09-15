package com.adiops.init.boot.freemarker;

import com.adiops.init.boot.freemarker.entity.EntityFieldType;
import com.adiops.init.boot.freemarker.entity.EntityModel;

public class CodeGenerator {
	public static final String APP_NAME="app1";
	public static final String FOLDERPATH="C://Z-G/target/";
	public static final String CLASSPATH_RESOURCES="src/main/resources/";
	
	public static void main(String[] args) throws Exception {		
		String course="com.adiops.apigateway.;course=>keyid:String:0,name:String:1,description:String:2,author_id:String:3,domain_id:String:4";
		EntityModel courseModel= getEntityModel(course);
		String module="com.adiops.apigateway.;module=>keyid:String:0,name:String:1,description:String:2,author_id:String:3,domain_id:String:4";
		EntityModel moduleModel= getEntityModel(module);
		String topic="com.adiops.apigateway.;topic=>keyid:String:0,name:String:1,description:String:2,title:String:3,author_id:String:4,domain_id:String:5";
		EntityModel topicModel= getEntityModel(topic);
		
		String question="com.adiops.apigateway.;question=>keyid:String:0,title:String:1,description:String:2,answer:String:3,author_id:String:4,domain_id:String:5";
		EntityModel questionModel= getEntityModel(question);
		
		String image="com.adiops.apigateway.;image=>keyid:String:0,name:String:1,url:String:2,author_id:String:3,domain_id:String:4";
		EntityModel imageModel= getEntityModel(image);
		
		String video="com.adiops.apigateway.;video=>keyid:String:0,name:String:1,url:String:2,author_id:String:3,domain_id:String:4";
		EntityModel videoModel= getEntityModel(video);
		
		String page="com.adiops.apigateway.;page=>keyid:String:0,name:String:1,description:String:2,author_id:String:3,domain_id:String:4";
		EntityModel pageModel= getEntityModel(page);
		
		
		courseModel.addManyToManyRelation(moduleModel);
		courseModel.addManyToManyRelation(imageModel);
		courseModel.addManyToManyRelation(videoModel);
		courseModel.addManyToManyRelation(pageModel);
		
		moduleModel.addManyToManyRelation(courseModel);
		moduleModel.addManyToManyRelation(topicModel);
		moduleModel.addManyToManyRelation(imageModel);
		moduleModel.addManyToManyRelation(videoModel);
		moduleModel.addManyToManyRelation(pageModel);
		
		topicModel.addManyToManyRelation(moduleModel);
		topicModel.addManyToManyRelation(questionModel);
		topicModel.addManyToManyRelation(imageModel);
		topicModel.addManyToManyRelation(videoModel);
		topicModel.addManyToManyRelation(pageModel);
		
		questionModel.addManyToManyRelation(topicModel);
		questionModel.addManyToManyRelation(imageModel);
		questionModel.addManyToManyRelation(videoModel);
		questionModel.addManyToManyRelation(pageModel);
		
		imageModel.addManyToManyRelation(courseModel);
		imageModel.addManyToManyRelation(moduleModel);
		imageModel.addManyToManyRelation(topicModel);
		imageModel.addManyToManyRelation(questionModel);
		
		videoModel.addManyToManyRelation(courseModel);
		videoModel.addManyToManyRelation(moduleModel);
		videoModel.addManyToManyRelation(topicModel);
		videoModel.addManyToManyRelation(questionModel);
		
		pageModel.addManyToManyRelation(courseModel);
		pageModel.addManyToManyRelation(moduleModel);
		pageModel.addManyToManyRelation(topicModel);
		pageModel.addManyToManyRelation(questionModel);
		
		
		generateCode(courseModel);
		generateCode(moduleModel);
		generateCode(topicModel);
		generateCode(questionModel);
		generateCode(imageModel);
		generateCode(videoModel);
		generateCode(pageModel);
		
		
		
		
		
	}
	
	public static void generateCode(EntityModel tEntityModel) throws Exception {		
		
		tEntityModel.setCodeGeneratePath(FOLDERPATH);
		JavaCodeGenerator.generate(tEntityModel);
		HTMLCodeGenerator.generate(tEntityModel);
		
	}
	

	public static EntityModel getEntityModel(String text) {
		String[] pack=text.split(";");
		String[] spilt= pack[1].split("=>");
 		EntityModel tEntityModel= new EntityModel(pack[0],spilt[0]);
		String[] tokens=spilt[1].split(",");
		for(String token : tokens)
		tEntityModel.setField(getEntityFieldType(token));
		return tEntityModel;
	}
	
	public static EntityFieldType getEntityFieldType(String text) {
		String[] spilt= text.split(":");
		EntityFieldType tEntityFieldType= new EntityFieldType(spilt[0],spilt[1],Integer.parseInt(spilt[2]));		
		return tEntityFieldType;
	}
	
}
