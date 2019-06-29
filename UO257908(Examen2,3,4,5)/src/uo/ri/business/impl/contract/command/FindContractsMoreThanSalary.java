package uo.ri.business.impl.contract.command;

import java.util.List;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FindContractsMoreThanSalary implements Command<List<ContractDto>> {

	private ContractRepository repo = Factory.repository.forContract();
	private Double salary;

	public FindContractsMoreThanSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public List<ContractDto> execute() throws BusinessException {
		List<Contract> listaContratos = repo.findContractsMoreThanSalary(salary);
		return DtoAssembler.toContractDtoList(listaContratos);
	}

}
