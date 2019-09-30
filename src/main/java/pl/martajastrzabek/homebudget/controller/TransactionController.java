package pl.martajastrzabek.homebudget.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import pl.martajastrzabek.homebudget.dao.TransactionDAO;
import pl.martajastrzabek.homebudget.factory.ConnectionFactory;
import pl.martajastrzabek.homebudget.model.Transaction;
import pl.martajastrzabek.homebudget.model.TransactionType;

import java.sql.SQLException;
import java.util.List;

@Controller
public class TransactionController {

    TransactionDAO transactionDao;

    {
        try {
            transactionDao = new TransactionDAO(new ConnectionFactory());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/")
    public String home(Model model, @RequestParam(required = false) TransactionType type) {
        List<Transaction> transactions = null;
        if (type != null) {
            try {
                transactions = transactionDao.getTransactionByType(type);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                transactions = transactionDao.getAllTransaction();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("transactions", transactions);

        return "index";
    }

    @GetMapping("/add")
    public String addTransactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());

        return "addTransactionForm";
    }

    @PostMapping("/add")
    public String addNewTransaction(Transaction transaction) {
        try {
            transactionDao.save(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/edit")
    public String transactionForm(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "editTransactionForm";
    }

    @PostMapping("/edit")
    public String editTransaction(Transaction transaction) {
        try {
            transactionDao.update(transaction);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "index";
    }

    @GetMapping("/delete")
    public String deleteForm() {
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam long id) {
        try {
            transactionDao.delete(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "index";
    }

}
