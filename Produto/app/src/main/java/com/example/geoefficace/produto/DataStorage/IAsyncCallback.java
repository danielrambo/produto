package com.example.geoefficace.produto.DataStorage;

/**
 * Created by geoefficace on 04/04/2019.
 */

public interface IAsyncCallback<T> {
    public void receive(Exception ex, T data);
}

