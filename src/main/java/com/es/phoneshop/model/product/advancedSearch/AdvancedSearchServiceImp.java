package com.es.phoneshop.model.product.advancedSearch;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.PriceHistory;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdvancedSearchServiceImp implements AdvancedSearchService {
    private ArrayListProductDao arrayListProductDao;

    public AdvancedSearchServiceImp() {
        this.arrayListProductDao = ArrayListProductDao.getInstance();
    }


    public static AdvancedSearchServiceImp getInstance() {
        return AdvancedSearchServiceImp.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final AdvancedSearchServiceImp instance = new AdvancedSearchServiceImp();
    }


    @Override
    public List<Product> findProducts(String description, int minPrice, int maxPrice, int minStock, int maxStock) {

        String[] words = description
                .trim()
                .toLowerCase()
                .split(" ");
        if(maxPrice == Integer.MIN_VALUE){
            maxPrice = Integer.MAX_VALUE;
        }
        if(maxStock == Integer.MIN_VALUE){
            maxStock = Integer.MAX_VALUE;
        }
        int finalMaxPrice = maxPrice;
        int finalMaxStock = maxStock;
        return arrayListProductDao.findProducts().stream()
                .filter(product -> minPrice <= product.getPrice().intValue()
                        && product.getPrice().intValue() <= finalMaxPrice
                        && minStock <= product.getStock()
                        && product.getStock() <= finalMaxStock)
                .collect(Collectors.toMap(Function.identity(), product -> Arrays.stream(words)
                        .filter(x -> product.getDescription().toLowerCase().contains(x)).count()))
                .entrySet().stream()
                .filter(x -> x.getValue() > 0)
                .sorted(Comparator.comparing(Map.Entry<Product, Long>::getValue).reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
