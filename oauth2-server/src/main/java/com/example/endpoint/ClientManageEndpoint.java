package com.example.endpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationService;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.bind.annotation.*;

@FrameworkEndpoint
@SessionAttributes("authorizationRequest")
public class ClientManageEndpoint {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ClientDetailsService clientDetailsService;
    @Autowired
    private ClientRegistrationService clientRegistrationService;


    private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();

    public ClientManageEndpoint(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * @param exceptionTranslator the exception translator to set
     */
    public void setExceptionTranslator(WebResponseExceptionTranslator exceptionTranslator) {
        this.exceptionTranslator = exceptionTranslator;
    }

    @RequestMapping(value = "/oauth/clients/{clientId}",  method = RequestMethod.GET)
    @ResponseBody
    public Object checkToken(@PathVariable("clientId") String clientId) {
        ClientDetails client = clientDetailsService.loadClientByClientId(clientId);
        if (client == null) {
            throw new InvalidClientException("Client was not recognised");
        }
        return client;

    }
}
