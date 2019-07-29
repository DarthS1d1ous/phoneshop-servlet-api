package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.enums.OutputOrder;
import com.es.phoneshop.model.product.enums.SortBy;
import com.es.phoneshop.model.product.history.HistoryService;
import com.es.phoneshop.model.product.history.HttpSessionHistoryService;


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
        OutputOrder outputOrder = OutputOrder.ASC;
        if (request.getParameter(ORDER) != null) {
            outputOrder = OutputOrder.valueOf(request.getParameter(ORDER));
        }
        SortBy sortBy = null;
        if (request.getParameter(SORT) != null) {
            sortBy = SortBy.valueOf(request.getParameter(SORT));
        }
        if (query == null) {
            query = "";
        }
        request.setAttribute("products", arrayListProductDao.findProducts(query, outputOrder, sortBy));
        request.getRequestDispatcher("/WEB-INF/pages/productList.jsp").forward(request, response);
    }

}
