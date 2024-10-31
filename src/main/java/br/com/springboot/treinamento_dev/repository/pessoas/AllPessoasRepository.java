package br.com.springboot.treinamento_dev.repository.pessoas;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Pessoas;

@Repository
public interface AllPessoasRepository extends JpaRepository<Pessoas, Long> {

  	@Query(value = "select u from Pessoas u where u.nomeCompleto like %?1%")
	List<Pessoas> findByNameContaining(String name);
}
