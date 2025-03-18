package com.parrino.CRUD.servicio;

import com.parrino.CRUD.modelo.Cliente;

import java.util.List;

public interface IClienteServicio { //Logica de los metodos
    public List<Cliente> listarClientes();

    public Cliente buscarClientePorId(Integer idCliente);

    public void guardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);

}
