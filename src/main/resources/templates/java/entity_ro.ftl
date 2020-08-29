package ${EntityModel.packagePath}.resourceobject;

import java.util.Date;


/**
 * This class is responsible for transfer the resource ${EntityModel.displayName} Data
 * @author Deepak Pal
 *
 */
public class ${EntityModel.displayName}RO {

	private Long id;
	
	
	private Date lastModified;	
	
	
	private Date createdDate;
	
	<#list EntityModel.fields as field>	
	private ${field.type} ${field.camelCase};
	</#list>
	
	public Long getId(){
		return this.id;
	}
	
	public void setId(Long uuid){
		this.id=uuid;
	}
	
	<#list EntityModel.fields as field>
	
	public void set${field.initCapName}(${field.type} ${field.camelCase}){
		this.${field.camelCase}=${field.camelCase};
	}
	
	public ${field.type} get${field.initCapName}(){
		return this.${field.camelCase};
	}
	</#list>
	
	
}
