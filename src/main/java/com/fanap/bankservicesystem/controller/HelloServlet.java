package com.fanap.bankservicesystem.controller;

import com.fanap.bankservicesystem.business.account.impl.BankAccountImpl;
import com.fanap.bankservicesystem.business.service.Bank;
import com.fanap.bankservicesystem.business.service.BankImpl;
import com.fanap.bankservicesystem.business.util.FileUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        FileUtil.creatExample();
        try {
            FileUtil.readBankDataFromTheFile();
            BankImpl.getInstance().listAccounts();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
        try {
            FileUtil.writeBankDataToTheFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}