package io.github.tuyendev.msv.common.annotation;

@FunctionalInterface
public interface Executor<T> {

	T run();
}
