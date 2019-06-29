package uo.ri.business.impl.invoice.command;

import java.util.Map;

import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;

public class SettleInvoice implements Command<Void> {

	public SettleInvoice(Long idInvoiceDto, Map<Long, Double> cargos) {

	}

	@Override
	public Void execute() throws BusinessException {
		return null;
	}

}
