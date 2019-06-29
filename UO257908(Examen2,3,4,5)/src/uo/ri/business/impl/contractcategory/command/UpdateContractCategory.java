package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class UpdateContractCategory implements Command<Void> {

	private ContractCategoryDto dto;
	private CategoryRepository repo = Factory.repository.forContractCategory();

	public UpdateContractCategory(ContractCategoryDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory categoria = repo.findById(dto.id);
		BusinessCheck.isNotNull(categoria, "No existe");

		BusinessCheck.numeroMenorCero(dto.trieniumSalary, 
				"El triennium es negativo");
		BusinessCheck.numeroMenorCero(dto.productivityPlus, 
				"La productividad es negativa");

		categoria.setProductivityPlus(dto.productivityPlus);
		categoria.setTrieniumSalary(dto.trieniumSalary);

		return null;

	}

}
