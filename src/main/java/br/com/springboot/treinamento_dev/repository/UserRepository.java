package br.com.springboot.treinamento_dev.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Suporte;

@Repository
public interface UserRepository extends JpaRepository<Suporte, Long> {

		@Query(value = "select u from Suporte u where upper(trim(u.requerente)) like %?1%")
		List<Suporte> buscarPorNome(String name);
}
