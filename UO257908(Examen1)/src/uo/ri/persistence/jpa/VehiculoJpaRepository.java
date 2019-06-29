package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.VehiculoRepository;
import uo.ri.model.Vehiculo;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class VehiculoJpaRepository 
	extends BaseRepository<Vehiculo> 
	implements VehiculoRepository {

	@Override
	public List<Vehiculo> findByClienteId(Long id) {
		return Jpa.getManager()
				.createNamedQuery("Vehiculo.findVehiculoByClientId",
						Vehiculo.class)
				.setParameter(1, id)
				.getResultList();
	}

}
