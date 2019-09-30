package pl.martajastrzabek.homebudget.dao;

import pl.martajastrzabek.homebudget.factory.ConnectionFactory;
import pl.martajastrzabek.homebudget.model.Transaction;
import pl.martajastrzabek.homebudget.model.TransactionType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    private Connection connection;

    public TransactionDAO(ConnectionFactory connectionFactory) throws SQLException, ClassNotFoundException {
        connection = connectionFactory.createConnection();
    }

    public void save(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transaction (type, description, amount, date) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, transaction.getType().toString());
        preparedStatement.setString(2, transaction.getDescription());
        preparedStatement.setBigDecimal(3, transaction.getAmount());
        preparedStatement.setDate(4, transaction.getDate());

        preparedStatement.executeUpdate();
    }

    public Transaction read(long id) throws SQLException {
        String query = "SELECT * FROM transaction WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return getTransaction(resultSet);
        }
        return null;
    }

    private Transaction getTransaction(ResultSet resultSet) throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setId(resultSet.getLong("id"));
        transaction.setType(TransactionType.valueOf(resultSet.getString("type")));
        transaction.setDescription(resultSet.getString("description"));
        transaction.setAmount(resultSet.getBigDecimal("amount"));
        transaction.setDate(resultSet.getDate("date"));

        return transaction;
    }

    public void update(Transaction transaction) throws SQLException {
        String query = "UPDATE transaction SET type = ?, description = ?, amount = ?, date = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, transaction.getType().toString());
        preparedStatement.setString(2, transaction.getDescription());
        preparedStatement.setBigDecimal(3, transaction.getAmount());
        preparedStatement.setDate(4, transaction.getDate());
        preparedStatement.setLong(5, transaction.getId());

        preparedStatement.executeUpdate();
    }

    public void delete(long id) throws SQLException {
        String query = "DELETE FROM transaction WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    public List<Transaction> getAllTransaction() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transaction";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Transaction transaction = getTransaction(resultSet);
            transactions.add(transaction);
        }
        return transactions;
    }

    public List<Transaction> getTransactionByType(TransactionType type) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transaction";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Transaction transaction = getTransaction(resultSet);
            if (transaction.getType() == type) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public void close() throws SQLException {
        connection.close();
    }
}
