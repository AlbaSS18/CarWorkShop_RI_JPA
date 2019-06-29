package uo.ri.ui.admin.contract;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.contract.action.AddContractAction;
import uo.ri.ui.admin.contract.action.DeleteContractAction;
import uo.ri.ui.admin.contract.action.FinishContractAction;
import uo.ri.ui.admin.contract.action.ListContractsMoreThanSalaryAction;
import uo.ri.ui.admin.contract.action.ListContractsOfMechanicAction;
import uo.ri.ui.admin.contract.action.UpdateContractAction;
import uo.ri.ui.admin.mechanic.action.ListMechanicsAction;

public class ContratosMenu extends BaseMenu {

	public ContratosMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de contratos", null},
			
			{ "Listar mecánicos", 				ListMechanicsAction.class },
			{ "Añadir contrato", 				AddContractAction.class }, 
			{ "Modificar contrato", 			UpdateContractAction.class }, 
			{ "Eliminar contrato", 				DeleteContractAction.class }, 
			{ "Finalizar un contrato", 			FinishContractAction.class }, 
			{ "Listar contratos de un mecánico", 		ListContractsOfMechanicAction.class },
			{ "Listar contratos cuyo salario sea mayor al dado", 		ListContractsMoreThanSalaryAction.class },
			// Crear una opción en el menú administrador para que cuando metas un salario te de todos los contratos cuyo salario sea mayor al dado.
		};
	}

}
