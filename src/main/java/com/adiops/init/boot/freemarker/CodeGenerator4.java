package com.adiops.init.boot.freemarker;

import com.adiops.init.boot.freemarker.entity.EntityModel;

public class CodeGenerator4 {

	public static void main(String[] args) throws Exception {		
		String appuser="com.adiops.apigateway.;app_user=>keyid:String:0,user_name:String:1,email:String:2,first_name:String:3,last_name:String:4,encrypted_password:String:5,mobile:String:6,enabled:Boolean:7";
		EntityModel appUserModel= CodeGenerator.getEntityModel(appuser);
		
		String approle="com.adiops.apigateway.;app_role=>keyid:String:0,name:String:1,description:String:2,permission:String:3";
		EntityModel appRoleModel= CodeGenerator.getEntityModel(approle);
		
		
		String learningTrack="com.adiops.apigateway.;learning_track=>keyid:String:0,name:String:1,credit:Integer:2,score:Integer:3,max_score:Integer:4,total_step:Integer:5,status:String:6,refid:String:7";
		EntityModel learningTrackModel= CodeGenerator.getEntityModel(learningTrack);
		
		String learningTrackPath="com.adiops.apigateway.;learning_track_path=>keyid:String:0,step:Integer:1,status:String:2,refid:String:3";
		EntityModel learningTrackPathModel= CodeGenerator.getEntityModel(learningTrackPath);
		
		
		String learningTrackQuestion="com.adiops.apigateway.;learning_track_question=>keyid:String:0,title:String:1,question:String:2,answer:String:3,correct_answer:String:4,score:Integer:5,max_score:Integer:6,course_key:String:7,module_key:String:8,topic_key:String:9,question_key:String:10,user_key:String:11,status:Integer:12";
		EntityModel learningTrackQuestionModel= CodeGenerator.getEntityModel(learningTrackQuestion);

		
		appUserModel.addOneToManyRelation(learningTrackModel);
		learningTrackModel.addManyToOneRelation(appUserModel);
		learningTrackModel.addOneToOneRelation(learningTrackModel);
		
		learningTrackModel.addOneToManyRelation(learningTrackPathModel);
		learningTrackPathModel.addManyToOneRelation(learningTrackModel);
		
		learningTrackPathModel.addOneToOneRelation(learningTrackQuestionModel);
		learningTrackPathModel.addOneToOneRelation(learningTrackPathModel);
		learningTrackQuestionModel.addOneToOneRelation(learningTrackPathModel);
		
		appUserModel.addManyToManyRelation(appRoleModel);		
		appRoleModel.addManyToManyRelation(appUserModel);
		
		
		
		CodeGenerator.generateCode(learningTrackModel);
		CodeGenerator.generateCode(learningTrackPathModel);
		CodeGenerator.generateCode(learningTrackQuestionModel);
		CodeGenerator.generateCode(appUserModel);
		CodeGenerator.generateCode(appRoleModel);
		
		
		
		
		
	}
	

	
}
