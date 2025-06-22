package com.ibdev.boavistastorage.entity;

public enum StatusEstoque {
    BOM,
    RAZOAVEL,
    ZERADO;
    StatusEstoque() {
    }



    @Override
    public String toString() {
        return "StatusEstoque{}";
    }
}
