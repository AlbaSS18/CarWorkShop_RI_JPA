package uo.ri.ui.cash.action;

import java.util.List;

import alb.util.menu.Action;
import uo.ri.business.InvoiceService;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.conf.Factory;
import uo.ri.ui.util.Printer;

public class FacturasMasAñoYSinAbonar implements Action {

	@Override
	public void execute() throws BusinessException {
			
		
		InvoiceService is = Factory.service.forInvoice();
		List<InvoiceDto> invoices = is.findInvoicesMoreYearAndSinAbonar();
		
		for(InvoiceDto i: invoices) {
			Printer.printInvoice(i);
		}
	}

}
