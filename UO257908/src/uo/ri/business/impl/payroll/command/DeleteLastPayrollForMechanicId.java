package uo.ri.business.impl.payroll.command;


import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.business.repository.NominaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;
import uo.ri.model.Payroll;

public class DeleteLastPayrollForMechanicId implements Command<Void> {
	
	private Long idMecanico;
	private NominaRepository repo = Factory.repository.forNomina();
	private MecanicoRepository repoMeca = Factory.repository.forMechanic();


	public DeleteLastPayrollForMechanicId(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	@Override
	public Void execute() throws BusinessException {
		Mecanico mecanico = repoMeca.findById(idMecanico);
		BusinessCheck.isNotNull(mecanico, "El mecánico no existe");
		
		Payroll ultimaNominaGeneradaPorElMecanico = 
				repo.findLastPayrollByMechanicId(idMecanico);		
		BusinessCheck.isNotNull(ultimaNominaGeneradaPorElMecanico, 
				"El mecánico no tiene nóminas generadas");
		
		repo.remove(ultimaNominaGeneradaPorElMecanico);
		return null;
	}

	

}
