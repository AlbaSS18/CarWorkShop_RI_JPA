package uo.ri.business.impl.mechanic.command;

import java.util.List;

import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Mecanico;

public class FindMechanicsWithoutActiveContract implements Command<List<MechanicDto>> {

	private MecanicoRepository repo  = Factory.repository.forMechanic();
	private Long idCategoria;

	public FindMechanicsWithoutActiveContract(Long idCategoria) {
		this.idCategoria = idCategoria;
	}

	@Override
	public List<MechanicDto> execute() throws BusinessException {
		List<Mecanico> listaMecanico = repo.findMechanicsWithoutActiveContract(idCategoria);
		return DtoAssembler.toMechanicDtoList(listaMecanico);
	}
	
	

}
