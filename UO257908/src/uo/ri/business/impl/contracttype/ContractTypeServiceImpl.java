package uo.ri.business.impl.contracttype;

import java.util.List;

import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.contracttype.command.AddContractType;
import uo.ri.business.impl.contracttype.command.DeleteContractType;
import uo.ri.business.impl.contracttype.command.FindAllContractTypes;
import uo.ri.business.impl.contracttype.command.FindContractTypeById;
import uo.ri.business.impl.contracttype.command.UpdateContractType;
import uo.ri.conf.Factory;

public class ContractTypeServiceImpl implements ContractTypeCrudService{

	private CommandExecutor executor = Factory.executor.forExecutor();
	
	@Override
	public void addContractType(ContractTypeDto tipoDeContrato) 
			throws BusinessException {
		executor.execute(new AddContractType(tipoDeContrato));
	}

	@Override
	public void deleteContractType(Long id) throws BusinessException {
		executor.execute(new DeleteContractType(id));
	}

	@Override
	public void updateContractType(ContractTypeDto tipoDeContrato ) 
			throws BusinessException {
		executor.execute(new UpdateContractType(tipoDeContrato));
	}

	@Override
	public ContractTypeDto findContractTypeById(Long id) 
			throws BusinessException {
		return executor.execute(new FindContractTypeById(id));
	}

	@Override
	public List<ContractTypeDto> findAllContractTypes() 
			throws BusinessException {
		return executor.execute(new FindAllContractTypes());
	}

}
