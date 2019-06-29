package uo.ri.otrosTest.associations;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import alb.util.date.Dates;
import uo.ri.model.Association;
import uo.ri.model.Contract;
import uo.ri.model.ContractCategory;
import uo.ri.model.Mecanico;


public class CategorizaTest {
	private Mecanico mecanico;	
	private Contract contrato;
	private ContractCategory categoriaContrato;

	@Before
	public void setUp() {
		mecanico = new Mecanico("dni-mecanico", "nombre", "apellidos");
		categoriaContrato = new ContractCategory("categoriaContratoTest");
		contrato = new Contract(mecanico, Dates.fromDdMmYyyy(18, 4, 2019));
				
		Association.Categorize.link(contrato, categoriaContrato);
	}
	
	@Test
	public void testCategorizarLinked() {
		assertTrue(contrato.getContractCategory() == categoriaContrato);
		assertTrue(categoriaContrato.getContract().contains(contrato));
	}

	@Test
	public void testCategorizarUnlink() {
		Association.Categorize.unlink(contrato, categoriaContrato);
		
		assertTrue(! categoriaContrato.getContract().contains(contrato));
		assertTrue(categoriaContrato.getContract().size() == 0);
		assertTrue(contrato.getContractCategory() == null);
		
	}

	@Test
	public void testSafeReturn() {
		Set<Contract> contratos = categoriaContrato.getContract();
		contratos.remove(this.contrato);
		
		assertTrue(contratos.size() == 0);
		assertTrue("Se tiene que retornar copia de la colección", categoriaContrato.getContract().size() == 1);
	}

}
