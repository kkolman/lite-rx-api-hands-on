package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Learn how to transform values.
 *
 * @author Sebastien Deleuze
 */
public class Part04Transform {

    Mono<User> capitalizeOne(Mono<User> mono) {
        return mono.map(u -> capitalizeUser(u));
    }

    Flux<User> capitalizeMany(Flux<User> flux) {
        return flux.map(u -> capitalizeUser(u));
    }

    Flux<User> asyncCapitalizeMany(Flux<User> flux) {
        return flux.flatMap(u -> asyncCapitalizeUser(u));
    }

    Mono<User> asyncCapitalizeUser(User u) {
        return Mono.just(capitalizeUser(u));
    }

    static User capitalizeUser(User u) {
        return new User(u.getUsername().toUpperCase(), u.getFirstname().toUpperCase(), u.getLastname().toUpperCase());
    }

}
