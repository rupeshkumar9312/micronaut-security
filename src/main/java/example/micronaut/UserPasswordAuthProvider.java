package example.micronaut;

import io.micronaut.http.annotation.Controller;
import io.micronaut.security.authentication.*;
import io.reactivex.Flowable;
import org.reactivestreams.Publisher;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Collections;
import java.util.Map;

@Singleton
@Controller("/auth")
public class UserPasswordAuthProvider implements AuthenticationProvider {

    @Inject
    UsersStore store;

    @Override
    public Publisher<AuthenticationResponse> authenticate(AuthenticationRequest authenticationRequest) {

        String username = authenticationRequest.getIdentity().toString();
        String password = authenticationRequest.getSecret().toString();
        if (password.equals(store.getUserPassword(username))) {
            UserDetails details = new UserDetails(username, Collections.singletonList(store.getUserRole(username)));


            return Flowable.just(details);
        } else {
            return Flowable.just(new AuthenticationFailed());
        }
    }


}