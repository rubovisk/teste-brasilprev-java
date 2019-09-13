package org.brasil.prev.rest.repository;

import org.brasil.prev.rest.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
