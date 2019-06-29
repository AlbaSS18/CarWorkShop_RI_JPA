package uo.ri.business.impl.contracttype.command;

import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.conf.Factory;
import uo.ri.model.ContractType;

public class AddContractType implements Command<Void> {

	private ContractTypeDto tipoDeContrato;
	private ContractTypeRepository repo = Factory.repository.forContractType();

	
	public AddContractType(ContractTypeDto tipoDeContrato) {
		this.tipoDeContrato = tipoDeContrato;
	}
	
	
	@Override
	public Void execute() throws BusinessException {
		BusinessCheck.numeroMenorCero(tipoDeContrato.compensationDays,
				"El número de días es negativo");
		ContractType contractSameName = repo.findByName(tipoDeContrato.name);
		BusinessCheck.isNull(contractSameName, 
				"Ya existe un tipo de contrato con el mismo nombre");
		
		ContractType c = EntityAssembler.toEntity(tipoDeContrato);
		repo.add(c);
		return null;
	}
}
