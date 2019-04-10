package com.example.geoefficace.produto.Fragment;

/**
 * Created by geoefficace on 04/04/2019.
 */

public interface IListWithMenu<T> {

    void toView(T element);
    void edit(T element);
    void remove(T element);

}
