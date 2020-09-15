package ${EntityModel.packagePath}.resourceobject;

import java.util.Date;

<#list EntityModel.oneToOneRelations as relation>
import ${relation.packagePath}.resourceobject.${relation.displayName}RO;
</#list>

<#list EntityModel.manyToOneRelations as relation>
import ${relation.packagePath}.resourceobject.${relation.displayName}RO;
</#list>

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
	
	
	<#list EntityModel.manyToOneRelations as relation>
	
	 private ${relation.displayName}RO ${relation.name};
	</#list>
	
	<#list EntityModel.oneToOneRelations as relation>	 
	 private ${relation.displayName}RO ${relation.initName};
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
	
	<#list EntityModel.oneToOneRelations as relation>	 
	 private ${relation.displayName}RO ${relation.initName}RO;
	</#list>
	
	<#list EntityModel.oneToOneRelations as relation>	 
	public void set${relation.displayName}RO(${relation.displayName}RO ${relation.name}RO){
		this.${relation.initName}RO=${relation.name}RO;
	}
	
	public ${relation.displayName}RO get${relation.displayName}RO(){
		return this.${relation.initName}RO;
	}
	</#list>
	
	<#list EntityModel.manyToOneRelations as relation>	 
	 private ${relation.displayName}RO ${relation.initName}RO;
	</#list>
	
	<#list EntityModel.manyToOneRelations as relation>	 
	public void set${relation.displayName}RO(${relation.displayName}RO ${relation.name}RO){
		this.${relation.initName}RO=${relation.name}RO;
	}
	
	public ${relation.displayName}RO get${relation.displayName}RO(){
		return this.${relation.initName}RO;
	}
	</#list>
}
