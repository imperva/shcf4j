package com.imperva.shcf4j.auth;

import java.security.Principal;

public interface Credentials {

    Principal getUserPrincipal();

    String getPassword();

}
