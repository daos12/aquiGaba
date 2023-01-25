package dao;

import factory.ConnectionFactory;
import modelo.Cliente;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClienteDAO {

    private Connection connection;

    int idCliente;
    String nomeCliente;
    String emailCliente;
    String enderecoCliente;
    int cpfCliente;
    String telefoneCliente;
    String cepCliente;
    String generoCliente;

    public ClienteDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Cliente cliente) {
        String sql = "INSERT INTO cliente (nomeCliente, emailCliente, enderecoCliente, cpfCliente, telefoneCliente, cepCliente, generoCliente) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEmailCliente());
            stmt.setString(3, cliente.getEnderecoCliente());
            stmt.setInt(4, cliente.getCpfCliente());
            stmt.setString(5, cliente.getTelefoneCliente());
            stmt.setString(6, cliente.getCepCliente());
            stmt.setString(7, cliente.getGeneroCliente());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    //Editar informações, método update
    public void update(Cliente cliente) {
        String sql = "UPDATE cliente SET nomeCliente = ?, emailCliente = ?, enderecoCliente = ?, cpfCliente = ?, telefoneCliente = ?, cepCliente = ?, generoCliente = ? WHERE idCliente = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, cliente.getNomeCliente());
            stmt.setString(2, cliente.getEmailCliente());
            stmt.setString(3, cliente.getEnderecoCliente());
            stmt.setInt(4, cliente.getCpfCliente());
            stmt.setString(5, cliente.getTelefoneCliente());
            stmt.setString(6, cliente.getCepCliente());
            stmt.setString(7, cliente.getGeneroCliente());
            stmt.setInt(8, cliente.getIdCliente());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!");
            throw new RuntimeException(u);
        }
    }
    //Deleta informações do banco de dados

    public void delete(Cliente cliente) {
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());//necessario somente o id
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            throw new RuntimeException(u);
        }
    }

    public List<Cliente> leitura() {
        connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null; //metodo utilizado para view
        List<Cliente> clientes = new ArrayList<>(); //Array para guardar os estados
        try {
            stmt = connection.prepareStatement("SELECT * FROM cliente");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente")); //nomes colunas bando de dados
                cliente.setNomeCliente(rs.getString("nomeCliente"));
                cliente.setEmailCliente(rs.getString("emailCliente"));
                cliente.setEnderecoCliente(rs.getString("enderecoCliente"));
                cliente.setCpfCliente(rs.getInt("cpfCliente"));
                cliente.setTelefoneCliente(rs.getString("telefoneCliente"));
                cliente.setCepCliente(rs.getString("cepCliente"));
                cliente.setGeneroCliente(rs.getString("generoCliente"));
                clientes.add(cliente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//ConnectionFactory.closeConnection(connection, stmt,rs);
        }
        return clientes;
    }

}
