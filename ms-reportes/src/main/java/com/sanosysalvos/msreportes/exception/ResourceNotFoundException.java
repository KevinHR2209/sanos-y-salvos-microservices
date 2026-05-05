// ResourceNotFoundException.java
package com.sanosysalvos.msreportes.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}