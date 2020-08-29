package com.adiops.init.boot.freemarker;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.CaseUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class EntityGenerator {
	

	
	public static final String NAMESPACE= "com.adiops.apigateway.";
	public static final String FOLDERPATH="C://Z-G/bootinit/";
	
	
	
	public static class FieldType {
		String name;
		String initCapName;
		String snakeCase;
		String camelCase;
		String type;
		int position;
		
		public FieldType(String name ,String type ,int position) {
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

		public String getInitCapName() {
			return initCapName;
		}

		public String getSnakeCase() {
			return snakeCase;
		}

		public String getCamelCase() {
			return camelCase;
		}

		public String getType() {
			return type;
		}

		public int getPosition() {
			return position;
		}

		
		 
	}
	
	public static void main(String[] args) throws Exception {
		
		String relation[] = new String[] {"course","module"};
		
		
		//UnZipFile.projectInitialzer(FOLDERPATH);
		generate("course",new String[]{"key","name","desc","author_id"},new String[]{"online"});
		generate("module",new String[]{"key","name","desc"},new String[]{"online"});
//		generate("topic",new String[]{"key","name","desc"},new String[]{"online"});
//		generate("image",new String[]{"key","name","desc"},new String[]{"type"});
//		generate("video",new String[]{"key","name","desc"},new String[]{"type"});
//		generate("document",new String[]{"key","name","desc"},new String[]{"type"});
//		generate("page",new String[]{"key","name","desc"},new String[]{"type"});
//		generate("question",new String[]{"key","question","answer","hint"},new String[]{"online","type","level"});
//		generate("course_module",new String[]{"course_id","module_id"},new String[]{});
	}
	
	public static void generate(String entity,String[] str_field,String[] num_field) throws Exception {
		 Configuration cfg = new Configuration();
		 try {
			 Map<String, Object> data = new HashMap<String, Object>();
				//if(data.(ENTITY))
				data.put("Package",NAMESPACE+entity);
				data.put("name",  entity);
				data.put("Name",  StringUtils.capitalize(CaseUtils.toCamelCase(entity, false, '_')));
				
				List<FieldType> fields = new ArrayList<>();
				int i =0;
				for (; i < str_field.length; i++) {
					fields.add(new FieldType(str_field[i], "String", i));
				}
				
				data.put("Strings",fields);
				
				fields = new ArrayList<>();
				
				for (int j=0; j < num_field.length;j++, i++) {
					fields.add(new FieldType(num_field[j], "Integer", i));
				}
				
				data.put("Integers",fields);
				
				Writer out = new OutputStreamWriter(System.out);
				//Load template from source folder
				Template template = cfg.getTemplate("src/main/resources/entity.ftl");
				generateFile(template,data,data.get("Name")+"Entity","entity");
				
				
				//Load template from source folder
				template = cfg.getTemplate("src/main/resources/repository.ftl");
				generateFile(template,data,data.get("Name")+"Repository","repository");
				
				// Console output
				template.process(data, out);
				out.flush();
				
				//Load template from source folder
				template = cfg.getTemplate("src/main/resources/entity_ro.ftl");
				generateFile(template,data,data.get("Name")+"RO","resourceobject");
				// Console output
				
				template.process(data, out);
				out.flush();
				
				
				//Load template from source folder
				template = cfg.getTemplate("src/main/resources/entity_service.ftl");
				generateFile(template,data,data.get("Name")+"Service","service");
				
				//Load template from source folder
				template = cfg.getTemplate("src/main/resources/entity_resource.ftl");
				generateFile(template,data,data.get("Name")+"Resource","resource");
				
				//Load template from source folder
				template = cfg.getTemplate("src/main/resources/entity_web_controller.ftl");
				generateFile(template,data,data.get("Name")+"WebController","web");
				
				
				template = cfg.getTemplate("src/main/resources/templates/entity_list.ftl");
				generateTemplateFile(template,data,entity+"-list",entity);
				
				template = cfg.getTemplate("src/main/resources/templates/entity_edit.ftl");
				generateTemplateFile(template,data,entity+"-edit",entity);
				
				
				// Console output
				template.process(data, out);
				out.flush();
				
				
				
				
		 }catch (IOException e) {
				e.printStackTrace();
			} catch (TemplateException e) {
				e.printStackTrace();
			}
	}
	
	public static void generateFile(Template template,  Map<String, Object> data ,String name,String component) throws IOException, TemplateException {
		// File output
		Path path=createPackageFolder(component,data.get("name").toString());
		Writer file = new FileWriter (new File(path.toFile(),name+".java"));
		template.process(data, file);
		file.flush();
		file.close();
	}
	
	public static void generateTemplateFile(Template template,  Map<String, Object> data ,String name,String component) throws IOException, TemplateException {
		// File output
		Path path = Paths.get(FOLDERPATH+"app/src/main/resources/templates" +"/"+component);
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
	
	public static Path createPackageFolder(String component,String name) throws IOException {
		 
		Path path = Paths.get(FOLDERPATH+"app/src/main/java/" +StringUtils.replaceChars(NAMESPACE+name, '.', File.separatorChar)+"/"+component);

	        if (!Files.exists(path)) 	            
	            Files.createDirectories(path);
	       
	     return path;   
	}
}


