package dao;

import factory.ConnectionFactory;
import modelo.Funcionario;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class FuncionarioDAO {

    private Connection connection;

    int idFuncionario;
    String nomeFuncionario;
    String emailFuncionario;
    String enderecoFuncionario;
    int cpfFuncionario;
    String telefoneFuncionario;
    String cepFuncionario;
    String generoFuncionario;

    public FuncionarioDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Funcionario funcionario) {
        String sql = "INSERT INTO funcionario (nomeFuncionario, emailFuncionario, enderecoFuncionario, cpfFuncionario, telefoneFuncionario, cepFuncionario, generoFuncionario) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getEmailFuncionario());
            stmt.setString(3, funcionario.getEnderecoFuncionario());
            stmt.setInt(4, funcionario.getCpfFuncionario());
            stmt.setString(5, funcionario.getTelefoneFuncionario());
            stmt.setString(6, funcionario.getCepFuncionario());
            stmt.setString(7, funcionario.getGeneroFuncionario());
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    //Editar informações, método update
    public void update(Funcionario funcionario) {
        String sql = "UPDATE funcionario SET nomeFuncionario = ?, emailFuncionario = ?, enderecoFuncionario = ?, cpfFuncionario = ?, telefoneFuncionario = ?, cepFuncionario = ?, generoFuncionario = ? WHERE idFuncionario = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, funcionario.getNomeFuncionario());
            stmt.setString(2, funcionario.getEmailFuncionario());
            stmt.setString(3, funcionario.getEnderecoFuncionario());
            stmt.setInt(4, funcionario.getCpfFuncionario());
            stmt.setString(5, funcionario.getTelefoneFuncionario());
            stmt.setString(6, funcionario.getCepFuncionario());
            stmt.setString(7, funcionario.getGeneroFuncionario());
            stmt.setInt(8, funcionario.getIdFuncionario());

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!");
            throw new RuntimeException(u);
        }
    }
    //Deleta informações do banco de dados

    public void delete(Funcionario funcionario) {
        String sql = "DELETE FROM funcionario WHERE idFuncionario = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, funcionario.getIdFuncionario());//necessario somente o id
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            throw new RuntimeException(u);
        }
    }

    public List<Funcionario> leitura() {
        connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null; //metodo utilizado para view
        List<Funcionario> funcionarios = new ArrayList<>(); //Array para guardar os estados
        try {
            stmt = connection.prepareStatement("SELECT * FROM funcionario");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setIdFuncionario(rs.getInt("idFuncionario")); //nomes colunas bando de dados
                funcionario.setNomeFuncionario(rs.getString("nomeFuncionario"));
                funcionario.setEmailFuncionario(rs.getString("emailFuncionario"));
                funcionario.setEnderecoFuncionario(rs.getString("enderecoFuncionario"));
                funcionario.setCpfFuncionario(rs.getInt("cpfFuncionario"));
                funcionario.setTelefoneFuncionario(rs.getString("telefoneFuncionario"));
                funcionario.setCepFuncionario(rs.getString("cepFuncionario"));
                funcionario.setGeneroFuncionario(rs.getString("generoFuncionario"));
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//ConnectionFactory.closeConnection(connection, stmt,rs);
        }
        return funcionarios;
    }

}
