package com.application.service.interfaces.prediccion;

import com.application.persistence.entity.prediccion.CervezaPrediction;

public interface CervezaPredictionService {
    public String predecirDemanda(CervezaPrediction datosEntrada) throws Exception;
}