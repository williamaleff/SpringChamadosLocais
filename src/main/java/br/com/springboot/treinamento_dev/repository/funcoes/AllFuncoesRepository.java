package br.com.springboot.treinamento_dev.repository.funcoes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Funcoes;

@Repository
public interface AllFuncoesRepository extends JpaRepository<Funcoes, Long> {

   	@Query(value = "select u from Funcoes u where u.nome like %?1%")
	List<Funcoes> findByNameContaining(String name);
}
