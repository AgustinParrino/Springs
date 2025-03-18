package com.parrino.CRUD.servicio;

import com.parrino.CRUD.modelo.Cliente;
import com.parrino.CRUD.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

    @Service
    public class ClienteServicio implements IClienteServicio{

        @Autowired //implementa todo de IclienteServicio
        private ClienteRepositorio clienteRepositorio;

        @Override
        public List<Cliente> listarClientes() {
            List<Cliente> clientes = clienteRepositorio.findAll(); //queremos encontrar todas las lineas
            return clientes;
        }

        @Override
        public Cliente buscarClientePorId(Integer idCliente) {
            Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
            return cliente;
        }

        @Override
        public void guardarCliente(Cliente cliente) {
            clienteRepositorio.save(cliente);
        }

        @Override
        public void eliminarCliente(Cliente cliente) {
            clienteRepositorio.delete(cliente);
        }
    }


