package com.example.backend.model;

import java.util.concurrent.TimeUnit;

public record CompletionTime (int amount,
                             TimeUnit timeUnit){

    @Override
    public String toString() {
        return this.amount + " " +  timeUnit.toString().toLowerCase();
    }
}
