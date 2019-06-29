package uo.ri.business.exception;

import java.util.Date;
import java.util.List;
import java.util.Set;

import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.ContractType;
import uo.ri.model.types.ContractStatus;

public class BusinessCheck {

	public static void isNull(Object o, String errorMsg) throws BusinessException {
		isTrue( o == null, errorMsg); 
	}

	public static void isNull(Object o) throws BusinessException {
		isTrue( o == null, o.getClass().getName() + " cannot be null here"); 
	}

	public static void isNotNull(Object o, String errorMsg) throws BusinessException {
		isTrue( o != null, errorMsg); 
	}

	public static void isNotNull(Object o) throws BusinessException {
		isTrue( o != null, o.getClass().getName() + " cannot be null here"); 
	}

	public static void isFalse(boolean condition) throws BusinessException {
		isTrue( ! condition, "Invalid assertion");
	}

	public static void isFalse(boolean condition, String errorMsg) throws BusinessException {
		isTrue( ! condition, errorMsg);
	}

	public static void isTrue(boolean condition) throws BusinessException {
		isTrue(condition, "Invalid assertion");
	}

	public static void isTrue(boolean condition, String errorMsg) throws BusinessException {
		if ( condition == true ) return;
		throw new BusinessException( errorMsg );
	}
	
	public static void isEmpty (Set<?> lista, String mensaje) throws BusinessException {
		if(!lista.isEmpty()) {
			throw new BusinessException(mensaje);
		}
		return;
	}
	
	public static void numeroMenorCero(double cantidad, String mensaje) throws BusinessException {
		if(cantidad<0) {
			throw new BusinessException(mensaje);
		}
		return;
		
	}

	public static void endDateMenorStartDate(Date endDate, Date startDate, String string) throws BusinessException {
		if(endDate!=null) {
			if(endDate.before(startDate)) {
				throw new BusinessException(string);
			}
		}
	}

	public static void isActive(Contract c, String mensaje) throws BusinessException {
		if(c.getStatus().equals(ContractStatus.ACTIVE)) {
			throw new BusinessException(mensaje);
		}
		return;
	}

	public static void nominasGeneradas(Contract c, String mensaje) throws BusinessException {
		if(c.nominasParaEsteContrato()) {
			return;
		}
		throw new BusinessException(mensaje);
	}

	public static void haRealizadoIntervenciones(Contract c, String mensaje) throws BusinessException {
		if(c.actividadDuranteSuVigencia()) {
			return;
		}
		throw new BusinessException(mensaje);
	}

	public static void isNotActive(Contract c, String mensaje) throws BusinessException {
		if(c.getStatus().equals(ContractStatus.ACTIVE)) {
			return;
		}
		throw new BusinessException(mensaje);
	}

	public static void otroTipoContratoMismoNombre(List<ContractType> listaTipoContratos, ContractTypeDto tipoDeContrato, String mensaje) throws BusinessException {
		for(ContractType tipoContratoLista : listaTipoContratos) {
			String nombre = tipoContratoLista.getName();
			if(nombre.equals(tipoDeContrato.name)) {
				throw new BusinessException(mensaje);
			}
		}
		return;
	}

	public static void otroCategoriaContratoMismoNombre(List<ContractCategory> listaCategoriaContratos, ContractCategoryDto categoria, String mensaje) throws BusinessException {
		for(ContractCategory categoriaContrato : listaCategoriaContratos) {
			String nombre = categoriaContrato.getName();
			if(nombre.equals(categoria.name)) {
				throw new BusinessException(mensaje);
			}
		}
		return;
	}

}
