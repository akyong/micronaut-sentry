package micronaut.error.log.using.sentry;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import io.sentry.event.UserBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/sentry")
public class SentryController {
    private final String email = "ang.kionglam.aries@gmail.com"; //Change This
    private Logger log = LoggerFactory.getLogger(SentryController.class);

    void unsafeMethod() {
        throw new UnsupportedOperationException("You shouldn't call this!");
    }

    @Get("/error")
    void logWithStaticAPI() {

        Sentry.init("https://8dd8b8ccf4904fd9a606f73c1e1f0322@sentry.io/1527843");

        /*
         Record a breadcrumb in the current context which will be sent
         with the next event(s). By default the last 100 breadcrumbs are kept.
         */
        Sentry.getContext().recordBreadcrumb(
                new BreadcrumbBuilder().setMessage("User made an action").build()
        );

        // Set the user in the current context.
        Sentry.getContext().setUser(
                new UserBuilder().setEmail(email).build()
        );

        /*
         This sends a simple event to Sentry using the statically stored instance
         that was created in the ``main`` method.
         */
        Sentry.capture("ERROR - TEST SEDERHANA");

        try {
            unsafeMethod();
        } catch (Exception e) {
            // This sends an exception event to Sentry using the statically stored instance
            // that was created in the ``main`` method.
            Sentry.capture(e);
        }
    }
}
