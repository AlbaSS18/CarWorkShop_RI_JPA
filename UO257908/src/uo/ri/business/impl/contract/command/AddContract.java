package uo.ri.business.impl.contract.command;

import java.util.Date;

import alb.util.date.Dates;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.CategoryRepository;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Mecanico;

public class AddContract implements Command<Void> {

	private ContractDto contrato;
	private ContractRepository repo = Factory.repository.forContract();
	private MecanicoRepository repoMecanico = Factory.repository.forMechanic();
	private CategoryRepository repoCategory = 
			Factory.repository.forContractCategory();
	private ContractTypeRepository repoType = 
			Factory.repository.forContractType();

	public AddContract(ContractDto c) {
		this.contrato = c;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico mecanico = repoMecanico.findById(contrato.mechanicId);
		BusinessCheck.isNotNull(mecanico, "El mecánico no existe");
		ContractCategory categoria = 
				repoCategory.findById(contrato.categoryId);
		BusinessCheck.isNotNull(categoria, 
				"La categoría del contrato no existe");
		ContractType tipo = repoType.findById(contrato.typeId);
		BusinessCheck.isNotNull(tipo, "El tipo de contrato no existe");

		BusinessCheck.endDateMenorStartDate(contrato.endDate, 
				contrato.startDate,
				"La fecha fin de contrato es anterior a la fecha de inicio");
		BusinessCheck.numeroMenorCero(contrato.yearBaseSalary, 
				"El salario base es negativo");

		Contract contratoActivoMecanico = mecanico.getActiveContract();
		if (contratoActivoMecanico != null) {
			Date fechaConMesAnterior = Dates.addMonths(contrato.startDate, -1);
			Date fechaConUltimoDia = Dates.lastDayOfMonth(fechaConMesAnterior);

			contratoActivoMecanico.markAsFinished(fechaConUltimoDia);
		}

		Contract c = 
				EntityAssembler.toEntity(mecanico, contrato, categoria, tipo);
		repo.add(c);
		return null;
	}
}
