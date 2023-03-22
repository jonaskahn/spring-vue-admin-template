package io.github.tuyendev.msv.common.exception;

public class ShouldNeverOccurException extends LogicException {

    public ShouldNeverOccurException() {
        super("This exception should never be happened");
    }
}
