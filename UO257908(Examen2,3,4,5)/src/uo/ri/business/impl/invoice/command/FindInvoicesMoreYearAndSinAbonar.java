package uo.ri.business.impl.invoice.command;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;

public class FindInvoicesMoreYearAndSinAbonar implements Command<List<InvoiceDto>>{

	private FacturaRepository repoFactura = Factory.repository.forFactura();
	
	@Override
	public List<InvoiceDto> execute() throws BusinessException {
		List<Factura> listaFacturas = repoFactura.findInvoicesMoreYearAndSinAbonar();
		
		List<Factura> listaFactursaMoreYear = new ArrayList<>();
		//Cogemos las que tengan más de un año
		for(Factura f: listaFacturas) {
			Date fechaFactura = f.getFecha();
			Date fechaHoy = Dates.now();
			
			int dias = Dates.diffDays(fechaHoy, fechaFactura);
			
			if(dias>=365) {
				listaFactursaMoreYear.add(f);
			}
		}
		
		
		return DtoAssembler.toFacturaDtoList(listaFactursaMoreYear); 
	}

}
