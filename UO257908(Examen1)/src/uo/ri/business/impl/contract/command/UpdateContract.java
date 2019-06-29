package uo.ri.business.impl.contract.command;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class UpdateContract implements Command<Void> {

	private ContractDto dto;
	private ContractRepository repo = Factory.repository.forContract();

	public UpdateContract(ContractDto dto) {
		this.dto = dto;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = repo.findById(dto.id);
		BusinessCheck.isNotNull(c, "No existe");
		BusinessCheck.isNotActive(c, "El contrato no está activo");
		BusinessCheck.numeroMenorCero(dto.yearBaseSalary, 
				"El salario base es menor que cero");
		BusinessCheck.endDateMenorStartDate(dto.endDate, dto.startDate,
				"La fecha final es menor que la fecha de inicio");
		c.setEndDate(dto.endDate);
		c.setBaseSalaryPerYear(dto.yearBaseSalary);
		return null;
	}

}
