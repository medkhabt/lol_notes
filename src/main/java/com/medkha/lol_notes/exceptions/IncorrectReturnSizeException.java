package com.medkha.lol_notes.exceptions;

public class IncorrectReturnSizeException extends RuntimeException{
    private final int expectedSize;
    private final int actualSize;
    public IncorrectReturnSizeException(String errorMessage, int expectedSize) {
        super(errorMessage);
        this.expectedSize = expectedSize;
        this.actualSize = -1;
    }

    public IncorrectReturnSizeException(String errorMessage, int expectedSize, int actualSize) {
        super(errorMessage);
        this.expectedSize = expectedSize;
        this.actualSize = actualSize;
    }

    public int getExpectedSize() {
        return expectedSize;
    }

    public int getActualSize() {
        return actualSize;
    }
}
