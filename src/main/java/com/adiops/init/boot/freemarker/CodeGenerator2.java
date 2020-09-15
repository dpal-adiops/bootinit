package com.adiops.init.boot.freemarker;

import com.adiops.init.boot.freemarker.entity.EntityModel;

public class CodeGenerator2 {

	public static void main(String[] args) throws Exception {		
		String appuser="com.adiops.apigateway.;app_user=>keyid:String:0,user_name:String:1,email:String:2,first_name:String:3,last_name:String:4,encrypted_password:String:5,mobile:String:6,enabled:Boolean:7";
		EntityModel appUserModel= CodeGenerator.getEntityModel(appuser);
		String approle="com.adiops.apigateway.;app_role=>keyid:String:0,name:String:1,description:String:2,permission:String:3";
		EntityModel appRoleModel= CodeGenerator.getEntityModel(approle);
		
		
		
		appUserModel.addManyToManyRelation(appRoleModel);
		
		appRoleModel.addManyToManyRelation(appUserModel);
		
		
		
		CodeGenerator.generateCode(appUserModel);
		CodeGenerator.generateCode(appRoleModel);
		
		
		
		
		
	}
	

	
}
