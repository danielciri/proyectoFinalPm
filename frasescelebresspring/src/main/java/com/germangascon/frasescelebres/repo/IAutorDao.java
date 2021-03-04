package com.germangascon.frasescelebres.repo;

import com.germangascon.frasescelebres.models.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IAutorDao extends JpaRepository<Autor, Integer> {
    /*
    @Query("SELECT a.* FROM autor a WHERE a.id = ?1")
    Autor findById(int id);
    */
}
