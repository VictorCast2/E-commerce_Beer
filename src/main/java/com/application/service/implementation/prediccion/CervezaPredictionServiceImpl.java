package com.application.service.implementation.prediccion;

import com.application.persistence.entity.prediccion.CervezaPrediction;
import com.application.persistence.repository.CervezaPredictionRepository;
import com.application.service.interfaces.prediccion.CervezaPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

@Service
@RequiredArgsConstructor
public class CervezaPredictionServiceImpl implements CervezaPredictionService {

    private final CervezaPredictionRepository repository;

    @Override
    public String predecirDemanda(CervezaPrediction datosEntrada) throws Exception {
        // Cargar dataset base (estructura ARFF)
        ConverterUtils.DataSource source = new ConverterUtils.DataSource("src/main/resources/Costa De Oro Imports.arff");
        Instances dataset = source.getDataSet();

        // Establecer la clase a predecir
        dataset.setClassIndex(dataset.numAttributes() - 1);

        // Crear nueva instancia (según tus atributos)
        Instance instance = (Instance) dataset.get(0).copy();
        instance.setValue(0, datosEntrada.getTipoCerveza());
        instance.setValue(1, datosEntrada.getPrecio());
        instance.setValue(2, datosEntrada.getGradoAlcohol());
        instance.setValue(3, datosEntrada.getPaisOrigen());
        instance.setValue(4, datosEntrada.getMarca());
        instance.setValue(5, datosEntrada.getVolumen());
        instance.setValue(6, datosEntrada.getStock());

        // Cargar modelo entrenado
        ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("src/main/resources/Modelo con J48 - Training set (C=0.25, M=2).model"));
        Classifier modelo = (Classifier) ois.readObject();
        ois.close();

        // Clasificar instancia
        double pred = modelo.classifyInstance(instance);
        String clasePredicha = dataset.classAttribute().value((int) pred);

        // Guardar resultado
        datosEntrada.setCategoriaPredicha(clasePredicha);
        repository.save(datosEntrada);

        return clasePredicha;
    }

}