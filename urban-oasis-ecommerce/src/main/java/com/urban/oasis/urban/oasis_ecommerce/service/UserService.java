package com.urban.oasis.urban.oasis_ecommerce.service;

import com.urban.oasis.urban.oasis_ecommerce.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt) throws Exception;
    User findUserByEmail(String email) throws Exception;
}
