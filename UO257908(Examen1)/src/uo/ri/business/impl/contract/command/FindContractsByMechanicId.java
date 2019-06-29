package uo.ri.business.impl.contract.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;

public class FindContractsByMechanicId implements Command<List<ContractDto>> {

	private MecanicoRepository repo = Factory.repository.forMechanic();
	private Long mecanicoId;

	public FindContractsByMechanicId(Long id) {
		this.mecanicoId = id;
	}

	@Override
	public List<ContractDto> execute() throws BusinessException {
		Mecanico meca = repo.findById(mecanicoId);
		BusinessCheck.isNotNull(meca, "No se ha encontrado el mecánico");
		// Lista de contratos
		List<Contract> listContr = new ArrayList<>();

		for (Iterator<Contract> it = meca.getContracts().iterator();
				it.hasNext();) {
			Contract x = (Contract) it.next();
			listContr.add(x);
		}

		return meca == null ? null : DtoAssembler.toContractDtoList(listContr);
	}

}
