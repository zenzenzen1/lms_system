// package com.example.identity_service.configuration;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
// import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
// import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
// import org.springframework.security.oauth2.client.registration.ClientRegistration;
// import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
// import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
// import org.springframework.security.oauth2.client.web.AuthenticatedPrincipalOAuth2AuthorizedClientRepository;
// import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;

// @Configuration
// public class OAuth2LoginConfig {
//     private static String CLIENT_ID =
//
// "552949554697-t767e16mq1fo2pbtluunhe6nkpfbds9u.apps.googleusercontent.com552949554697-t767e16mq1fo2pbtluunhe6nkpfbds9u.apps.googleusercontent.com";

//     private static String CLIENT_SECRET = "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";

//     @Bean
//     public ClientRegistrationRepository clientRegistrationRepository() {
//         return new InMemoryClientRegistrationRepository(this.googleClientRegistration());
//     }

//     @Bean
//     public OAuth2AuthorizedClientService authorizedClientService(
//             ClientRegistrationRepository clientRegistrationRepository) {
//         return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
//     }

//     @Bean
//     public OAuth2AuthorizedClientRepository authorizedClientRepository(
//             OAuth2AuthorizedClientService authorizedClientService) {
//         return new AuthenticatedPrincipalOAuth2AuthorizedClientRepository(authorizedClientService);
//     }

//     private ClientRegistration googleClientRegistration() {
//         return CommonOAuth2Provider.GOOGLE
//                 .getBuilder("google")
//                 .clientId(CLIENT_ID)
//                 .clientSecret(CLIENT_SECRET)
//                 .build();
//     }
// }
