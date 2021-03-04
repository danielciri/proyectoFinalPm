package com.germangascon.frasescelebres.repo;

import com.germangascon.frasescelebres.models.Frase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Date;


public interface IFraseDao extends JpaRepository<Frase, Integer> {

    @Query(value = "select * from frase where frase.fecha_programada=?1", nativeQuery = true)
    Frase getFraseDelDia(Date fecha);

    @Query(value = "SELECT f FROM Frase f where f.autor.id = ?1")
    Frase getFraseAutor(Integer id);

}
