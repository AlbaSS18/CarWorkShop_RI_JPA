package uo.ri.persistence.jpa;

import uo.ri.business.repository.ClienteRepository;
import uo.ri.model.Cliente;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ClienteJpaRepository 
		extends BaseRepository<Cliente> 
		implements ClienteRepository {

	@Override
	public Cliente findByDni(String dni) {
		return Jpa.getManager()
				.createNamedQuery("Cliente.findByDni", Cliente.class)
				.setParameter(1, dni)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

}
