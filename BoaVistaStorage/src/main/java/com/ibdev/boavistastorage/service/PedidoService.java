package com.ibdev.boavistastorage.service;

import com.ibdev.boavistastorage.entity.Pedido;
import com.ibdev.boavistastorage.repository.PedidoRepository;

import java.time.LocalDateTime;

public class PedidoService {
    private static PedidoRepository pedidoRepository;

    public PedidoService(PedidoService pedidoService) {
        this.pedidoRepository = pedidoRepository;
    }

    public void createPedido(Pedido pedido) {
        pedidoRepository.create(pedido);
    }

    public void readAllPedidos() {
        pedidoRepository.readAll();
    }

    public Pedido findPedidoById(Long id) {
        return pedidoRepository.findById(id);
    }

    public Pedido findPedidoByDataPedido(LocalDateTime dataPedido) {
        return pedidoRepository.findByDataPedido(dataPedido);
    }

    public void updatePedido(Long idPedido, Pedido pedido) {
        pedidoRepository.update(idPedido, pedido);
    }

    public void deletePedido(Long idPedido) {
        pedidoRepository.delete(idPedido);
    }
}
