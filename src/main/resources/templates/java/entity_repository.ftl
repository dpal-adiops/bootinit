package  ${EntityModel.packagePath}.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import  ${EntityModel.packagePath}.entity.${EntityModel.displayName}Entity;

/**
 * ${EntityModel.displayName} Data JPA repository interface.
 * @author Deepak Pal
 *
 */
@Repository
public interface ${EntityModel.displayName}Repository extends JpaRepository<${EntityModel.displayName}Entity, Long>{

	${EntityModel.displayName}Entity findByKeyid(String key);
}
