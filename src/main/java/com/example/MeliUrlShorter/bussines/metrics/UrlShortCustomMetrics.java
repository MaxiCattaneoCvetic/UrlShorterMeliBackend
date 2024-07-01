package com.example.MeliUrlShorter.bussines.metrics;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class UrlShortCustomMetrics extends AppenderBase<ILoggingEvent> {
    /*
            Descripcion de la clase -> Configuramos las metricas para medir con actuator.
            MasterRegistry nos permite registrar las metricas custom y el post lo que hace es postearlas en nuestra interfaz.

    */
    private static final Logger logger = LoggerFactory.getLogger(UrlShortCustomMetrics.class);
    private final MeterRegistry meterRegistry;

    //Aca añadimos las variables para contar las request y las respuestas
    private Counter postAndPutRequestCounter;
    private Counter getRequestCounter;
    private Counter saveCounterDatabase;
    private Counter getCounterDatabase;
    private Counter logCacheCounter;

    @Autowired
    public UrlShortCustomMetrics(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @PostConstruct
    public void initMetrics() {
        postAndPutRequestCounter = Counter.builder("custom.post.and.put.request.count")
                .description("Total de peticiones (PUT) y (POST) realizadas")
                .register(meterRegistry);

        getRequestCounter = Counter.builder("custom.get.request.count")
                .description("Total de peticiones de obtención (GET) realizadas")
                .register(meterRegistry);

        saveCounterDatabase = Counter.builder("custom.crud.save.count")
                .description("Cantidad de operaciones de persistencia en la base de datos")
                .register(meterRegistry);

        getCounterDatabase = Counter.builder("custom.crud.find.count")
                .description("Cantidad de operaciones de búsqueda en la base de datos")
                .register(meterRegistry);

        logCacheCounter = Counter.builder("cache.log.entries.found")
                .description("Cantidad de veces que se encontraron entradas en el cache")
                .register(meterRegistry);

        meterRegistry.gauge("custom.uptime", Duration.ofMillis(System.currentTimeMillis()), Duration::toSeconds);
    }

    public void postAndPutCount() {
        postAndPutRequestCounter.increment();
    }

    public void getRequestCount() {
        getRequestCounter.increment();
    }

    public void incrementCrudSaveCount() {
        saveCounterDatabase.increment();
    }

    public void incrementCrudFindCount() {
        getCounterDatabase.increment();
    }


    // Tengo que implementar logica para contar las entradas en el cache y medirlas para saber cuantas veces utiliza el cache dividido a la cantidad de peticiones


    @Override
    protected void append(ILoggingEvent eventObject) {
//        String message = eventObject.getFormattedMessage();
//        logger.debug("Log received: {}", message);
//
//        if (message.contains("Cache entry for key") || message.contains("found in cache(s)")) {
//            logCacheCounter.increment();
//            logger.debug("Cache log entry found");
//        }
    }
}
