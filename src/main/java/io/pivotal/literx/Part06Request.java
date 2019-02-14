package io.pivotal.literx;

import io.pivotal.literx.domain.User;
import io.pivotal.literx.repository.ReactiveRepository;
import io.pivotal.literx.repository.ReactiveUserRepository;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to control the demand.
 *
 * @author Sebastien Deleuze
 */
public class Part06Request {

    ReactiveRepository<User> repository = new ReactiveUserRepository();

    StepVerifier requestAllExpectFour(Flux<User> flux) {
        return StepVerifier.create(flux)
                .expectNextCount(4L)
                .expectComplete();
    }

    StepVerifier requestOneExpectSkylerThenRequestOneExpectJesse(Flux<User> flux) {
        Flux<User> loggingFlux = flux.log();
        return StepVerifier.create(loggingFlux)
                .expectNext(User.SKYLER)
                .expectNext(User.JESSE)
                .thenCancel();

    }

    Flux<User> fluxWithLog() {
        return repository.findAll().log();
    }

    Flux<User> fluxWithDoOnPrintln() {
        return repository.findAll()
                .doOnSubscribe(subscriber -> System.out.println("Starting: " + subscriber))
                .doOnNext(u -> System.out.println(String.format("%s - %s", u.getFirstname(), u.getLastname())))
                .doOnComplete(() -> System.out.println("The end !"));
    }

}
