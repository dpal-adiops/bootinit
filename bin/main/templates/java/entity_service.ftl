package  ${EntityModel.packagePath}.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.adiops.apigateway.common.helper.CSVHelper;
import com.adiops.apigateway.common.response.RestException;
 
import ${EntityModel.packagePath}.entity.${EntityModel.displayName}Entity;
import ${EntityModel.packagePath}.repository.${EntityModel.displayName}Repository;
import ${EntityModel.packagePath}.resourceobject.${EntityModel.displayName}RO;

<#list EntityModel.manyToManyRelations as relation>
import ${relation.packagePath}.entity.${relation.displayName}Entity;
import ${relation.packagePath}.repository.${relation.displayName}Repository;
import ${relation.packagePath}.resourceobject.${relation.displayName}RO;
</#list>


/**
 * This is the implementation class for ${EntityModel.displayName} responsible for all the CRUD operations and other business related operations.
 * @author Deepak Pal
 *
 */
@Service
public class ${EntityModel.displayName}Service{

	@Autowired
	${EntityModel.displayName}Repository m${EntityModel.displayName}Repository;

	<#list EntityModel.manyToManyRelations as relation>
	@Autowired
	${relation.displayName}Repository m${relation.displayName}Repository;
	</#list>
	@Autowired
	private ModelMapper mModelMapper;

	/**
	 *
	 * fetch list of ${EntityModel.displayName}
	 */
	
	public List<${EntityModel.displayName}RO> get${EntityModel.displayName}ROs() {
		List<${EntityModel.displayName}RO> t${EntityModel.displayName}ROs = m${EntityModel.displayName}Repository.findAll().stream()
				.map(entity -> mModelMapper.map(entity, ${EntityModel.displayName}RO.class)).collect(Collectors.toList());
		return t${EntityModel.displayName}ROs;
	}

	/**
	 *
	 * get ${EntityModel.displayName} by id
	 */
	public ${EntityModel.displayName}RO get${EntityModel.displayName}ById(Long id) throws RestException {
        Optional<${EntityModel.displayName}Entity> t${EntityModel.displayName} = m${EntityModel.displayName}Repository.findById(id);
         
        if(t${EntityModel.displayName}.isPresent()) {
            return mModelMapper.map(t${EntityModel.displayName}.get(), ${EntityModel.displayName}RO.class);
        } else {
            throw new RestException("No ${EntityModel.name} record exist for given id");
        }
    }
	
	 
	/**
	 * create or update 
	 */
	public ${EntityModel.displayName}RO createOrUpdate${EntityModel.displayName}(${EntityModel.displayName}RO t${EntityModel.displayName}RO) throws RestException 
    {
        ${EntityModel.displayName}Entity newEntity ;
         
        if(t${EntityModel.displayName}RO.getId()!=null)
        {
        	newEntity=	 m${EntityModel.displayName}Repository.findById(t${EntityModel.displayName}RO.getId()).orElse(new ${EntityModel.displayName}Entity());
        }
        else
        {
        	newEntity=new ${EntityModel.displayName}Entity();
        }
       
              
      
        <#list EntityModel.fields as field>
        	if(t${EntityModel.displayName}RO.get${field.initCapName}() !=null)
        	newEntity.set${field.initCapName}(t${EntityModel.displayName}RO.get${field.initCapName}());
        </#list>

  	 try {
        	 newEntity = m${EntityModel.displayName}Repository.save(newEntity);   
		} catch (Exception e) {
			throw new RestException("Could not save result due to unique key exception");
		}
                 
        return  mModelMapper.map(newEntity, ${EntityModel.displayName}RO.class);
    } 
     
	/**
	 * delete by Id
	 */
     public void delete${EntityModel.displayName}ById(Long id) throws RestException {
       Optional<${EntityModel.displayName}Entity> t${EntityModel.displayName} = m${EntityModel.displayName}Repository.findById(id);
         
        if(t${EntityModel.displayName}.isPresent()) { 
        
            m${EntityModel.displayName}Repository.deleteById(id);
        } else {
            throw new RestException("No ${EntityModel.name} record exist for given id");
        }
    } 
    
	/**
	 * upload file to DB
	 */
	
	public void importCSV(MultipartFile file) throws RestException {
		try {
			List<${EntityModel.displayName}Entity> t${EntityModel.displayName}s = CSVHelper.csvToPOJOs(file.getInputStream(), ${EntityModel.displayName}Entity.class);
			m${EntityModel.displayName}Repository.deleteAll();
			m${EntityModel.displayName}Repository.saveAll(t${EntityModel.displayName}s);
		} catch (IOException e) {
			throw new RestException(RestException.ERROR_STATUS_BAD_REQUEST,
					"fail to store csv data: " + e.getMessage());
		}
	}

	/**
	 * upload stream to DB
	 */
	public void importCSV(InputStream is) throws RestException {
		try {
			List<${EntityModel.displayName}Entity> t${EntityModel.displayName}s = CSVHelper.csvToPOJOs(is, ${EntityModel.displayName}Entity.class);
			m${EntityModel.displayName}Repository.saveAll(t${EntityModel.displayName}s);
		} catch (Exception e) {
			throw new RestException(RestException.ERROR_STATUS_BAD_REQUEST,
					"fail to store csv data: " + e.getMessage());
		}
	}

	/**
	 * get count
	 */
	public long count() {
		return m${EntityModel.displayName}Repository.count();
	}

	/**
	 *
	 * fetch list of ${EntityModel.displayName}
	 */
	
	public List<${EntityModel.displayName}RO> findAll(int pageNumber, int rowPerPage) {
		Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, Sort.by("id").ascending());
		List<${EntityModel.displayName}RO> t${EntityModel.displayName}ROs = m${EntityModel.displayName}Repository.findAll(sortedByIdAsc).stream()
				.map(entity -> mModelMapper.map(entity, ${EntityModel.displayName}RO.class)).collect(Collectors.toList());
		return t${EntityModel.displayName}ROs;
	}
	
		<#list EntityModel.manyToManyRelations as relation>
	
	/**
	 *
	 * fetch list of ${EntityModel.displayName} ${relation.name}s
	 */
	
	public List<${relation.displayName}RO> find${EntityModel.displayName}${relation.displayName}s(Long id) {
		List<${relation.displayName}RO> t${relation.displayName}ROs= new ArrayList<>();   
		Optional<${EntityModel.displayName}Entity> t${EntityModel.displayName} = m${EntityModel.displayName}Repository.findById(id);
		if(t${EntityModel.displayName}.isPresent()) {
			t${EntityModel.displayName}.ifPresent(en->{
				en.get${relation.displayName}s().forEach(re->{					
					t${relation.displayName}ROs.add(mModelMapper.map(re, ${relation.displayName}RO.class));
				});
			});
		}				
		return t${relation.displayName}ROs;
	}
	
	/**
	 *
	 * assign a ${relation.name} to ${EntityModel.name}
	 */
	
	public void add${EntityModel.displayName}${relation.displayName}(Long id, Long ${relation.name}Id) {
		Optional<${relation.displayName}Entity> t${relation.displayName} = m${relation.displayName}Repository.findById(${relation.name}Id);
		Optional<${EntityModel.displayName}Entity> t${EntityModel.displayName} = m${EntityModel.displayName}Repository.findById(id);
		if(t${relation.displayName}.isPresent() && t${EntityModel.displayName}.isPresent()) {
			${EntityModel.displayName}Entity t${EntityModel.displayName}Entity=t${EntityModel.displayName}.get();
			${relation.displayName}Entity t${relation.displayName}Entity= t${relation.displayName}.get();
			t${EntityModel.displayName}Entity.get${relation.displayName}s().add(t${relation.displayName}Entity);
			t${relation.displayName}Entity.get${EntityModel.displayName}s().add(t${EntityModel.displayName}Entity);
			m${EntityModel.displayName}Repository.save(t${EntityModel.displayName}Entity);
		}
			
	}
	
	/**
	 *
	 * unassign a ${relation.name} to ${EntityModel.name}
	 */
	
	public void unassign${EntityModel.displayName}${relation.displayName}(Long id, Long ${relation.name}Id) {
		Optional<${relation.displayName}Entity> t${relation.displayName} = m${relation.displayName}Repository.findById(${relation.name}Id);
		Optional<${EntityModel.displayName}Entity> t${EntityModel.displayName} = m${EntityModel.displayName}Repository.findById(id);
		if(t${relation.displayName}.isPresent() && t${EntityModel.displayName}.isPresent()) {
			${EntityModel.displayName}Entity t${EntityModel.displayName}Entity=t${EntityModel.displayName}.get();
			t${EntityModel.displayName}Entity.get${relation.displayName}s().remove(t${relation.displayName}.get());
			m${EntityModel.displayName}Repository.save(t${EntityModel.displayName}Entity);
		}
			
	}
	
	/**
	 *
	 * fetch list of ${EntityModel.displayName} ${relation.name}s
	 */
	
	public List<${relation.displayName}RO> findUnassign${EntityModel.displayName}${relation.displayName}s(Long id) {
		List<${relation.displayName}RO> t${relation.displayName}ROs= new ArrayList<>();   
		 m${EntityModel.displayName}Repository.findById(id).ifPresent(en->{
			 List<${relation.displayName}Entity> t${relation.displayName}s = m${relation.displayName}Repository.findAll();
			 t${relation.displayName}s.removeAll(en.get${relation.displayName}s());
			 t${relation.displayName}s.forEach(re->{					
					t${relation.displayName}ROs.add(mModelMapper.map(re, ${relation.displayName}RO.class));
				});
		 });
		
		return t${relation.displayName}ROs;
	}
	
	</#list>
	
}
