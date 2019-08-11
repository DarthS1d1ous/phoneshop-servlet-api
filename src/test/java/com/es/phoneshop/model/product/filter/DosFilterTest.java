package com.es.phoneshop.model.product.filter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    @Mock
    private ServletRequest servletRequest;
    @Mock
    private HttpServletResponse response;
    @Mock
    private ServletResponse servletResponse;
    @Mock
    private FilterChain filterChain;
    @Mock
    private FilterConfig filterConfig;
    @Mock
    private Map<String, DosFilter.Connection> ipCounter;
    @InjectMocks
    private DosFilter dosFilter;

    @Before
    public void setup() {
        when(servletRequest.getRemoteAddr()).thenReturn("");
    }

    @Test
    public void testInit() {
        when(filterConfig.getInitParameter("maxConnectionsPerMinute")).thenReturn("20");

        dosFilter.init(filterConfig);

        verify(filterConfig).getInitParameter("maxConnectionsPerMinute");
    }

    @Test
    public void doFilterNullConnection() throws IOException, ServletException {
        dosFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(ipCounter).put(eq(""), any(DosFilter.Connection.class));
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void doFilterRemoveConnection() throws IOException, ServletException {
        DosFilter.Connection connection = new DosFilter.Connection(LocalDateTime.MIN);
        String ip = servletRequest.getRemoteAddr();
        when(ipCounter.get(ip)).thenReturn(connection);

        dosFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(ipCounter).remove(ip);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

    @Test
    public void doFilterPutConnection() throws IOException, ServletException {
        DosFilter.Connection connection = new DosFilter.Connection(LocalDateTime.now());
        String ip = servletRequest.getRemoteAddr();
        when(ipCounter.get(ip)).thenReturn(connection);

        dosFilter.doFilter(servletRequest, servletResponse, filterChain);

        verify(ipCounter).put(ip, connection);
        verify(filterChain).doFilter(servletRequest, servletResponse);
    }

//    @Test
//    public void doFilterMaxConnectionsPerMinute() throws IOException, ServletException {
//        DosFilter.Connection connection = new DosFilter.Connection(LocalDateTime.now());
//        String ip = servletRequest.getRemoteAddr();
//        when(ipCounter.get(ip)).thenReturn(connection);
//
//        dosFilter.doFilter(servletRequest, response, filterChain);
//
//        verify(response).setStatus(429);
//    }
}