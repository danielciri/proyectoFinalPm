package com.germangascon.frasescelebres.controllers;


import com.germangascon.frasescelebres.models.Categoria;
import com.germangascon.frasescelebres.repo.ICategoriaDao;
import com.germangascon.frasescelebres.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {
    @Autowired
    private ICategoriaDao repo;

    @GetMapping("/all")
    public List<Categoria> getCategorias() {
        return repo.findAll();
    }

    @GetMapping(value = "/{id}")
    public Optional<Categoria> getCategoriaById(@PathVariable("id") Integer id) {
        return repo.findById(id);
    }

    @PostMapping("/add")
    public boolean addCategoria(@RequestBody Categoria categoria) {

        try{
            Log.i("Nueva Categoría: ", categoria.toString());
            repo.save(categoria);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @PutMapping
    public void updateCategoria(@RequestBody Categoria categoria) {
        Log.i("Update Categoria: ", categoria.toString());
        /*
        repo.save(categoria);
         */
    }

    @DeleteMapping(value = "/{id}")
    public void deleteCategoria(@PathVariable("id") Integer id) {
        repo.deleteById(id);
    }
}
