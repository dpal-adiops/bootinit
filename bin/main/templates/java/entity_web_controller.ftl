package ${EntityModel.packagePath}.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adiops.apigateway.common.response.RestException;
import ${EntityModel.packagePath}.resourceobject.${EntityModel.displayName}RO;
import ${EntityModel.packagePath}.service.${EntityModel.displayName}Service;

/**
 * The web controller class for ${EntityModel.displayName} 
 * @author Deepak Pal
 *
 */
@Controller
public class ${EntityModel.displayName}WebController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final int ROW_PER_PAGE = 30;
	
	@Autowired
	${EntityModel.displayName}Service m${EntityModel.displayName}Service;

	@GetMapping(value = "/admin/web/${EntityModel.name}s")
	public String get${EntityModel.displayName}s(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
		List<${EntityModel.displayName}RO> ${EntityModel.name}s = m${EntityModel.displayName}Service.findAll(pageNumber, ROW_PER_PAGE);
		 
	    long count = m${EntityModel.displayName}Service.count();
	    boolean hasPrev = pageNumber > 1;
	    boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
	    model.addAttribute("${EntityModel.name}s", ${EntityModel.name}s);
	    model.addAttribute("hasPrev", hasPrev);
	    model.addAttribute("prev", pageNumber - 1);
	    model.addAttribute("hasNext", hasNext);
	    model.addAttribute("next", pageNumber + 1);
	    model.addAttribute("selectedMenu", "${EntityModel.name}");
	    return "admin/${EntityModel.name}/${EntityModel.name}-list";
	}

	@GetMapping(value = { "/admin/web/${EntityModel.name}s/add" })
	public String showAdd${EntityModel.displayName}(Model model) {
		${EntityModel.displayName}RO t${EntityModel.displayName}RO = new ${EntityModel.displayName}RO();
	    model.addAttribute("selectedMenu", "${EntityModel.name}");
	    model.addAttribute("${EntityModel.name}RO", t${EntityModel.displayName}RO);
	    return "admin/${EntityModel.name}/${EntityModel.name}-add";
	}

	@PostMapping(value = "/admin/web/${EntityModel.name}s/add")
	public String add${EntityModel.displayName}(@ModelAttribute ${EntityModel.displayName}RO t${EntityModel.displayName}RO,Model model)  {
		try {
		 t${EntityModel.displayName}RO=m${EntityModel.displayName}Service.createOrUpdate${EntityModel.displayName}(t${EntityModel.displayName}RO);
		} catch (RestException e) {
			model.addAttribute("error", e.getMessage());
		}
		
		
		model.addAttribute("${EntityModel.name}RO", t${EntityModel.displayName}RO);
		model.addAttribute("selectedMenu", "${EntityModel.name}");
		return "admin/${EntityModel.name}/${EntityModel.name}-edit";
	}

	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}" })
	public String showEdit${EntityModel.displayName}(Model model, @PathVariable Long ${EntityModel.name}Id) throws RestException {
		${EntityModel.displayName}RO t${EntityModel.displayName}RO = m${EntityModel.displayName}Service.get${EntityModel.displayName}ById(${EntityModel.name}Id);
	    model.addAttribute("selectedMenu", "${EntityModel.name}");
	    model.addAttribute("${EntityModel.name}RO", t${EntityModel.displayName}RO);
	    return "admin/${EntityModel.name}/${EntityModel.name}-edit";
	}

	

	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}/delete" })
	public String delete${EntityModel.displayName}ById(Model model, @PathVariable Long ${EntityModel.name}Id) throws RestException {
		m${EntityModel.displayName}Service.delete${EntityModel.displayName}ById(${EntityModel.name}Id);
		return "redirect:/admin/web/${EntityModel.name}s";
	}
	
	<#list EntityModel.manyToManyRelations as relation>	 
	
	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}/${relation.name}s" })
	public String get${EntityModel.displayName}${relation.displayName}s(Model model, @PathVariable Long ${EntityModel.name}Id) throws RestException {
		model.addAttribute("${EntityModel.name}RO", m${EntityModel.displayName}Service.get${EntityModel.displayName}ById(${EntityModel.name}Id));
		model.addAttribute("${relation.name}s", m${EntityModel.displayName}Service.find${EntityModel.displayName}${relation.displayName}s(${EntityModel.name}Id));
		model.addAttribute("selectedMenu", "${EntityModel.name}");
		return "admin/${EntityModel.name}/${relation.name}/${relation.name}-list";
	}
	
	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}/${relation.name}s/assign" })
	public String assign${relation.displayName}s(Model model, @PathVariable Long ${EntityModel.name}Id) throws RestException {
		model.addAttribute("${EntityModel.name}RO", m${EntityModel.displayName}Service.get${EntityModel.displayName}ById(${EntityModel.name}Id));		
		model.addAttribute("${relation.name}s", m${EntityModel.displayName}Service.findUnassign${EntityModel.displayName}${relation.displayName}s(${EntityModel.name}Id));
		model.addAttribute("selectedMenu", "${EntityModel.name}");
		return "admin/${EntityModel.name}/${relation.name}/${relation.name}-assign";
	}
	
	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}/${relation.name}s/{${relation.name}Id}/assign" })
	public String get${relation.displayName}s(Model model, @PathVariable Long ${EntityModel.name}Id, @PathVariable Long ${relation.name}Id) throws RestException {
		m${EntityModel.displayName}Service.add${EntityModel.displayName}${relation.displayName}(${EntityModel.name}Id, ${relation.name}Id);
		model.addAttribute("${EntityModel.name}RO", m${EntityModel.displayName}Service.get${EntityModel.displayName}ById(${EntityModel.name}Id));
		model.addAttribute("${relation.name}s", m${EntityModel.displayName}Service.find${EntityModel.displayName}${relation.displayName}s(${EntityModel.name}Id));
		model.addAttribute("selectedMenu", "${EntityModel.name}");
		return "admin/${EntityModel.name}/${relation.name}/${relation.name}-list";
	}
	
	@GetMapping(value = { "/admin/web/${EntityModel.name}s/{${EntityModel.name}Id}/${relation.name}s/{${relation.name}Id}/unassign" })
	public String unassign${relation.displayName}s(Model model, @PathVariable Long ${EntityModel.name}Id, @PathVariable Long ${relation.name}Id) throws RestException {
		m${EntityModel.displayName}Service.unassign${EntityModel.displayName}${relation.displayName}(${EntityModel.name}Id, ${relation.name}Id);
		model.addAttribute("${EntityModel.name}RO", m${EntityModel.displayName}Service.get${EntityModel.displayName}ById(${EntityModel.name}Id));
		model.addAttribute("${relation.name}s", m${EntityModel.displayName}Service.find${EntityModel.displayName}${relation.displayName}s(${EntityModel.name}Id));
		model.addAttribute("selectedMenu", "${EntityModel.name}");
		return "admin/${EntityModel.name}/${relation.name}/${relation.name}-list";
	}
	
	</#list>

}
