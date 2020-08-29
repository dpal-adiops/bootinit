package com.adiops.init.boot.freemarker;

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
import static com.adiops.init.boot.freemarker.CodeGenerator.*;
import org.apache.commons.lang3.RegExUtils;

import com.adiops.init.boot.freemarker.entity.EntityModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HTMLCodeGenerator {

	public static void generate(EntityModel tEntityModel) throws Exception {
		 Configuration cfg = new Configuration();
		 try {
			 Map<String, Object> data = new HashMap<String, Object>();
				data.put(EntityModel.class.getSimpleName(),tEntityModel);
				
				Template template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-edit.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-edit",tEntityModel.getName());
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-add.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-add",tEntityModel.getName());
				template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/entity-list.html");
				HTMLCodeGenerator.generateTemplateFile(template,data,tEntityModel.getName()+"-list",tEntityModel.getName());
				
				//add relation model
				for (EntityModel rEntityModel : tEntityModel.getManyToManyRelations()) {
					data.put("RelationModel",rEntityModel);
					template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/relation/module-list.html");
					HTMLCodeGenerator.generateTemplateFile(template,data,rEntityModel.getName()+"-list",tEntityModel.getName()+"/"+rEntityModel.getName());
					template = cfg.getTemplate(CLASSPATH_RESOURCES+"templates/html/relation/module-assign.html");
					HTMLCodeGenerator.generateTemplateFile(template,data,rEntityModel.getName()+"-assign",tEntityModel.getName()+"/"+rEntityModel.getName());
				}
			
				
				
		 }catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
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
}
