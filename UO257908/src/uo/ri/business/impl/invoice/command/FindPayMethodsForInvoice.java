package uo.ri.business.impl.invoice.command;

import java.util.List;

import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.Command;

public class FindPayMethodsForInvoice 
	implements  Command<List<PaymentMeanDto>>{
	
	public FindPayMethodsForInvoice(Long idInvoiceDto) {
		
	}

	@Override
	public List<PaymentMeanDto> execute() throws BusinessException {
		return null;
	}

	
}
