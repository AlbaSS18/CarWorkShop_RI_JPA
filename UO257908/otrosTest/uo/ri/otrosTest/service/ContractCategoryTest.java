package uo.ri.otrosTest.service;

import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import uo.ri.business.ContractCategoryCrudService;
import uo.ri.business.dto.ContractCategoryDto;
import uo.ri.business.exception.BusinessException;
import uo.ri.business.impl.BusinessFactory;
import uo.ri.conf.Factory;
import uo.ri.persistence.jpa.JpaRepositoryFactory;
import uo.ri.persistence.jpa.executor.JpaExecutorFactory;

public class ContractCategoryTest {
	
	private ContractCategoryCrudService servicioCategoriaContrato;

	@Before
	public void setUp() throws Exception {
		Factory.service = new BusinessFactory();
		Factory.repository = new JpaRepositoryFactory();
		Factory.executor = new JpaExecutorFactory();
		servicioCategoriaContrato = Factory.service.forContractCategoryCrud();
	}
	
	//----------------------------------------------Borrar categorías de contratos---------------------------------------------
	
	
	/**
	 * Se intenta eliminar una categoría que no existe
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarCategoriaQueNOExiste() throws BusinessException {
		servicioCategoriaContrato.deleteContractCategory(1000L);
	}
	
	/**
	 * Se intenta eliminar una categoría que tiene contratos asignados
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testBorrarCategoriaConContratos() throws BusinessException {
		servicioCategoriaContrato.deleteContractCategory(1L);
	}
	
	//----------------------------------------------Insertar categorías de contratos---------------------------------------------
	
	/**
	 * Se intenta añadir una categoría con un nombre que ya existe. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarCategoriaMismoNombre() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.name = "Categoria de contrato 1";
		servicioCategoriaContrato.addContractCategory(categoria);
	}
	
	/**
	 * Se intenta añadir una categoría con un triennium negativo. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarTrienniumNegativo() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.trieniumSalary = -10;
		servicioCategoriaContrato.addContractCategory(categoria);
	}
	
	/**
	 * Se intenta añadir una categoría con una productividad negativa. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testInsertarProductividadNegativa() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.productivityPlus = -10;
		servicioCategoriaContrato.addContractCategory(categoria);
	}
	
	//----------------------------------------------Actualizar categorías de contratos---------------------------------------------
	/**
	 * Se intenta modificar una categoría que no existe. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testModificarCategoriaNoExiste() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.id = 109L;
		servicioCategoriaContrato.updateContractCategory(categoria);
	}
	
	/**
	 * Se intenta modificar una categoría con un trienium negativo. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testModificarTrienniumNegativo() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.trieniumSalary = -10;
		servicioCategoriaContrato.updateContractCategory(categoria);
	}
	
	/**
	 * Se intenta modificar una categoría con una productividad negativa. 
	 * El resultado esperado es una BusinessException
	 * 
	 * @throws BusinessException
	 */
	@Test (expected = BusinessException.class)
	public void testModificarProductividadNegativa() throws BusinessException {
		ContractCategoryDto categoria = new ContractCategoryDto();
		categoria.productivityPlus = -10;
		servicioCategoriaContrato.updateContractCategory(categoria);
	}
	
	//----------------------------------------------Encontrar categorías de contratos---------------------------------------------
	
	/**
	 * Se espera que devuelva null al no encontrar una categoría con ese id.
	 */
	@Test
	public void testFindContractCategoryById() throws BusinessException {
		assertNull(servicioCategoriaContrato.findContractCategoryById(1000L));
	}
	
}
