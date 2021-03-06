/*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.pivotal.literx;

import java.time.Duration;
import java.util.function.Supplier;

import io.pivotal.literx.domain.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

public class Part03StepVerifier {

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then completes successfully.
	void expectFooBarComplete(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo").expectNext("bar").expectComplete().verify();
	}

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits "foo" and "bar" elements then a RuntimeException error.
	void expectFooBarError(Flux<String> flux) {
		StepVerifier.create(flux).expectNext("foo").expectNext("bar").expectError(RuntimeException.class).verify();
	}

//========================================================================================

	// TODO Use StepVerifier to check that the flux parameter emits a User with "swhite"username
	// and another one with "jpinkman" then completes successfully.
	void expectSkylerJesseComplete(Flux<User> flux) {
		StepVerifier.create(flux).assertNext(user -> assertThat("swhite").isEqualTo(user.getUsername()))
				.assertNext(user -> assertThat("jpinkman").isEqualTo(user.getUsername())).verifyComplete();
	}

//========================================================================================

	// TODO Expect 10 elements then complete and notice how long the test takes.
	void expect10Elements(Flux<Long> flux) {
		StepVerifier.withVirtualTime(() -> flux)
				.expectSubscription()
				.thenRequest(Long.MAX_VALUE)
				.expectNextCount(10)
				.expectComplete();
	}

//========================================================================================

	// TODO Expect 3600 elements at intervals of 1 second, and verify quicker than 3600s
	// by manipulating virtual time thanks to StepVerifier#withVirtualTime, notice how long the test takes
	//EGB --> This one is not clear ... too late, think on it tomorrow...
	void expect3600Elements(Supplier<Flux<Long>> supplier) {
		StepVerifier.withVirtualTime(() -> Mono.delay(Duration.ofHours(3)))
				.expectSubscription()
				.expectNoEvent(Duration.ofHours(2))
				.thenAwait(Duration.ofHours(1))
				.expectNextCount(1)
				.expectComplete()
				.verify();
	}

	private void fail() {
		throw new AssertionError("workshop not implemented");
	}

}
