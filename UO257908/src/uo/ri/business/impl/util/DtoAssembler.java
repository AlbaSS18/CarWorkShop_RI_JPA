package uo.ri.business.impl.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uo.ri.business.dto.BreakdownDto;
import uo.ri.business.dto.CardDto;
import uo.ri.business.dto.CashDto;
import uo.ri.business.dto.ClientDto;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.dto.InvoiceDto;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.dto.PaymentMeanDto;
import uo.ri.business.dto.PayrollDto;
import uo.ri.business.dto.VoucherDto;
import uo.ri.model.Averia;
import uo.ri.model.Bono;
import uo.ri.model.Cliente;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.Factura;
import uo.ri.model.Mecanico;
import uo.ri.model.MedioPago;
import uo.ri.model.Metalico;
import uo.ri.model.Payroll;
import uo.ri.model.TarjetaCredito;

public class DtoAssembler {

	public static ClientDto toDto(Cliente c) {
		 ClientDto dto = new ClientDto();
		 
		 dto.id = c.getId();
		 dto.dni = c.getDni();
		 dto.name = c.getNombre();
		 dto.surname = c.getApellidos();
		 
		 return dto;
	}

	public static List<ClientDto> toClientDtoList(List<Cliente> clientes) {
		List<ClientDto> res = new ArrayList<>();
		for(Cliente c: clientes) {
			res.add( DtoAssembler.toDto( c ) );
		}
		return res;
	}

	public static MechanicDto toDto(Mecanico m) {
		MechanicDto dto = new MechanicDto();
		dto.id = m.getId();
		dto.dni = m.getDni();
		dto.name = m.getNombre();
		dto.surname = m.getApellidos();
		return dto;
	}

	public static List<MechanicDto> toMechanicDtoList(List<Mecanico> list) {
		List<MechanicDto> res = new ArrayList<>();
		for(Mecanico m: list) {
			res.add( toDto( m ) );
		}
		return res;
	}

	public static List<VoucherDto> toVoucherDtoList(List<Bono> list) {
		List<VoucherDto> res = new ArrayList<>();
		for(Bono b: list) {
			res.add( toDto( b ) );
		}
		return res;
	}

	public static VoucherDto toDto(Bono b) {
		VoucherDto dto = new VoucherDto();
		dto.id = b.getId();
		dto.clientId = b.getCliente().getId();
		dto.accumulated = b.getAcumulado();
		dto.code = b.getCodigo();
		dto.description = b.getDescripcion();
		dto.available = b.getDisponible();
		return dto;
	}

	public static CardDto toDto(TarjetaCredito tc) {
		CardDto dto = new CardDto();
		dto.id = tc.getId();
		dto.clientId = tc.getCliente().getId();
		dto.accumulated = tc.getAcumulado();
		dto.cardNumber = tc.getNumero();
		dto.cardExpiration = tc.getValidez();
		dto.cardType = tc.getTipo();
		return dto;
	}

	public static CashDto toDto(Metalico m) {
		CashDto dto = new CashDto();
		dto.id = m.getId();
		dto.clientId = m.getCliente().getId();
		dto.accumulated = m.getAcumulado();
		return dto;
	}

	public static InvoiceDto toDto(Factura factura) {
		InvoiceDto dto = new InvoiceDto();
		dto.id = factura.getId();
		dto.number = factura.getNumero();
		dto.date = factura.getFecha();
		dto.total = factura.getImporte();
		dto.vat = factura.getIva();
		dto.status = factura.getStatus().toString();
		return dto;
	}

	public static List<PaymentMeanDto> toPaymentMeanDtoList(List<MedioPago> list) {
		return list.stream()
				.map( mp -> toDto( mp ) )
				.collect( Collectors.toList() );
	}

	private static PaymentMeanDto toDto(MedioPago mp) {
		if (mp instanceof Bono) {
			return toDto( (Bono) mp );
		}
		else if (mp instanceof TarjetaCredito) {
			return toDto( (TarjetaCredito) mp );
		}
		else if (mp instanceof Metalico) {
			return toDto( (Metalico) mp);
		}
		else {
			throw new RuntimeException("Unexpected type of payment mean");
		}
	}

	public static List<BreakdownDto> toBreakdownDtoList(List<Averia> list) {
		return list.stream()
				.map( a -> toDto( a ) )
				.collect( Collectors.toList() );
	}
	
	public static BreakdownDto toDto(Averia a) {
		BreakdownDto dto = new BreakdownDto();
		dto.id = a.getId();
		dto.invoiceId = a.getFactura().getId();
		dto.vehicleId = a.getVehiculo().getId();
		dto.description = a.getDescripcion();
		dto.date = a.getFecha();
		dto.total = a.getImporte();
		dto.status = a.getStatus().toString();
		return dto;
	}
	
	public static ContractTypeDto toDto(ContractType tipoDeContrato) {
		ContractTypeDto dto = new ContractTypeDto();
		dto.id = tipoDeContrato.getId();
		dto.compensationDays = tipoDeContrato.getCompensationDays();
		dto.name = tipoDeContrato.getName();
		
		return dto;
	}
	
	public static List<ContractTypeDto> toContractTypeDtoList(List<ContractType> list) {
		List<ContractTypeDto> res = new ArrayList<>();
		for(ContractType m: list) {
			res.add( toDto( m ) );
		}
		return res;
	}
	
	public static ContractDto toDto(Contract contrato) {
		ContractDto dto = new ContractDto();
		dto.mechanicId = contrato.getMechanic().getId();
		dto.typeId = contrato.getContractType().getId();
		dto.categoryId = contrato.getContractCategory().getId();
		dto.startDate = contrato.getStartDate();
		dto.endDate = contrato.getEndDate();
		dto.yearBaseSalary = contrato.getBaseSalaryPerYear();
		dto.id = contrato.getId();
		dto.compensation = contrato.getCompensation();
		dto.status = contrato.getStatus().name();
		dto.dni = contrato.getMechanic().getDni();
		dto.categoryName = contrato.getContractCategory().getName();
		dto.typeName = contrato.getContractType().getName();
		dto.numeroNominas = contrato.getPayrolls().size();
		
		return dto;
	}
	
	public static List<ContractDto> toContractDtoList(List<Contract> list) {
		List<ContractDto> res = new ArrayList<>();
		for(Contract m: list) {
			res.add( toDto( m ) );
		}
		return res;
	}

	public static ContractCategoryDto toDto(ContractCategory categoriaContrato) {
		ContractCategoryDto dto = new ContractCategoryDto();
		dto.id = categoriaContrato.getId();
		dto.name = categoriaContrato.getName();
		dto.trieniumSalary = categoriaContrato.getTrieniumSalary();
		dto.productivityPlus = categoriaContrato.getProductivityPlus();
		return dto;
	}
	
	public static List<ContractCategoryDto> toContractCategoryDtoList(List<ContractCategory> listaDeCategoriaDeContratos) {
		List<ContractCategoryDto> res = new ArrayList<>();
		for(ContractCategory m: listaDeCategoriaDeContratos) {
			res.add( toDto( m ) );
		}
		return res;
	}

	public static PayrollDto toDto(Payroll payroll) {
		PayrollDto dto = new PayrollDto();
		dto.id = payroll.getId();
		dto.baseSalary = payroll.getBaseSalary();
		dto.date = payroll.getDate();
		dto.extraSalary = payroll.getExtraSalary();
		dto.productivity = payroll.getProductivity();
		dto.triennium = payroll.getTriennium();
		dto.irpf = payroll.getIrpf();
		dto.socialSecurity = payroll.getSocialSecurity();
		dto.grossTotal = payroll.getGrossTotal();
		dto.discountTotal = payroll.getDiscountTotal();
		dto.netTotal = payroll.getNetTotal();
		
		return dto;
	}

	public static List<PayrollDto> toPayrollDtoList(List<Payroll> listaNominas) {
		List<PayrollDto> res = new ArrayList<>();
		for(Payroll m: listaNominas) {
			res.add( toDto( m ) );
		}
		return res;
	}

}
