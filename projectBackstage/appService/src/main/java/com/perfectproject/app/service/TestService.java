package com.perfectproject.app.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface TestService {
    Map<String,Object> test(HttpServletRequest request);
}
