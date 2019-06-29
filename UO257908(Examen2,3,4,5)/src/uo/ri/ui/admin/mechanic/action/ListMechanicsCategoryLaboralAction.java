package uo.ri.ui.admin.mechanic.action;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class ListMechanicsCategoryLaboralAction implements Action {

	@Override
	public void execute() throws Exception {
		
		Long idCategoria = Console.readLong("Id de categoria"); 
		
		MechanicCrudService as = Factory.service.forMechanicCrudService();
		List<MechanicDto> mechanics = as.findMechanicsWithoutActiveContract(idCategoria);
		
		Console.println("\nListar mecánicos de una categoría laboral\n");  
		for(MechanicDto m : mechanics) {
			Printer.printMechanic( m );
		}
	}

}
