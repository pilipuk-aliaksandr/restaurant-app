package by.pilipuk.gateway.spec.entrypoint;

import by.pilipuk.gateway.core.security.JwtTokenProvider;
import by.pilipuk.gateway.environment.service.AuthTestService;
import by.pilipuk.common.spec.entrypoint.BaseControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;
 
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Test all methods from AuthController.class")
class AuthControllerTest extends BaseControllerTest {

    @Autowired
    private AuthTestService authTestService;

    @MockitoSpyBean
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void authenticateCurrentUser() {
        //given
        var authRequest = authTestService.createAuthRequest();

        String fixedAccessToken = "fixed_access_token";
        String fixedRefreshToken = "fixed_refresh_token";

        //when
        doReturn(fixedAccessToken).when(jwtTokenProvider).generateAccessToken(any());
        doReturn(fixedRefreshToken).when(jwtTokenProvider).generateRefreshToken(any());

        authTestService.saveUser();
        var expectedAuthResponse = authTestService.createAuthResponse();

        //then
        performAuthRequest("/v1/login", authRequest, expectedAuthResponse);
    }

    @Test
    void registerNewUser() {
        //given

        //when

        //then
        performPostRequest("/v1/registration", authTestService.createUserWriteDto());
    }
}