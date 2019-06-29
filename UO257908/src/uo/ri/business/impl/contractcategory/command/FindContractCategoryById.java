package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class FindContractCategoryById implements Command<ContractCategoryDto> {

	private Long id;
	private CategoryRepository repo = Factory.repository.forContractCategory();

	public FindContractCategoryById(Long id) {
		this.id = id;
	}

	@Override
	public ContractCategoryDto execute() throws BusinessException {
		ContractCategory tipoContrato = repo.findById(id);
		return tipoContrato == null ? null : DtoAssembler.toDto(tipoContrato);
	}

}
