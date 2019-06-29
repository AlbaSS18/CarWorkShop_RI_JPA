package uo.ri.persistence.jpa;

import java.util.List;

import uo.ri.business.repository.ContractTypeRepository;
import uo.ri.model.ContractType;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractTypeJpaRepository 
	extends BaseRepository<ContractType> 
	implements ContractTypeRepository {

	@Override
	public ContractType findById(Long id) {
		return Jpa.getManager()
				.createNamedQuery("ContractType.findById", ContractType.class)
				.setParameter(1, id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<ContractType> findAll(Long id) {
		return Jpa.getManager()
				.createNamedQuery("ContractType.findAll", ContractType.class)
				.setParameter(1, id)
				.getResultList();
	}

	@Override
	public ContractType findByName(String name) {
		return Jpa.getManager()
				.createNamedQuery("ContractType.findByName",
						ContractType.class)
				.setParameter(1, name)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

}
