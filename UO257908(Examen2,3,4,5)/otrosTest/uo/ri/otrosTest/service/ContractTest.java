package uo.ri.otrosTest.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.business.ContractCrudService;
import uo.ri.business.dto.ContractDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class ContractTest {
	
	private ContractCrudService servicioContrato;
	
	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
		servicioContrato = Factory.service.forContractCrud();
	}

	//----------------------------------------------Borrar contratos---------------------------------------------
	
	/**
	 * Se intenta eliminar un contrato que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarContratoNoExiste() throws BusinessException {
		servicioContrato.deleteContract(1000L);
	}
	
	/**
	 * Se intenta eliminar un contrato el cual tiene nóminas generadas
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarContratoMecanicoTieneNominas() throws BusinessException {
		servicioContrato.deleteContract(1L);
		
	}
	
	/**
	 * Se intenta eliminar un contrato cuyo mecánico ha tenido intervenciones durante el periodo del contrato
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarContratoMecanicoTieneIntervenciones() throws BusinessException {
		servicioContrato.deleteContract(10L);
	}
	
	//----------------------------------------------Insertar contratos---------------------------------------------
	
	/**
	 * Se intenta añadir un contrato con un tipo de contrato que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarContratoConUnTipoDeContratoQueNoExiste() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.mechanicId = 1L;
		contrato.typeId = 1000L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = 2000;
		servicioContrato.addContract(contrato);
	}
	
	/**
	 * Se intenta añadir un contrato con un mecánico que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarContratoMecanicoNoExiste() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.mechanicId = 1000L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = 2000;
		servicioContrato.addContract(contrato);
	}
	
	/**
	 * Se intenta añadir un contrato con una categoría que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarContratoCategoriaNoExiste() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1000L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = 2000;
		servicioContrato.addContract(contrato);
	}
	
	/**
	 * Se intenta añadir un contrato con un salario base negativo
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarContratoSalarioBaseNegativo() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = -2000;
		servicioContrato.addContract(contrato);
	}
	
	/**
	 * Se intenta añadir un contrato con una fecha de fin de contrato anterior a la fecha de inicio del contrato
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarContratoEndDateWrong() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.endDate = Dates.fromDdMmYyyy(12, 4, 2012);
		contrato.yearBaseSalary = 2000;
		servicioContrato.addContract(contrato);
	}
	
	
	
	//----------------------------------------------Actualizar contratos---------------------------------------------

	/**
	 * Se intenta modificar un contrato que no existe. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testActualizarContratoQueNoExiste() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.id = 100L;
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = 2000;
		servicioContrato.updateContract(contrato);
	}
	
	/**
	 * Se intenta modificar un contrato que no está activo
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testActualizarContratoQueNoEstaActivo() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.id = 6L;
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = 2000;
		servicioContrato.updateContract(contrato);
	}
	
	/**
	 * Se intenta modificar un contrato con un salario base negativo
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testActualizarContratoSalarioBaseNegativo() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.id = 1L;
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.yearBaseSalary = -2000;
		servicioContrato.updateContract(contrato);
	}
	
	/**
	 * Se intenta modificar un contrato con una fecha de fin de contrato anterior a la fecha de inicio del contrato
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testActualizarContratoEndDateWrong() throws BusinessException {
		ContractDto contrato = new ContractDto();
		contrato.id = 1L;
		contrato.mechanicId = 1L;
		contrato.typeId = 1L;
		contrato.categoryId = 1L;
		contrato.startDate = Dates.fromDdMmYyyy(18, 5, 2019);
		contrato.endDate = Dates.fromDdMmYyyy(12, 2, 2015);
		contrato.yearBaseSalary = 2000;
		servicioContrato.updateContract(contrato);
	}
	
	//----------------------------------------------Encontrar contratos---------------------------------------------
	
	/**
	 * 
	 * Se espera que devuelva null al no encontrar un contrato con ese id.
	 */
	@Test
	public void testFindContractById() throws BusinessException {
		assertNull(servicioContrato.findContractById(1000L));
	}
	

	/**
	 * Se intenta encontrar los contratos de un mecánico que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testFindContractsByMechanicId() throws BusinessException {
		servicioContrato.findContractsByMechanicId(1000L);
	}
	
	//----------------------------------------------Terminar contratos---------------------------------------------
	
	/**
	 * Se intenta terminar un contrato que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testTerminarContratoNoExiste() throws BusinessException {
		servicioContrato.finishContract(1000L, Dates.fromDdMmYyyy(25, 9, 2020));
	}
	

	/**
	 * Se intenta terminar un contrato que no está activo
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testTerminarContratoNoActivo() throws BusinessException {
		servicioContrato.finishContract(6L, Dates.fromDdMmYyyy(25, 9, 2020));
	}
	

	/**
	 * Se intenta terminar un contrato con una fecha de fin de contrato anterior a la fecha de inicio del contrato
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testTerminarContratoEndDateWrong() throws BusinessException {
		servicioContrato.finishContract(1L, Dates.fromDdMmYyyy(25, 9, 2009));
	}
}
