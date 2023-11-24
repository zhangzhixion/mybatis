package com.powernode.bank.web;

import com.powernode.bank.exceptions.MoneyNotEnoughException;
import com.powernode.bank.exceptions.TransferException;
import com.powernode.bank.service.AccountService;
import com.powernode.bank.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/transfer")
public class AccountServlet extends HttpServlet {
    private AccountService accountService = new AccountServiceImpl();
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取值
        String fromActon = req.getParameter("fromActon");
        String toActon = req.getParameter("toActon");
        Double money = Double.parseDouble(req.getParameter("money"));
        //调用service的方法完成转账
        try {
            accountService.transfer(fromActon,toActon,money);
            resp.sendRedirect("/success.html");
        } catch (MoneyNotEnoughException e) {
            resp.sendRedirect("error1.html");
        } catch (TransferException e) {
            resp.sendRedirect("error2.html");
        }catch (Exception e){
            resp.sendRedirect("error2.html");
        }
        //调用view完成数据的显示
    }
}
