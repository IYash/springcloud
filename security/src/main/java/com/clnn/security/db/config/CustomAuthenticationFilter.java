package com.clnn.security.db.config;

import com.clnn.security.db.entity.AuthenticationBean;
import com.clnn.security.db.entity.UserDO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        //attempt Authentication when Content-Type is json
        String ct = request.getContentType();
        if( null == ct) super.attemptAuthentication(request, response);
        if(request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                ||request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)){
            String mType = request.getMethod();
            //use jackson to deserialize json
            ObjectMapper mapper = new ObjectMapper();
            CustomUsernamePasswordAuthenticationToken authRequest = null;
            try (InputStream is = request.getInputStream()){
                AuthenticationBean authenticationBean = mapper.readValue(is,AuthenticationBean.class);
                authRequest = new CustomUsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword(),authenticationBean.getVcode());
            }catch (IOException e) {
                e.printStackTrace();
                new CustomUsernamePasswordAuthenticationToken(
                        "", "","");
            }finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }

        //transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }
    }

}
