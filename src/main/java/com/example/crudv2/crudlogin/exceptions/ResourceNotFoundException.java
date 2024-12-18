package com.example.crudv2.crudlogin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String nombreDelRescurso;
    private String nombreDelCampo;
    private long valorDelCampo;

    public ResourceNotFoundException(String nombreDelRescurso, String nombreDelCampo, long valorDelCampo) {
        super(String.format("%s no encontrado con %s: '%s'", nombreDelRescurso, nombreDelCampo, valorDelCampo));
        this.nombreDelRescurso = nombreDelRescurso;
        this.nombreDelCampo = nombreDelCampo;
        this.valorDelCampo = valorDelCampo;
    }

    public String getNombreDelRescurso() {
        return nombreDelRescurso;
    }

    public void setNombreDelRescurso(String nombreDelRescurso) {
        this.nombreDelRescurso = nombreDelRescurso;
    }

    public String getNombreDelCampo() {
        return nombreDelCampo;
    }

    public void setNombreDelCampo(String nombreDelCampo) {
        this.nombreDelCampo = nombreDelCampo;
    }

    public long getValorDelCampo() {
        return valorDelCampo;
    }

    public void setValorDelCampo(long valorDelCampo) {
        this.valorDelCampo = valorDelCampo;
    }
}
