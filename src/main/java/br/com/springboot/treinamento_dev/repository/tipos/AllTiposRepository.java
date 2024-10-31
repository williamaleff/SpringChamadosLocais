package br.com.springboot.treinamento_dev.repository.tipos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Tipos;

@Repository
public interface AllTiposRepository extends JpaRepository<Tipos, Long> {

  	@Query(value = "select u from Tipos u where u.nome like %?1%")
	List<Tipos> findByNameContaining(String name);
  	}
