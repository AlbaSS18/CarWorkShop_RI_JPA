package uo.ri.business.impl.invoice;

import java.util.List;
import java.util.Map;

import uo.ri.business.InvoiceService;
import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.CommandExecutor;
import uo.ri.business.impl.invoice.command.CambiarEstadoAPagoDudoso;
import uo.ri.business.impl.invoice.command.CreateInvoiceFor;
import uo.ri.business.impl.invoice.command.FindInvoice;
import uo.ri.business.impl.invoice.command.FindInvoicesMoreYearAndSinAbonar;
import uo.ri.business.impl.invoice.command.FindPayMethodsForInvoice;
import uo.ri.business.impl.invoice.command.FindRepairsByClient;
import uo.ri.business.impl.invoice.command.SettleInvoice;
import uo.ri.conf.Factory;

public class InvoiceServiceImpl implements InvoiceService {

	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public InvoiceDto createInvoiceFor(List<Long> idsAveria)
			throws BusinessException {

		return executor.execute( new CreateInvoiceFor( idsAveria ));
	}

	@Override
	public InvoiceDto findInvoice(Long numeroInvoiceDto) 
			throws BusinessException {
		return executor.execute(new FindInvoice(numeroInvoiceDto));
	}

	@Override
	public List<PaymentMeanDto> findPayMethodsForInvoice(Long idInvoiceDto)
			throws BusinessException {
		return executor.execute(new FindPayMethodsForInvoice(idInvoiceDto));
	}

	@Override
	public void settleInvoice(Long idInvoiceDto, Map<Long, Double> cargos)
			throws BusinessException {
		executor.execute(new SettleInvoice(idInvoiceDto, cargos));
	}

	@Override
	public List<BreakdownDto> findRepairsByClient(String dni) 
			throws BusinessException {
		return executor.execute(new FindRepairsByClient(dni));
	}

	@Override
	public List<InvoiceDto> findInvoicesMoreYearAndSinAbonar() throws BusinessException {
		return executor.execute(new FindInvoicesMoreYearAndSinAbonar());
	}

	@Override
	public int cambiarEstadoAPagoDudoso() throws BusinessException {
		return executor.execute(new CambiarEstadoAPagoDudoso());
	}

}
