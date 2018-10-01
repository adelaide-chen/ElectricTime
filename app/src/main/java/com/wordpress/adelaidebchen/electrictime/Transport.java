package com.wordpress.adelaidebchen.electrictime;

class Transport {
    private final String mode;
    private final double speed;
    private final int range;

    public Transport(String mode, double speed, int range) {
        this.mode = mode;
        this.speed = speed;
        this.range = range;
    }

    public String getMode() {
        return mode;
    }

    public double getSpeed() {
        return speed;
    }

    public int getRange() {
        return range;
    }

}

