package uo.ri.business.impl.payroll.command;

import java.util.List;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.NominaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Payroll;

public class DeleteLastGeneratedPayrolls implements Command<Integer> {

	private NominaRepository repo = Factory.repository.forNomina();

	@Override
	public Integer execute() throws BusinessException {

		List<Payroll> listLastGeneratedPayrolls = 
				repo.findLastGeneratedPayrolls();

		for (Payroll p : listLastGeneratedPayrolls) {
			repo.remove(p);
		}

		return listLastGeneratedPayrolls.size();
	}

}
