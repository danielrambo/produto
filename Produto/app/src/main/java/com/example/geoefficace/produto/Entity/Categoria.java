package com.example.geoefficace.produto.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geoefficace on 02/04/2019.
 */

public class Categoria {

    private String id;
    private String name;
    private List<Categoria> list;

    public Categoria() {
        this.getCategorias();
    }

    public Categoria(String id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Categoria> getList() {
        return list;
    }

    public void setList(List<Categoria> list) {
        this.list = list;
    }

    public List<Categoria> getCategorias(){

        this.list = new ArrayList<>();
        this.list.add(new Categoria("mq", "Máquinas"));
        this.list.add(new Categoria("pc", "Peças"));

        return this.list;
    }

    public List<String> getCategoriasString(List<Categoria> cat){

        List<String> array = new ArrayList<>();
        for(Categoria c : cat)
            array.add(c.getName());

        return array;

    }
}
