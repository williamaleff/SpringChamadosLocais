package br.com.springboot.treinamento_dev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.springboot.treinamento_dev.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {
	
}
