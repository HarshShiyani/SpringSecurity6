package com.harsh.securebank.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.CollectionUtils;

// This converter used to convert Keycloak roles to Granted Authority (with ROLE_ prefix)
public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwtToken) {
        // token will have 'realm_access' key that contains the roles details
        Map<String, Object> realmAccess = (Map<String, Object>) jwtToken.getClaims().get("realm_access");

        if(CollectionUtils.isEmpty(realmAccess))
            return new ArrayList<>();

        // 'roles' key comes under the 'realm-access' key in the token
        List<String> roles = (List<String>) realmAccess.get("roles");

        return roles.stream().map(roleValue -> "ROLE_" + roleValue)
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toList());
    }
}
