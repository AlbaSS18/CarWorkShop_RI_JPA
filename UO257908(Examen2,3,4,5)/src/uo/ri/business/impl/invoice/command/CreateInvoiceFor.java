package uo.ri.business.impl.invoice.command;

import java.util.List;

import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.exception.BusinessCheck;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;
import uo.ri.business.impl.util.DtoAssembler;
import uo.ri.business.repository.AveriaRepository;
import uo.ri.business.repository.FacturaRepository;
import uo.ri.conf.Factory;
import uo.ri.model.Averia;
import uo.ri.model.Factura;
import uo.ri.model.types.AveriaStatus;

public class CreateInvoiceFor implements Command<InvoiceDto>{

	private List<Long> idsAveria;
	private FacturaRepository repoFacturas = Factory.repository.forFactura();
	private AveriaRepository repoAverias = Factory.repository.forAveria();
	
	public CreateInvoiceFor(List<Long> idsAveria) {
		this.idsAveria = idsAveria;
	}

	@Override
	public InvoiceDto execute() throws BusinessException {
		Long numero = repoFacturas.getNextInvoiceNumber(); 
		List<Averia> averias = repoAverias.findByIds(idsAveria);
		comprobaciones(averias);
		
		Factura f = new Factura(numero, averias);
		repoFacturas.add(f);
		
		return DtoAssembler.toDto(f);
	}

	private void comprobaciones(List<Averia> averias) throws BusinessException {
		BusinessCheck.isTrue(averias.size()>0,"No hay averías para facturar");
		for(Averia averia: averias) {
			BusinessCheck.isTrue(
					averia.getStatus().equals(AveriaStatus.TERMINADA),
					"La avería está terminada por lo "
					+ "tanto no se puede facturar");
		}
	}

}
