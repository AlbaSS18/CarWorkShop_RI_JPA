package uo.ri.otrosTest.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import uo.ri.business.MechanicCrudService;
import uo.ri.business.dto.MechanicDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class MechanicTest {

	private MechanicCrudService servicioMecanico;
	
	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
		servicioMecanico = Factory.service.forMechanicCrudService();
	}
	
	//----------------------------------------------Borrar mecánicos---------------------------------------------

	/**
	 * Se intenta eliminar un mecánico que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarMecanicoNoExiste() throws BusinessException {
		servicioMecanico.deleteMechanic(10000L);
	}
	
	/**
	 * Se intenta eliminar un mecánico que tiene intervenciones
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarMecanicoTieneIntervenciones() throws BusinessException {
		servicioMecanico.deleteMechanic(1L);
	}
	
	/**
	 * Se intenta eliminar un mecánico que tiene averías asignadas
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarMecanicoTieneAveriasAsignadas() throws BusinessException {
		servicioMecanico.deleteMechanic(106L);
	}
	
	//----------------------------------------------Insertar mecánicos---------------------------------------------
	
	/**
	 * Se intenta añadir un mecánico con un dni existente
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarMecanicoMismoDni() throws BusinessException {
		MechanicDto mecanico = new MechanicDto();
		mecanico.dni = "622298066";
		servicioMecanico.addMechanic(mecanico);
	}
	
	//----------------------------------------------Actualizar mecánicos---------------------------------------------
	
	/**
	 * Se intenta modificar un mecánico que no existe.
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testActualizarMecanicoNoExiste() throws BusinessException {
		MechanicDto mecanico = new MechanicDto();
		mecanico.id = 1000L;
		servicioMecanico.updateMechanic(mecanico);
	}
	
	//----------------------------------------------Encontrar mecánicos---------------------------------------------
	
	/**
	 * Se espera que devuelva null al no encontrar un mecánico con ese id
	 */
	@Test
	public void testFindMechanicById() throws BusinessException {
		assertNull(servicioMecanico.findMechanicById(1000L));
	}
	
}
