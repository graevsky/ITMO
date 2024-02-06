package org.example.Enums;

import java.util.Random;


public enum CelestialBody {
    MOON("Moon"), SUN("Sun"), MARS("Mars");

    private final String body;

    CelestialBody(String body) {
        this.body = body;
    }

    public static CelestialBody getRandomCelestialBody() {
        CelestialBody[] bodies = CelestialBody.values();
        int index = new Random().nextInt(bodies.length);
        return bodies[index];
    }

    @Override
    public String toString() {
        return body;
    }
}

