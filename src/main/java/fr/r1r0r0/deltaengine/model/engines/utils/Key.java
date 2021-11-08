package fr.r1r0r0.deltaengine.model.engines.utils;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public enum Key {
    A(KeyCode.A), Z(KeyCode.Z), E(KeyCode.E), R(KeyCode.R), T(KeyCode.T), Y(KeyCode.Y), U(KeyCode.U), I(KeyCode.I), O(KeyCode.O), P(KeyCode.P), Q(KeyCode.Q), S(KeyCode.S), D(KeyCode.D), F(KeyCode.F), G(KeyCode.G), H(KeyCode.H), J(KeyCode.J), K(KeyCode.K), L(KeyCode.L), M(KeyCode.M), W(KeyCode.W), X(KeyCode.X), C(KeyCode.C), V(KeyCode.V), B(KeyCode.B), N(KeyCode.N),
    ARROW_UP(KeyCode.UP), ARROW_LEFT(KeyCode.LEFT), ARROW_DOWN(KeyCode.DOWN), ARROW_RIGHT(KeyCode.RIGHT),
    ONE(KeyCode.DIGIT1), TWO(KeyCode.DIGIT2), THREE(KeyCode.DIGIT3), FOUR(KeyCode.DIGIT4), FIVE(KeyCode.DIGIT5), SIX(KeyCode.DIGIT6), SEVEN(KeyCode.DIGIT7), EIGHT(KeyCode.DIGIT8), NINE(KeyCode.DIGIT9), ZERO(KeyCode.DIGIT0),
    ENTER(KeyCode.ENTER), BACK_SPACE(KeyCode.BACK_SPACE), ESCAPE(KeyCode.ESCAPE),
    CTRL(KeyCode.CONTROL), SHIFT(KeyCode.SHIFT),
    ALT(KeyCode.ALT), ALT_GR(KeyCode.ALT_GRAPH), TAB(KeyCode.TAB);

    private final KeyCode jfxKey;

    Key(KeyCode key) {
        jfxKey = key;
    }

    public KeyCode getJFXInput() {
        return jfxKey;
    }
}
