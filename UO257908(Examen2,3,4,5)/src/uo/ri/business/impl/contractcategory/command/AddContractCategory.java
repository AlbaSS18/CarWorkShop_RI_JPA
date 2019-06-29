package uo.ri.business.impl.contractcategory.command;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractCategory;

public class AddContractCategory implements Command<Void> {

	private ContractCategoryDto categoria;
	private CategoryRepository repo = Factory.repository.forContractCategory();

	public AddContractCategory(ContractCategoryDto dto) {
		this.categoria = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.numeroMenorCero(categoria.trieniumSalary, 
				"El triennium es negativo");
		BusinessCheck.numeroMenorCero(categoria.productivityPlus, 
				"La productividad es negativa");

		ContractCategory contratoCategoriaMismoNombre = 
				repo.findByName(categoria.name);
		BusinessCheck.isNull(contratoCategoriaMismoNombre, 
				"Ya existe una categoría de contrato con el mismo nombre");

		ContractCategory c = EntityAssembler.toEntity(categoria);
		repo.add(c);
		return null;
	}

}
