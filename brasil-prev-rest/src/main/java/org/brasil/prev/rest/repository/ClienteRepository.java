package org.brasil.prev.rest.repository;

import org.brasil.prev.rest.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	Cliente findByUserName(String name);
}
