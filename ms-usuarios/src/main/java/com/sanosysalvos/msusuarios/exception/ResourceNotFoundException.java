// ResourceNotFoundException.java
package com.sanosysalvos.msusuarios.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}