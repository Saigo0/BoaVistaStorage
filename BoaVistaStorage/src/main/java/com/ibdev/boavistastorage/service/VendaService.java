package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Cliente;
import com.ibdev.boavistastorage.entity.Venda;
import com.ibdev.boavistastorage.repository.VendaRepository;

import java.time.LocalDateTime;

public class VendaService {
    private static VendaRepository vendaRepository;

    public VendaService(VendaRepository vendaRepository) {
        this.vendaRepository = vendaRepository;
    }

    public void createVenda(Venda venda) {
        vendaRepository.create(venda);
    }

    public void readAllVendas() {
        vendaRepository.readAll();
    }

    public Venda findVendaById(Long id) {
        return vendaRepository.findById(id);
    }

    public Venda findVendaByDataVenda(LocalDateTime dataVenda) {
        return vendaRepository.findByDataVenda(dataVenda);
    }

    public Venda findVendaByValorTotal(double valorTotal) {
        return vendaRepository.findByValorTotal(valorTotal);
    }

    public Venda findVendaByCliente(Cliente cliente) {
        return vendaRepository.findByCliente(cliente);
    }

    public void updateVenda(Long idVenda, Venda venda) {
        vendaRepository.update(idVenda, venda);
    }

    public void deleteVenda(Long idVenda) {
        vendaRepository.delete(idVenda);
    }
}
