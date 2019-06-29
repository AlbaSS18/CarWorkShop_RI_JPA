package uo.ri.business.impl.contracttype.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class UpdateContractType implements Command<Void> {
	
	private ContractTypeDto tipoDeContrato;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	
	public UpdateContractType(ContractTypeDto tipoDeContrato) {
		this.tipoDeContrato = tipoDeContrato;
	}

	@Override
	public Void execute() throws BusinessException {
		ContractType tipoDeContrato2 = repo.findById(tipoDeContrato.id);
		BusinessCheck.isNotNull(tipoDeContrato2,"No existe");
		BusinessCheck.numeroMenorCero(tipoDeContrato.compensationDays, 
				"El número de días es negativo");
		tipoDeContrato2.setCompensationDays(tipoDeContrato.compensationDays);
		
		return null;
		
	}

}
