package com.icarusfrog.graphql.hoverfly_exp;

import io.specto.hoverfly.junit.core.Hoverfly;
import io.specto.hoverfly.junit.core.HoverflyMode;
import io.specto.hoverfly.junit.core.SimulationSource;
import io.specto.hoverfly.junit.dsl.HoverflyDsl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import static io.specto.hoverfly.junit.core.HoverflyConfig.localConfigs;

public class HoverflyExp {

    @Test
    public void testHoverflySetup() {
        // Placeholder for Hoverfly experiment setup
    }

    @Test
    void exampleHoverFly() {
        try(Hoverfly hoverfly = new Hoverfly(localConfigs().proxyPort(8083).asWebServer(), HoverflyMode.SIMULATE)) {
            hoverfly.start();
            hoverfly.simulate(
                    SimulationSource.dsl(
                            HoverflyDsl.service("localhost:8083")
                                    .andDelay(1, TimeUnit.MILLISECONDS).forAll()
                                    .get("/api/bookings/1")
                                    .willReturn(
                                            HoverflyDsl.response()
                                                    .status(201)
                                                    .body("{ \"id\": 1, \"name\": \"Booking 1\" }")
                                                    .withFixedDelay(1, TimeUnit.MILLISECONDS)
                                    )
                    )
            );

            hoverfly.reset();
        }
    }

    @Test
    void exampleHoverFlySpy() {
        try(Hoverfly hoverfly = new Hoverfly(localConfigs(), HoverflyMode.SPY)) {
            hoverfly.start();

            hoverfly.reset();
        }
    }

    @Test
    void exampleHoverFlyWithCapture() {
        try(Hoverfly hoverfly = new Hoverfly(localConfigs().enableStatefulCapture().proxyPort(8084), HoverflyMode.CAPTURE)) {
            hoverfly.start();

            hoverfly.exportSimulation(Path.of("./captured-simulation.json"));
            hoverfly.reset();
        }
    }

    @Test
    void exampleHoverFlyWithDiffing() {
        try(Hoverfly hoverfly = new Hoverfly(localConfigs(), HoverflyMode.DIFF)) {
            hoverfly.start();
            hoverfly.simulate(SimulationSource.classpath("simulation-to-compare.json"));

            hoverfly.assertThatNoDiffIsReported(false);
            hoverfly.reset();
        }
    }
}
