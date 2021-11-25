package fr.r1r0r0.deltaengine.model.sprites;

public final class Scale {
    /**
     * Default scale
     * scale is sometime useful for mirroring for example
     * dimension could not be used for this as they have to be positive
     */
    public final static Double DEFAULT_SCALE_X = 1.0;
    public final static Double DEFAULT_SCALE_Y = 1.0;
    public final static Scale DEFAULT_SCALE = new Scale(DEFAULT_SCALE_X, DEFAULT_SCALE_Y);

    private final double scaleX;
    private final double scaleY;

    /**
     * Constructor
     * @param scaleX the width
     * @param scaleY the height
     */
    public Scale (double scaleX, double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    /**
     * Return the attribute scaleX
     * @return the attribute scaleX
     */
    public double getScaleX () {
        return scaleX;
    }

    /**
     * Return the attribute scaleY
     * @return the attribute scaleY
     */
    public double getScaleY () {
        return scaleY;
    }
}
