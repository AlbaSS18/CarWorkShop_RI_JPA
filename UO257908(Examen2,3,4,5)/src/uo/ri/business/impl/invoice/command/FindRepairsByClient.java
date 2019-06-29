package uo.ri.business.impl.invoice.command;

import java.util.ArrayList;
import java.util.List;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.ClienteRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Cliente;

public class FindRepairsByClient implements Command<List<BreakdownDto>> {

	private String dni;
	private ClienteRepository repo = Factory.repository.forCliente();
	private AveriaRepository repoAveria = Factory.repository.forAveria();
	
	public FindRepairsByClient(String dni) {
		this.dni = dni;
	}

	@Override
	public List<BreakdownDto> execute() throws BusinessException {
		Cliente c = repo.findByDni(dni);
		BusinessCheck.isNotNull(c,"No se ha encontrado el cliente");
		List<Averia> listaAveriasDeCadaVehiculo = 
				repoAveria.findAveriaByDni(dni);
		BusinessCheck.isNotNull(listaAveriasDeCadaVehiculo, 
				"No tiene averías");
		
		List<BreakdownDto> listaAveriasDto = new ArrayList<>();
		for(Averia averia: listaAveriasDeCadaVehiculo) {
			listaAveriasDto.add(DtoAssembler.toDto(averia));
		}
		
		
		return listaAveriasDto;
	}

}
