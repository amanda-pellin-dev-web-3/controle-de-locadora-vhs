package br.edu.foz.ifpr.controle_de_locadora_vhs.filters;

import java.io.IOException;

import org.springframework.stereotype.Component;

import br.edu.foz.ifpr.controle_de_locadora_vhs.entities.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("usuarioLogado");
        }

        String uri = httpRequest.getRequestURI();

    Boolean acessoLiberado = uri.startsWith("/css")
            || uri.startsWith("/login")
            || uri.startsWith("/register")
            || uri.startsWith("/logout")
            || uri.endsWith(".jpg")
            || uri.endsWith(".png")
            || uri.endsWith(".css")
            || uri.endsWith(".js");

        if (acessoLiberado || user != null) {
            chain.doFilter(httpRequest, httpResponse);
        } else {
            httpResponse.sendRedirect("/login");
            return;
        }
    }
}
