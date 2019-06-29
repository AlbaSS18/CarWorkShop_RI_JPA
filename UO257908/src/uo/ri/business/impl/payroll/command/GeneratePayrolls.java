package uo.ri.business.impl.payroll.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.ContractRepository;
import uo.ri.business.repository.IntervencionRepository;
import uo.ri.business.repository.NominaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Contract;
import uo.ri.model.Intervencion;
import uo.ri.model.Payroll;

public class GeneratePayrolls implements Command<Integer>{
	
	private ContractRepository repoContra = Factory.repository.forContract();
	private NominaRepository repoNominas = Factory.repository.forNomina();
	private IntervencionRepository repoIntervencion = 
			Factory.repository.forIntervencion();

	@Override
	public Integer execute() throws BusinessException {
		int numeroNominasGeneradas = 0;
		
		//Día final del mes
		Date fechaNom = Dates.lastDayOfMonth(Dates.subMonths(Dates.now(), 1));
		//Día inicio del mes
		Date fechaInicioMes = Dates.firstDayOfMonth(fechaNom); 
		
		//Contratos activos o contratos extinguidos
		List<Contract> listaContratos = 
				repoContra.findContractsActiveOrExtinguished(fechaNom); 
		
		for(Contract contract: listaContratos) {
			// Última nómina generada para ese contrato
			Payroll lastPayroll = contract.getLastPayroll();
			
			 /*Si no existe la última nómina generada o 
			 si la última nómina generada fue de otro mes distinto, 
			 entonces deberá generarse una nómina 
			 y además la fecha de inicio del contrato 
			 deberá ser antes que la fecha de la nómina*/
			if ((lastPayroll == null || 
					!Dates.toString(fechaNom).equals
					(Dates.toString(lastPayroll.getDate())))
					&& contract.getStartDate().before(fechaNom)) {
				//Intervenciones del mecánico en el 
				//mes en el que se pide generar una nómina
				List<Intervencion> listIntervencion =  
						repoIntervencion.findByMechanicIdBetweenDates(
								contract.getMechanic().getId(), 
								fechaInicioMes, 
								fechaNom);
				
				Payroll p = new Payroll(contract, 
						Dates.now(), 
						listIntervencion.size());
				repoNominas.add(p);
				numeroNominasGeneradas++;
			}
		}
		
		return numeroNominasGeneradas;
	}

	

}
