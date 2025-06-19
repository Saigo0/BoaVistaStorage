package com.ibdev.boavistastorage.main;

import com.ibdev.boavistastorage.entity.*;
import com.ibdev.boavistastorage.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_basic");
        EntityManager em = emf.createEntityManager();

//        InsumoRepository insumoRepository = new InsumoRepository(em);
//        System.out.println("Criação do insumo:");
//        Insumo insumo = new Insumo("Frango", 20.0, "Kg", 4.0);
//        insumoRepository.create(insumo);
//        insumoRepository.readAll();
//        System.out.println(" ");
//        System.out.println("Atualização do insumo");
//        insumo.setPrecoCusto(25.0);
//        insumoRepository.update(insumo.getId(), insumo);
//        insumoRepository.readAll();
//        System.out.println(" ");
//
//        ProduzidoRepository produzidoRepository = new ProduzidoRepository(em);
//        System.out.println("Criação do produzido:");
//        Produzido produzido = new Produzido("Pastel de carne", "Massa e carne", "pastel", 12.0);
//        produzidoRepository.create(produzido);
//        produzidoRepository.readAll();
//        System.out.println(" ");
//
//        InsumoProduzido insumoProduzido = new InsumoProduzido(0.1, insumo, produzido);
//        insumoProduzido.setProduzido(produzido);
//        insumoProduzido.setInsumo(insumo);
//
//        produzido.addInsumoProduzido(insumoProduzido);
//
//        insumo.addInsumosProduzidos(insumoProduzido);
//
//        insumoProduzido.getProduzido().setPrecoCusto(insumoProduzido.getQuantUtilizada() * insumoProduzido.getInsumo().getPrecoCusto());
//
//        System.out.println("Atualização de produzido:");
//        produzidoRepository.update(produzido.getId(), produzido);
//        produzidoRepository.readAll();
//        System.out.println(" ");
//
//        System.out.println("Atualização de insumo:");
//        insumoRepository.update(insumo.getId(), insumo);
//        insumoRepository.readAll();
//        System.out.println(" ");
//
//        System.out.println("InsumoProduzidos: ");
//        System.out.println(insumo.getInsumosProduzidos());
//        System.out.println(produzido.getInsumosProduzidos());
//
//        System.out.println("AQUIIIIIIIII");

        ClienteRepository clienteRepository = new ClienteRepository(em);
        System.out.println("Criação do cliente:");
        Cliente cliente = new Cliente("JonasBlastoize", "09509509505", "69999999999", "Rua dos TioPinas");
        clienteRepository.create(cliente);
        clienteRepository.readAll();
        System.out.println(" ");


        VendaRepository vendaRepository = new VendaRepository(em);
        Venda venda = new Venda();

        venda.setValorTotal(200);
        venda.setCliente(cliente);
    }
}
