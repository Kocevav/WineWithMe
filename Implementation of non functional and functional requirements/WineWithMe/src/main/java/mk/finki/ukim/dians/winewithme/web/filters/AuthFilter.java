package mk.finki.ukim.dians.winewithme.web.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.finki.ukim.dians.winewithme.model.User;

import java.io.IOException;

@WebFilter(filterName = "AuthFilter",urlPatterns = {"/*"},
        initParams = {@WebInitParam(name = "ignore-path1",value = "/login"),@WebInitParam(name = "ignore-path2",value = "/register"),
                @WebInitParam(name = "ignore-path3",value = "/homepage"),@WebInitParam(name = "ignore-path4",value = "/auth-status")})
public class AuthFilter implements Filter {
    private String[] ignorePaths;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ignorePaths=new String[4];
        ignorePaths[0]=filterConfig.getInitParameter("ignore-path1");
        ignorePaths[1]=filterConfig.getInitParameter("ignore-path2");
        ignorePaths[2]=filterConfig.getInitParameter("ignore-path3");
        ignorePaths[3]=filterConfig.getInitParameter("ignore-path4");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        User user= (User) req.getSession().getAttribute("User");
        String uri=req.getRequestURI();


        if(user!=null || uri.contains(ignorePaths[0]) || uri.contains(ignorePaths[1]) || uri.contains(ignorePaths[2])  || uri.contains(ignorePaths[3])){
            filterChain.doFilter(req,resp);
        }else{
            resp.sendRedirect("/homepage");
        }



    }
}