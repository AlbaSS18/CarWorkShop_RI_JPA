package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Mecanico;

public interface MecanicoRepository extends Repository<Mecanico> {

	/**
	 * @param dni
	 * @return the mechanic identified by the dni or null if none 
	 */
	Mecanico findByDni(String dni);
	
	/**
	 * @param id
	 * @return the mechanic identified by the id or null if none 
	 */
	Mecanico findById(Long id);


	/**
	 * @return lista de mecánicos con contrato activo
	 */
	List<Mecanico> findMecanicosContratoActivo();
	
}
