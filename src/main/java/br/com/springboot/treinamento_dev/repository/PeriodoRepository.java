package br.com.springboot.treinamento_dev.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Suporte;

@Repository
public interface PeriodoRepository extends JpaRepository<Suporte, Long> {

	@Query(value = "select u from Suporte u where data_do_chamado BETWEEN ?1 AND ?2") 
	List<Suporte> buscarPorPeriodo(String data, String data2);
}
