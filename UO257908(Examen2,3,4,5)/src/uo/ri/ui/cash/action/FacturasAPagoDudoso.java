package uo.ri.ui.cash.action;

import alb.util.console.Console;
import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.conf.Factory;

public class FacturasAPagoDudoso implements Action{
	
	@Override
	public void execute() throws Exception {

		InvoiceService cs = Factory.service.forInvoice();
		
		int qty = cs.cambiarEstadoAPagoDudoso();
		
		Console.printf("Se han cambiado %d nóminas%n", qty);
	}
			
}

