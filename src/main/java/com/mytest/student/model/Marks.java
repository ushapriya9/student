package com.mytest.student.model;

import java.io.Serializable;
import java.math.BigDecimal;

public final class Marks implements Serializable {

    private final Subject subject;
    private final BigDecimal obtained;
    private final BigDecimal outOf;

    public Marks(Subject subject, BigDecimal obtained, BigDecimal outOf) {
        this.subject = subject;
        this.obtained = obtained;
        this.outOf = outOf;
    }

    public BigDecimal getOutOf() {
        return outOf;
    }

    public BigDecimal getObtained() {
        return obtained;
    }

    public Subject getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Marks{" +
                "subject='" + subject + '\'' +
                ", obtained=" + obtained +
                ", outOf=" + outOf +
                '}';
    }
}
