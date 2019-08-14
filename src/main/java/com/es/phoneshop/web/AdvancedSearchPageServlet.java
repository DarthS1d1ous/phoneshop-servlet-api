package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.advancedSearch.AdvancedSearchServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;

public class AdvancedSearchPageServlet extends HttpServlet {

    private ArrayListProductDao arrayListProductDao;
    private AdvancedSearchServiceImp advancedSearchServiceImp;

    @Override
    public void init() {
        this.arrayListProductDao = ArrayListProductDao.getInstance();
        this.advancedSearchServiceImp = AdvancedSearchServiceImp.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String description;
        if (request.getParameter("description") == null) {
            description = "";
        } else {
            description = request.getParameter("description");
        }
        boolean error = false;
        int minPrice = 0;
        int maxPrice = 0;
        int minStock = 0;
        int maxStock = 0;
        try {
            minPrice = parseValue(request, "minPrice");
            maxPrice = parseValue(request, "maxPrice");
            minStock = parseValue(request, "minStock");
            maxStock = parseValue(request, "maxStock");

        } catch (NumberFormatException | ParseException e) {
            error = true;
        }
        if (error) {
            request.setAttribute("error", "Incorrect data");
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        } else {
            request.setAttribute("products", advancedSearchServiceImp.findProducts(description, minPrice, maxPrice, minStock, maxStock));
            request.getRequestDispatcher("/WEB-INF/pages/advancedSearch.jsp").forward(request, response);
        }
    }

    private int parseValue(HttpServletRequest request, String parameter) throws ParseException, NumberFormatException {
        String value = request.getParameter(parameter);
        if (value == null || value.equals(""))
            return Integer.MIN_VALUE;
        return NumberFormat.getInstance(request.getLocale()).parse(value).intValue();
    }
}

