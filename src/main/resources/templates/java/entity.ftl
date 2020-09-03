package ${EntityModel.packagePath}.entity;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;


import org.hibernate.annotations.GenericGenerator;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
<#list EntityModel.manyToManyRelations as relation>	
import ${relation.packagePath}.entity.${relation.displayName}Entity;
</#list>
/**
 * The Main data entity class for ${EntityModel.displayName} Data
 * @author Deepak Pal
 *
 */
@Entity(name = "${EntityModel.name}")
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"keyid"})})
public class ${EntityModel.displayName}Entity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	@CsvDate(value = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date lastModified;	
	
	@Column
	private Date createdDate;
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long uuid){
		id=uuid;
		
	}

	<#list EntityModel.fields as field>
	@Column(name="${field.name}")
	@CsvBindByPosition(position = ${field.position})
	private ${field.type} ${field.camelCase};
	</#list>
	
	<#list EntityModel.manyToManyRelations as relation>
	 ${EntityModel.getRelationTable(relation)}
	 private Set<${relation.displayName}Entity> ${relation.name}s = new HashSet<>();
	</#list>
	
	<#list EntityModel.fields as field>
	
	public void set${field.initCapName}(${field.type} ${field.camelCase}){
		this.${field.camelCase}=${field.camelCase};
	}
	
	public ${field.type} get${field.initCapName}(){
		return this.${field.camelCase};
	}
	
	
	</#list>
	
	<#list EntityModel.manyToManyRelations as relation>	 
	public void set${relation.displayName}s(Set<${relation.displayName}Entity> ${relation.name}s){
		this.${relation.name}s=${relation.name}s;
	}
	
	public Set<${relation.displayName}Entity> get${relation.displayName}s(){
		return this.${relation.name}s;
	}
	</#list>
	
	
}
