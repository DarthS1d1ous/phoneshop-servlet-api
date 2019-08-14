package com.es.phoneshop.model.product.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class DosFilter implements Filter {

    private static int maxConnectionsPerMinute;
    private Map<String, Connection> ipCounter;

    @Override
    public void init(FilterConfig filterConfig) {
        maxConnectionsPerMinute = Integer.valueOf(filterConfig.getInitParameter("maxConnectionsPerMinute"));
        ipCounter = new HashMap<>();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String ip = servletRequest.getRemoteAddr();
        if (ipCounter.get(ip) == null) {
            ipCounter.put(ip, new Connection(LocalDateTime.now()));
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            Connection connection = ipCounter.get(ip);
            if (connection.firstConnection.until(LocalDateTime.now(), ChronoUnit.SECONDS) > 60) {
                ipCounter.remove(ip);
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (maxConnectionsPerMinute > connection.totalConnections.incrementAndGet()) {
                ipCounter.put(ip, connection);
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse) servletResponse).setStatus(429);
            }
        }
    }

    @Override
    public void destroy() {

    }

    protected static class Connection {
        private final LocalDateTime firstConnection;
        private final AtomicInteger totalConnections;

        Connection(LocalDateTime firstConnection) {
            this.totalConnections = new AtomicInteger(0);
            this.firstConnection = firstConnection;
        }
    }
}
