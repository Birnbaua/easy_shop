package com.birnbaua.easyshop.auth;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
@Component
class SpringSecurityAuditorAware implements AuditorAware<String> { 
     
     private static final String EMPTY_AUDITOR = "";
 
     @Override
     public Optional<String> getCurrentAuditor() {
          Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
          if (authentication == null) {
                return Optional.of(EMPTY_AUDITOR);
          }
 
          Object object = authentication.getPrincipal();
          if (!(object instanceof UserDetails)) {
                return Optional.of(EMPTY_AUDITOR);
          }
 
          return Optional.of(((UserDetails) object).getUsername());
     }
}