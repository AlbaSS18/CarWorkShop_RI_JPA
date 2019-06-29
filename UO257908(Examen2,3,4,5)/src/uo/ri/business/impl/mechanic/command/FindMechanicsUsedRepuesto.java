package uo.ri.business.impl.mechanic.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.MecanicoRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Mecanico;

public class FindMechanicsUsedRepuesto implements Command<List<MechanicDto>>{

	private MecanicoRepository repo  = Factory.repository.forMechanic();
	private AveriaRepository repoAverias = Factory.repository.forAveria();
	

	public List<MechanicDto> execute() throws BusinessException {
		
		List<Averia> averia = repoAverias.findAll();
		
		
		List<Averia> averiaLessYear = new ArrayList<>();
		for(Averia a: averia) {
			Date fechaAveria = a.getFecha();
			int dias = Dates.diffDays(Dates.now(), fechaAveria);
			if(dias<365) {
				averiaLessYear.add(a);
			}
		}
		
		List<Mecanico> listaMecanico = repo.findMechanicsUsedRepuesto(averiaLessYear);

		
		return DtoAssembler.toMechanicDtoList(listaMecanico);
	}

}
