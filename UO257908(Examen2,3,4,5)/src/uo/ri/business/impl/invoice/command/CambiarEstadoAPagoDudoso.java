package uo.ri.business.impl.invoice.command;

import java.util.Date;
import java.util.List;

import alb.util.date.Dates;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Factura;
import uo.ri.model.types.FacturaStatus;

public class CambiarEstadoAPagoDudoso implements Command<Integer>{
	
	private FacturaRepository repoFactura = Factory.repository.forFactura();

	@Override
	public Integer execute() throws BusinessException {
		int numeroCambios = 0;
		
		List<Factura> listFacturas = repoFactura.facturasMasAñoSinAbonar();
		
		for(Factura f: listFacturas) {
			Date fechaFactura = f.getFecha();
			int numeroDias = Dates.diffDays(Dates.now(), fechaFactura);
			if(numeroDias >= 365) {
				f.setStatus(FacturaStatus.PAGO_DUDOSO);
				numeroCambios++;
			}	
		}
		
		return numeroCambios;
	}

	

}
