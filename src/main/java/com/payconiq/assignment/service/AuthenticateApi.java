package com.payconiq.assignment.service;

import com.payconiq.assignment.controller.AuthenticateApiController;
import com.payconiq.assignment.model.AuthenticationRequest;
import com.payconiq.assignment.model.AuthenticationResponse;


/**
 * A delegate to be called by the {@link AuthenticateApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface AuthenticateApi {

    /**
     * POST /authenticate : Authenticate the user
     * Authenticate user by credentials and generate JWT
     *
     * @param authenticationRequest has userName and password to authenticate (required)
     * @return successful operation - jwt response (status code 200)
     *         or Invalid Request (status code 400)
     *         or not found (status code 404)
     */
    AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest);
}
