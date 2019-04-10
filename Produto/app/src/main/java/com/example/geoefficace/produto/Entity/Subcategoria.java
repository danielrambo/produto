package com.example.geoefficace.produto.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoefficace on 02/04/2019.
 */

public class Subcategoria {

    private String id;
    private String name;
    private String categoria;
    private List<Subcategoria> list;

    public Subcategoria() {
        this.getSubcategorias();
    }

    public Subcategoria(String id, String name, String categoria) {
        this.id = id;
        this.name = name;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<Subcategoria> getList() {
        return list;
    }

    public void setList(List<Subcategoria> list) {
        this.list = list;
    }

    public List<Subcategoria> getSubcategorias(){

        this.list = new ArrayList<>();
        this.list.add(new Subcategoria("mq-agr-verde", "Agrícola", "mq"));
        this.list.add(new Subcategoria("mq-agr-amarela", "Construção", "mq"));

        return this.list;
    }

    public List<String> getSubcategoriasString(List<Subcategoria> sub){

        List<String> array = new ArrayList<>();
        for(Subcategoria c : sub)
            array.add(c.getName());

        return array;

    }
}
