package uo.ri.business.impl.contractcategory.command;

import java.util.List;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class FindAllContractCategories 
	implements Command<List<ContractCategoryDto>> {

	private CategoryRepository repo = Factory.repository.forContractCategory();

	@Override
	public List<ContractCategoryDto> execute() throws BusinessException {
		List<ContractCategory> listaCategoriaContratos = repo.findAll();
		return DtoAssembler.toContractCategoryDtoList(listaCategoriaContratos);
	}

}
