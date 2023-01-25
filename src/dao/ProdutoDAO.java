package dao;

import factory.ConnectionFactory;
import modelo.Produto;
import java.sql.*;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ProdutoDAO {

    private Connection connection;

    int idProduto;
    String nomeProduto;
    String descricaoProduto;
    String dataValidade;
    int quantidadeProduto;
    double valorUnitario;

    public ProdutoDAO() {
        this.connection = new ConnectionFactory().getConnection();
    }

    public void adiciona(Produto produto) {
        String sql = "INSERT INTO produto (nomeProduto, descricaoProduto, dataValidade, quantidadeProduto, valorUnitario) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());
            stmt.setString(3, produto.getDataValidade());
            stmt.setInt(4, produto.getQuantidadeProduto());
            stmt.setDouble(5, produto.getValorUnitario());     
            stmt.execute();
            stmt.close();
        } catch (SQLException u) {
            throw new RuntimeException(u);
        }

    }

    //Editar informações, método update
    public void update(Produto produto) {
        String sql = "UPDATE produto SET nomeProduto = ?, descricaoProduto = ?, dataValidade = ?, quantidadeProduto = ?, valorUnitario = ? WHERE idProduto = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, produto.getNomeProduto());
            stmt.setString(2, produto.getDescricaoProduto());
            stmt.setString(3, produto.getDataValidade());
            stmt.setInt(4, produto.getQuantidadeProduto());
            stmt.setDouble(5, produto.getValorUnitario());
            stmt.setInt(6, produto.getIdProduto());
            

            stmt.execute();
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar!");
            throw new RuntimeException(u);
        }
    }
    //Deleta informações do banco de dados

    public void delete(Produto produto) {
        String sql = "DELETE FROM produto WHERE idProduto = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, produto.getIdProduto());//necessario somente o id
            stmt.execute();
            JOptionPane.showMessageDialog(null, "Excluido com sucesso!");
            stmt.close();
        } catch (SQLException u) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir!");
            throw new RuntimeException(u);
        }
    }

    public List<Produto> leitura() {
        connection = new ConnectionFactory().getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null; //metodo utilizado para view
        List<Produto> produtos = new ArrayList<>(); //Array para guardar os estados
        try {
            stmt = connection.prepareStatement("SELECT * FROM produto");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Produto produto = new Produto();
                produto.setIdProduto(rs.getInt("idProduto")); //nomes colunas bando de dados
                produto.setNomeProduto(rs.getString("nomeProduto"));
                produto.setDescricaoProduto(rs.getString("descricaoProduto"));
                produto.setDataValidade(rs.getString("dataValidade"));
                produto.setQuantidadeProduto(rs.getInt("quantidadeProduto"));
                produto.setValorUnitario(rs.getDouble("valorUnitario"));                
                produtos.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
//ConnectionFactory.closeConnection(connection, stmt,rs);
        }
        return produtos;
    }


}
