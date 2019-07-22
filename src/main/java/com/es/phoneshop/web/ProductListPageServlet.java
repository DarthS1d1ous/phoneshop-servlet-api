package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ProductListPageServlet extends HttpServlet {
    private static final String ORDER = "order";
    private static final String SORT = "sort";

    private ArrayListProductDao arrayListProductDao;


    @Override
    public void init() {
        this.arrayListProductDao = ArrayListProductDao.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        String order = null;
        if (request.getParameter(ORDER) != null) {
            order = request.getParameter(ORDER);
        }
        String sortBy = null;
        if (request.getParameter(SORT) != null) {
            sortBy = request.getParameter(SORT);
        }

        request.setAttribute("products", arrayListProductDao.findProducts(query,order,sortBy));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
