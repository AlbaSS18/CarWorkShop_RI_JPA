package uo.ri.business.impl.mechanic.command;

import java.util.Date;


import alb.util.date.Dates;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Mecanico;

public class DeleteMechanic implements Command<Void>{

	private Long idMecanico;
	private MecanicoRepository repo = Factory.repository.forMechanic();
	
	public DeleteMechanic(Long idMecanico) {
		this.idMecanico = idMecanico;
	}

	public Void execute() throws BusinessException {		
		Mecanico m = repo.findById(idMecanico); 
		BusinessCheck.isNotNull(m, "No existe");
		checkCanBeDeleted(m);
		
//		if(m.getContracts()!=null || m.getAsignadas() !=null || m.getContracts()!=null) {
//			
//			Contract contratoActivoMecanico = m.getActiveContract();
//			if (contratoActivoMecanico != null) {
//				
//				Date mesAnterior = Dates.subMonths(Dates.now(), 1);
//				Date ultimoDia = Dates.lastDayOfMonth(mesAnterior);
//				
//				contratoActivoMecanico.markAsFinished(ultimoDia);
//				m.setNombre("INACTIVO_");
//			}
//		}
//		else {
//			repo.remove(m);
//		}
		repo.remove(m);
		
		return null;
	}

	private void checkCanBeDeleted(Mecanico m) throws BusinessException{
		m.setNombre("INACTIVO_");
		// Intervenciones
		BusinessCheck.isTrue(m.getIntervenciones().isEmpty(), 
				"No se puede borrar porque tiene intervenciones asignadas"); 
		 // Averías
		BusinessCheck.isTrue(m.getAsignadas().isEmpty(), 
				"No se puede borrar porque tiene averías asignadas");
		
//		Contract contratoActivoMecanico = m.getActiveContract();
//		if (contratoActivoMecanico != null) {
//			
//			Date mesAnterior = Dates.subMonths(Dates.now(), 1);
//			Date ultimoDia = Dates.lastDayOfMonth(mesAnterior);
//			
//			contratoActivoMecanico.markAsFinished(ultimoDia);
//			m.setNombre("INACTIVO_");
//			
//			//MechanicDto mechanicDto = DtoAssembler.toDto(m);
//		}
		
		// Contratos
		BusinessCheck.isTrue(m.getContracts().isEmpty(), 
				"No se puede borrar porque tiene contratos asignados"); 
	}

}
