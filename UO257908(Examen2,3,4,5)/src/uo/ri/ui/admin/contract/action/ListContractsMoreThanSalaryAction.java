package uo.ri.ui.admin.contract.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListContractsMoreThanSalaryAction implements Action {

	@Override
	public void execute() throws BusinessException {
	
		Double salary = Console.readDouble("Salario dado"); 
		
		ContractCrudService as = Factory.service.forContractCrud();
		List<ContractDto> contracts = as.findContractsMoreThanSalary( salary );
		
		for(ContractDto c: contracts) {
			Printer.printContract( c );
		}
	}
}
