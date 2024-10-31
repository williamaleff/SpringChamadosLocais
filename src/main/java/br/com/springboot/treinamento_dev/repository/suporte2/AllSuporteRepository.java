package br.com.springboot.treinamento_dev.repository.suporte2;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Suporte2;

@Repository
public interface AllSuporteRepository extends JpaRepository<Suporte2, Long> {

  	@Query(value = "select u from Suporte2 u where u.descricao like %?1%")
	List<Suporte2> findByDescricaoContaining(String descricao);
  	
  	@Query(value = "select u from Suporte2 u where u.data like %?1%")
	List<Suporte2> findByDateContaining(String date);
  	
  	@Query(value = "select u from Suporte2 u where u.data like %?1% AND u.descricao like %?2%") 
	List<Suporte2> findByDateAndDescricaoContaining(String date, String descricao);
  	}
