package com.germangascon.frasescelebres.repo;

import com.germangascon.frasescelebres.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoriaDao extends JpaRepository<Categoria, Integer> {
}
