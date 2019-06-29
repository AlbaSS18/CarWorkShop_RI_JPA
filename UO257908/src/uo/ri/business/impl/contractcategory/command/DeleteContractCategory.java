package uo.ri.business.impl.contractcategory.command;

import java.util.Set;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;

public class DeleteContractCategory implements Command<Void> {

	private Long id;
	private CategoryRepository repo = Factory.repository.forContractCategory();

	public DeleteContractCategory(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractCategory c = repo.findById(id);
		BusinessCheck.isNotNull(c, "No existe");
		checkCanBeDeleted(c);
		repo.remove(c);

		return null;
	}

	private void checkCanBeDeleted(ContractCategory c) 
			throws BusinessException {
		Set<Contract> listaDeContratos = c.getContract();
		BusinessCheck.isEmpty(listaDeContratos, "Tiene contratos asociados");
	}

}
