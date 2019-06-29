package uo.ri.business.impl.contracttype.command;

import java.util.Set;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.ContractType;

public class DeleteContractType implements Command<Void>{

	private Long id;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	
	public DeleteContractType(Long id) {
		this.id = id;
	}
	@Override
	public Void execute() throws BusinessException {
		ContractType c = repo.findById(id);
		BusinessCheck.isNotNull(c, "No existe");
		checkCanBeDeleted(c);
		repo.remove(c);

		return null;
	}
	private void checkCanBeDeleted(ContractType c) throws BusinessException {
		Set<Contract> listaDeContratos = c.getContrato();
		BusinessCheck.isEmpty(listaDeContratos, "Tiene contratos asociados");
		
	}

}
