/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package filter;

import java.io.IOException;
import javax.servlet.*;

/**
 *
 * @author cuonglm
 */
public class EncodingFilter
        implements Filter {

    public EncodingFilter() {
    }

    @Override
    public void init(FilterConfig filterconfig)
            throws ServletException {
        filterConfig = filterconfig;
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
            throws IOException, ServletException {
        servletrequest.setCharacterEncoding(encoding);
        filterchain.doFilter(servletrequest, servletresponse);
    }

    @Override
    public void destroy() {
    }

    private String encoding;
    private FilterConfig filterConfig;
}
