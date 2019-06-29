package uo.ri.business.impl.contract.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class DeleteContract implements Command<Void> {

	private Long id;
	private ContractRepository repo = Factory.repository.forContract();

	public DeleteContract(Long id) {
		this.id = id;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = repo.findById(id);
		BusinessCheck.isNotNull(c, "No existe");
		checkCanBeDeleted(c);
		repo.remove(c);

		return null;
	}

	private void checkCanBeDeleted(Contract c) throws BusinessException {
		BusinessCheck.isActive(c, "El contrato está activo");
		BusinessCheck.nominasGeneradas(c, 
				"El contrato tiene nóminas generadas durante su vigencia");
		BusinessCheck.haRealizadoIntervenciones(c, 
				"El mecánico ha realizado intervenciones durante su vigencia");
	}

}
