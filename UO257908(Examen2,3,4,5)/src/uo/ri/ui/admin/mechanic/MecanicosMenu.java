package uo.ri.ui.admin.mechanic;

import alb.util.menu.BaseMenu;
import uo.ri.ui.admin.mechanic.action.AddMechanicAction;
import uo.ri.ui.admin.mechanic.action.DeleteMechanicAction;
import uo.ri.ui.admin.mechanic.action.ListActiveMechanicsAction;
import uo.ri.ui.admin.mechanic.action.ListMechanicsAction;
import uo.ri.ui.admin.mechanic.action.ListMechanicsCategoryLaboralAction;
import uo.ri.ui.admin.mechanic.action.ListMechanicsUsedRepuestoAction;
import uo.ri.ui.admin.mechanic.action.UpdateMechanicAction;

public class MecanicosMenu extends BaseMenu {

	public MecanicosMenu() {
		menuOptions = new Object[][] { 
			{"Administrador > Gestión de mecánicos", null},
			
			{ "Añadir mecánico", 				AddMechanicAction.class }, 
			{ "Modificar datos de mecánico", 	UpdateMechanicAction.class }, 
			{ "Eliminar mecánico", 				DeleteMechanicAction.class }, 
			{ "Listar mecánicos (activo)", 		ListActiveMechanicsAction.class },
			{ "Listar mecánicos (histórico)", 	ListMechanicsAction.class },
			{ "Listar mecánicos que han utilizado repuestos en averías de no más de un año", 	ListMechanicsUsedRepuestoAction.class },
			{ "Listar mecánicos de una categoría laboral", ListMechanicsCategoryLaboralAction.class}
			// Una opción de menú que devuelva todos los mecánicos que han reutilizado repuestos en averías de no más de un año.
		};
	}

}
