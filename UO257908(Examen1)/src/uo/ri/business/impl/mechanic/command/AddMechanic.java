package uo.ri.business.impl.mechanic.command;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.EntityAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;


public class AddMechanic implements Command<Void>{

	private MechanicDto dto;
	private MecanicoRepository repo = Factory.repository.forMechanic();

	public AddMechanic(MechanicDto mecanico) {
		this.dto = mecanico;
	}

	public Void execute() throws BusinessException {
		checkNotRepeatedDni(dto.dni);
		Mecanico m = EntityAssembler.toEntity(dto);
		
		repo.add(m);
		return null;
	}

	private void checkNotRepeatedDni(String dni) throws BusinessException {
		Mecanico mecanico = repo.findByDni(dni);
		BusinessCheck.isNull(mecanico, "Ya hay un mecánico con ese DNI");
	}

}
