package uo.ri.otrosTest.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import uo.ri.business.PayrollService;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class PayrollTest {
	
	private PayrollService servicioNomina;
	
	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
		servicioNomina = Factory.service.forPayroll();
	}
	
	//----------------------------------------------Borrar última nómina de un mecánico---------------------------------------------

	/**
	 * Se intenta eliminar las nóminas de un mecánico que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarNominaMecanicoNoExiste() throws BusinessException {
		servicioNomina.deleteLastPayrollForMechanicId(100L);
	}
	
	/**
	 * Se intenta eliminar las nóminas de un mecánico que no tiene nóminas
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarNominaMecanicoSinNominas() throws BusinessException {
		servicioNomina.deleteLastPayrollForMechanicId(2L);
	}
	
	//----------------------------------------------Encontrar nóminas---------------------------------------------

	/**
	 * Se espera que devuelva null al no encontrar una nómina con ese id
	 */
	@Test
	public void testFindPayrollById() throws BusinessException {
		assertNull(servicioNomina.findPayrollById(400L));
	}
	
}
