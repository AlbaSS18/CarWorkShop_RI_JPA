package uo.ri.business.impl.mechanic.command;

import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
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
		repo.remove(m);
	
		return null;
	}

	private void checkCanBeDeleted(Mecanico m) throws BusinessException{
		// Intervenciones
		BusinessCheck.isTrue(m.getIntervenciones().isEmpty(), 
				"No se puede borrar porque tiene intervenciones asignadas"); 
		 // Averías
		BusinessCheck.isTrue(m.getAsignadas().isEmpty(), 
				"No se puede borrar porque tiene averías asignadas");
		// Contratos
		BusinessCheck.isTrue(m.getContracts().isEmpty(), 
				"No se puede borrar porque tiene contratos asignados"); 
	}

}
