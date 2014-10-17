package com.example.walter.thirdlab.math;

public final class Maths {

    public static final double ONE_EIGHTY_DEGREES = Math.PI;

    public static final double THREE_SIXTY_DEGREES = ONE_EIGHTY_DEGREES * 2;

    public static final double ONE_TWENTY_DEGREES = THREE_SIXTY_DEGREES / 3;

    public static final double NINETY_DEGREES = Math.PI / 2;

    public static final double FOURTY_DEGREES = Math.PI / 4;

    private static final long POWER_CLAMP = 0x00000000ffffffffL;

    /**
     * Constructor, although not used at the moment.
     */
    private Maths() {
    }

    public static int power(final int base, final int raise) {
        int p = 1;
        long b = raise & POWER_CLAMP;

        long powerN = base;

        while (b != 0) {
            if ((b & 1) != 0) {
                p *= powerN;
            }
            b >>>= 1;
            powerN = powerN * powerN;
        }

        return p;
    }
}
