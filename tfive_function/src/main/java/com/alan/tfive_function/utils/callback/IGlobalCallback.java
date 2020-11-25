package com.alan.tfive_function.utils.callback;


import androidx.annotation.Nullable;


public interface IGlobalCallback<T> {

    void executeCallback(@Nullable T args);
}
