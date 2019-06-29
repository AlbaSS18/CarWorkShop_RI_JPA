package uo.ri.business.repository;

import java.util.List;

import uo.ri.model.Vehiculo;

public interface VehiculoRepository 
	extends Repository<Vehiculo>{
	
	/**
	 * @param List<Vehiculo>
	 * @return el cliente del cual quieres saber los vehículos
	 */
	List<Vehiculo> findByClienteId(Long id);
}
