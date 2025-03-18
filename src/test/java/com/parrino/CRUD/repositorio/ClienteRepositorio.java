package com.parrino.CRUD.repositorio;
import com.parrino.CRUD.modelo.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente,Integer> {
}

