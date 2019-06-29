package uo.ri.business.impl.contract.command;

import java.util.Date;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;

public class FinishContract implements Command<Void> {

	private ContractRepository repo = Factory.repository.forContract();
	private Long id;
	private Date endDate;

	public FinishContract(Long id, Date endDate) {
		this.id = id;
		this.endDate = endDate;
	}

	@Override
	public Void execute() throws BusinessException {
		Contract c = repo.findById(id);
		BusinessCheck.isNotNull(c, "El contracto no existe");
		BusinessCheck.isNotActive(c, "El contrato no está activo");
		BusinessCheck.endDateMenorStartDate(endDate, c.getStartDate(), 
				"La fecha de fin es superior a la inicial");

		c.markAsFinished(endDate);

		return null;
	}

}
