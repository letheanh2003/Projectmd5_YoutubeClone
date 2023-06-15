package rikkei.academy.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.OncePerRequestFilter;
import rikkei.academy.security.userPrincipal.CustomUserDetails;
import rikkei.academy.security.userPrincipal.CustomUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomUserDetailsService userDetailsService;
    public String getTokenFromRequest(HttpServletRequest request){
        String bearToken = request.getHeader("Authorization");
        if(bearToken!=null&&bearToken.startsWith("Bearer")){
            return bearToken.substring(7);
        }
        return null;
//Bearer nmasfgfgusidgbfjlheficoehfcujegfuig
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getTokenFromRequest(request);
            if (jwt!=null && jwtTokenProvider.validateToken(jwt)){
                String username = jwtTokenProvider.getUsernameFromJWTtoken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails!= null){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        }catch (Exception e){
            log.error("fail authentication =>>>"+ e);
        }
        filterChain.doFilter(request,response);
    }
}
