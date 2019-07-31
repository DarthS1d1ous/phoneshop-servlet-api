package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.history.HistoryService;
import com.es.phoneshop.model.product.history.HttpSessionHistoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductPriceHistoryPageServlet extends HttpServlet {
    private ArrayListProductDao arrayListProductDao;
    private HistoryService historyService;


    @Override
    public void init() {
        this.arrayListProductDao = ArrayListProductDao.getInstance();
        this.historyService = HttpSessionHistoryService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            historyService.update(request, null);
            Long productId = Long.valueOf(request.getPathInfo().substring(1));
            request.setAttribute("product", arrayListProductDao.getProduct(productId));
            request.getRequestDispatcher("/WEB-INF/pages/productPriceHistory.jsp").forward(request, response);
        } catch (ProductNotFoundException e) {
            response.setStatus(404);
            request.getRequestDispatcher("/WEB-INF/pages/productNotFound.jsp").forward(request, response);
        }
    }
}
