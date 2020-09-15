package com.adiops.init.boot.freemarker;

import com.adiops.init.boot.freemarker.entity.EntityModel;

public class CodeGenerator3 {

	public static void main(String[] args) throws Exception {		
		
		
		String learningPath="com.adiops.apigateway.;learning_path=>keyid:String:0,status:Integer:1,score:Integer:2,max_score:Integer:3,user_key:String:4,step:Integer:5,total_step:Integer:6";
		EntityModel learningPathModel= CodeGenerator.getEntityModel(learningPath);
		
		String coursePath="com.adiops.apigateway.;course_line_group=>keyid:String:0,status:Integer:1,score:Integer:2,max_score:Integer:3,title:String:4,description:String:5,user_key:String:6,course_key:String:7,step:Integer:8,total_step:Integer:9";
		EntityModel coursePathModel= CodeGenerator.getEntityModel(coursePath);
		
		
		String modulePath="com.adiops.apigateway.;module_line_group=>keyid:String:0,status:Integer:1,score:Integer:2,max_score:Integer:3,title:String:4,description:String:5,user_key:String:6,course_key:String:7,module_key:String:8,step:Integer:9,total_step:Integer:10";
		EntityModel modulePathModel= CodeGenerator.getEntityModel(modulePath);
		
		String topicPath="com.adiops.apigateway.;topic_line_group=>keyid:String:0,status:Integer:1,score:Integer:2,max_score:Integer:3,title:String:4,description:String:5,user_key:String:6,course_key:String:7,module_key:String:8,topic_key:String:9,step:Integer:10,total_step:Integer:11";
		EntityModel topicPathModel= CodeGenerator.getEntityModel(topicPath);
		
		
		String questionPath="com.adiops.apigateway.;question_line_item=>keyid:String:0,status:Integer:1,score:Integer:2,max_score:Integer:3,title:String:4,description:String:5,user_key:String:6,course_key:String:7,module_key:String:8,topic_key:String:9,question_key:String:10,answer:String:11,correct_answer:String:12,question_type:Integer:13";
		EntityModel questionPathModel= CodeGenerator.getEntityModel(questionPath);

		
		
		learningPathModel.addOneToManyRelation(coursePathModel);
		coursePathModel.addManyToOneRelation(learningPathModel);
		
		coursePathModel.addOneToManyRelation(modulePathModel);
		modulePathModel.addManyToOneRelation(coursePathModel);
		
		modulePathModel.addOneToManyRelation(topicPathModel);
		topicPathModel.addManyToOneRelation(modulePathModel);
		
		
		
		topicPathModel.addOneToManyRelation(questionPathModel);
		questionPathModel.addManyToOneRelation(topicPathModel);
		
		
		
		
		
		CodeGenerator.generateCode(learningPathModel);
		CodeGenerator.generateCode(coursePathModel);
		CodeGenerator.generateCode(modulePathModel);
		CodeGenerator.generateCode(topicPathModel);
		CodeGenerator.generateCode(questionPathModel);
		
		
		
		
		
	}
	

	
}
