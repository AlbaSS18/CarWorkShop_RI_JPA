package uo.ri.persistence.jpa;

import java.util.Date;

import java.util.List;

import uo.ri.business.repository.ContractRepository;
import uo.ri.model.Contract;
import uo.ri.persistence.jpa.util.BaseRepository;
import uo.ri.persistence.jpa.util.Jpa;

public class ContractJpaRepository 
	extends BaseRepository<Contract> 
	implements ContractRepository {

	@Override
	public Contract findById(Long id) {
		return Jpa.getManager()
				.createNamedQuery("Contract.findById", Contract.class)
				.setParameter(1, id)
				.getResultList()
				.stream()
				.findFirst()
				.orElse(null);
	}

	@Override
	public List<Contract> findContractsActiveOrExtinguished(Date fechaNomina) {
		return Jpa.getManager()
				.createNamedQuery("Contract.findContractsActiveOrExtinguished",
						Contract.class)
				.setParameter(1, fechaNomina)
				.getResultList();
	}

	@Override
	public List<Contract> findContractsMoreThanSalary(Double salary) {
		return Jpa.getManager()
				.createNamedQuery("Contract.findContractsMoreThanSalary",
						Contract.class)
				.setParameter(1, salary)
				.getResultList();
	}

}
