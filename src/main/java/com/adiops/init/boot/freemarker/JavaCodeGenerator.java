package com.adiops.init.boot.freemarker;

import static com.adiops.init.boot.freemarker.CodeGenerator.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import com.adiops.init.boot.freemarker.entity.EntityModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class JavaCodeGenerator {
	
	
	

	
	public static void generate(EntityModel tEntityModel) throws Exception {
		 Configuration cfg = new Configuration();
		 try {
			 Map<String, Object> data = new HashMap<String, Object>();
				data.put(EntityModel.class.getSimpleName(),tEntityModel);
				Template template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity.ftl");
				generateFile(template,data,"Entity","entity");
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity_ro.ftl");
				generateFile(template,data,"RO","resourceobject");
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity_repository.ftl");
				generateFile(template,data,"Repository","repository");
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity_service.ftl");
				generateFile(template,data,"Service","service");
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity_resource.ftl");
				generateFile(template,data,"Resource","resource");
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/java/entity_web_controller.ftl");
				generateFile(template,data,"WebController","web");
				
				
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-edit.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-edit",tEntityModel.getName());
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-add.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-add",tEntityModel.getName());
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-list.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-list",tEntityModel.getName());
				
				
		 }catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
	}
	
	

	
	
	public static void generateFile(Template template,  Map<String, Object> data ,String name,String component) throws IOException, TemplateException {
		// File output
		EntityModel tEntityModel= (EntityModel) data.get(EntityModel.class.getSimpleName());
		Path path=createPackageFolder(data,component);
		Writer file = new FileWriter (new File(path.toFile(),tEntityModel.getDisplayName()+name+".java"));
		template.process(data, file);
		file.flush();
		file.close();
	}
	
	public static void generateTemplateFile(Template template,  Map<String, Object> data ,String name,String component) throws IOException, TemplateException {
		// File output
		EntityModel tEntityModel= (EntityModel) data.get(EntityModel.class.getSimpleName());
		Path path = Paths.get(tEntityModel.getCodeGeneratePath()+APP_NAME+"/src/main/resources/templates" +"/"+component);
		 if (!Files.exists(path)) 	            
	            Files.createDirectories(path);
	       
		Writer file = new FileWriter (new File(path.toFile(),name+".html"));
		StringWriter stringWriter= new StringWriter();
		template.process(data, stringWriter);
		String content=RegExUtils.replaceAll(stringWriter.toString(),"\\@\\@","\\$");
		file.append(content);
		stringWriter.close();
		file.flush();
		file.close();
	}
	
	public static Path createPackageFolder(Map<String, Object> data,String component) throws IOException {
		 
		EntityModel tEntityModel= (EntityModel) data.get(EntityModel.class.getSimpleName());
		Path path = Paths.get(tEntityModel.getCodeGeneratePath()+APP_NAME+"/src/main/java/" +StringUtils.replaceChars(tEntityModel.getPackagePath(), '.', File.separatorChar)+"/"+component);
	    if (!Files.exists(path)) 	            
	      Files.createDirectories(path);
	     return path;   
	}
}


