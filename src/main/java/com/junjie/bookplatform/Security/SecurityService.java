package com.junjie.bookplatform.Security;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    void autoLogin(String username, String password, HttpServletRequest request);
}
