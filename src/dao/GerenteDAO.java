package dao;

import factory.ConnectionFactory;
import modelo.Gerente;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class GerenteDAO {

    private Connection connection;

    int idGerente;
    String nomeGerente;
    String emailGerente;
    String enderecoGerente;
    int cpfGerente;
    String telefoneGerente;
    String cepGerente;
    String generoGerente;
    int idFuncionario;

    public GerenteDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Gerente gerente) {
        String sql = "INSERT INTO gerente (nomeGerente, emailGerente, enderecoGerente, cpfGerente, telefoneGerente, cepGerente, generoGerente, idFuncionario) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, gerente.getNomeGerente());
            stmt.setString(2, gerente.getEmailGerente());
            stmt.setString(3, gerente.getEnderecoGerente());
            stmt.setInt(4, gerente.getCpfGerente());
            stmt.setString(5, gerente.getTelefoneGerente());
            stmt.setString(6, gerente.getCepGerente());
            stmt.setString(7, gerente.getGeneroGerente());
            stmt.setInt(8, gerente.getIdFuncionario());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    //Editar informações, método update
    public void update(Gerente gerente) {
        String sql = "UPDATE gerente SET nomeGerente = ?, emailGerente = ?, enderecoGerente = ?, cpfGerente = ?, telefoneGerente = ?, cepGerente = ?, generoGerente = ?, idFuncionario = ? WHERE idGerente = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, gerente.getNomeGerente());
            stmt.setString(2, gerente.getEmailGerente());
            stmt.setString(3, gerente.getEnderecoGerente());
            stmt.setInt(4, gerente.getCpfGerente());
            stmt.setString(5, gerente.getTelefoneGerente());
            stmt.setString(6, gerente.getCepGerente());
            stmt.setString(7, gerente.getGeneroGerente());
            stmt.setInt(8, gerente.getIdFuncionario());
            stmt.setInt(9, gerente.getIdGerente());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!");
            throw new RuntimeException(u);
        }
    }
    //Deleta informações do banco de dados

    public void delete(Gerente gerente) {
        String sql = "DELETE FROM gerente WHERE idGerente = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, gerente.getIdGerente());//necessario somente o id
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            throw new RuntimeException(u);
        }
    }

    public List<Gerente> leitura() {
        connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null; //metodo utilizado para view
        List<Gerente> gerentes = new ArrayList<>(); //Array para guardar os estados
        try {
            stmt = connection.prepareStatement("SELECT * FROM gerente");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Gerente gerente = new Gerente();
                gerente.setIdGerente(rs.getInt("idGerente")); //nomes colunas bando de dados
                gerente.setNomeGerente(rs.getString("nomeGerente"));
                gerente.setEmailGerente(rs.getString("emailGerente"));
                gerente.setEnderecoGerente(rs.getString("enderecoGerente"));
                gerente.setCpfGerente(rs.getInt("cpfGerente"));
                gerente.setTelefoneGerente(rs.getString("telefoneGerente"));
                gerente.setCepGerente(rs.getString("cepGerente"));
                gerente.setGeneroGerente(rs.getString("generoGerente"));
                gerente.setIdFuncionario(rs.getInt("idFuncionario"));
                gerentes.add(gerente);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GerenteDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//ConnectionFactory.closeConnection(connection, stmt,rs);
        }
        return gerentes;
    }

}
