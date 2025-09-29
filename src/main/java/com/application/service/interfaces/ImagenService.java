package com.application.service.interfaces;


import org.springframework.web.multipart.MultipartFile;

public interface ImagenService {

    String asignarImagen(MultipartFile imagen, String tipo);

}
