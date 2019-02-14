/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 * Learn how to use StepVerifier to test Mono, Flux or any other kind of Reactive Streams Publisher.
 *
 * @author Sebastien Deleuze
 * @see <a href="http://projectreactor.io/docs/test/release/api/reactor/test/StepVerifier.html">StepVerifier Javadoc</a>
 */
public class Part03StepVerifier {

    void expectFooBarComplete(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .expectComplete()
                .verify();
    }

    void expectFooBarError(Flux<String> flux) {
        StepVerifier.create(flux)
                .expectNext("foo")
                .expectNext("bar")
                .verifyError(RuntimeException.class);
    }

    void expectSkylerJesseComplete(Flux<User> flux) {
        StepVerifier.create(flux)
                .assertNext(u -> assertThat(u.getUsername()).isEqualTo("swhite"))
                .assertNext(u -> assertThat(u.getUsername()).isEqualTo("jpinkman"))
                .verifyComplete();
    }

    void expect10Elements(Flux<Long> flux) {
        StepVerifier.create(flux)
                .expectNextCount(10L)
                .verifyComplete();
    }

    void expect3600Elements(Supplier<Flux<Long>> supplier) {
        StepVerifier.withVirtualTime(supplier)
                .expectSubscription()
                .thenAwait(Duration.ofSeconds(1L))
                .expectNext(0L)
                .thenAwait(Duration.ofSeconds(1L))
                .expectNext(1L)
                .thenAwait(Duration.ofSeconds(3600L))
                .expectNextCount(3600L - 2L)
                .verifyComplete();
    }

}
