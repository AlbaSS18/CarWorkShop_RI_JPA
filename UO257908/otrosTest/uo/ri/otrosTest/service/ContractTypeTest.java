package uo.ri.otrosTest.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import uo.ri.business.ContractTypeCrudService;
import uo.ri.business.dto.ContractTypeDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class ContractTypeTest {
	private ContractTypeCrudService servicioTipoContrato;

	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
		servicioTipoContrato = Factory.service.forContractTypeCrud();
	}
	
	//----------------------------------------------Borrar tipos de contratos---------------------------------------------
	
	
	/**
	 * Se intenta eliminar un tipo de contrato que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarTipoQueNOExiste() throws BusinessException {
		servicioTipoContrato.deleteContractType(1000L);
	}
	
	/**
	 * Se intenta eliminar un tipo de contrato que tiene contratos asociados
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarTipoConContratos() throws BusinessException {
		servicioTipoContrato.deleteContractType(1L);
	}
	
	//----------------------------------------------Insertar tipos de contratos---------------------------------------------
	
	/**
	 * Se intenta añadir un tipo de contrato con un nombre que ya existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarTipoMismoNombre() throws BusinessException {
		ContractTypeDto tipo = new ContractTypeDto();
		tipo.name = "Tipo de contrato 1";
		servicioTipoContrato.addContractType(tipo);
	}
	
	/**
	 * Se intenta añadir un tipo de contrato con un número de días de compensación negativo. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarDiasNegativos() throws BusinessException {
		ContractTypeDto tipo = new ContractTypeDto();
		tipo.compensationDays = -10;
		servicioTipoContrato.addContractType(tipo);
	}
	
	//----------------------------------------------Actualizar tipos de contratos---------------------------------------------
	/**
	 * Se intenta modificar un tipo de contrato que no existe.
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testModificarTipoNoExiste() throws BusinessException {
		ContractTypeDto tipo = new ContractTypeDto();
		tipo.id = 100L;
		servicioTipoContrato.updateContractType(tipo);
	}
	
	/**
	 * Se intenta modificar un tipo de contrato con los días de compensación negativos.
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testModificarDiasNegativos() throws BusinessException {
		ContractTypeDto tipo = new ContractTypeDto();
		tipo.compensationDays = -10;
		servicioTipoContrato.updateContractType(tipo);
	}
	
	//----------------------------------------------Encontrar tipos de contratos---------------------------------------------
	/**
	 * Se espera que devuelva null al no encontrar un tipo de contrato con ese id.
	 */
	@Test
	public void testFindContractTypeById() throws BusinessException {
		assertNull(servicioTipoContrato.findContractTypeById(1000L));
	}
}
