package uo.ri.business.repository;

import uo.ri.model.Cliente;

public interface ClienteRepository extends Repository<Cliente> {

	/**
	 * @param dni
	 * @return the client identified by the dni or null if none 
	 */
	Cliente findByDni(String dni);

}
