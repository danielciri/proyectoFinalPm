package com.germangascon.frasescelebres.controllers;

import com.germangascon.frasescelebres.models.Autor;
import com.germangascon.frasescelebres.repo.IAutorDao;
import com.germangascon.frasescelebres.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/autor")
public class AutorController {
    @Autowired
    private IAutorDao repo;

    @GetMapping("/all")
    public List<Autor> getAutores() {
        return repo.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Autor> getAutor(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @PostMapping("/add")
    public boolean addAutor(@RequestBody Autor autor) {
        try{
            Log.i("Nuevo autor: ", autor.toString());
            repo.save(autor);
            return true;
        }catch (Exception e){
            return false;
        }

    }

    @PutMapping
    public void updateAutor(@RequestBody Autor autor) {
        Log.i("Update Autor: ", autor.toString());
        //repo.save(autor);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteAutor(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}
