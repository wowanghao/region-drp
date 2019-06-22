package io.terminus.common.data.transfer.executors;

import io.terminus.common.data.transfer.executors.ifaces.IRegisteredTaskExecutor;
import io.terminus.common.data.transfer.spi.ifaces.IDataExporter;
import io.terminus.common.data.transfer.spi.ifaces.IDataImporter;

import io.terminus.common.data.transfer.spi.annotations.DataImporter;
import io.terminus.common.data.transfer.spi.annotations.DataExporter;

import lombok.var;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;

/**
 * @author yushuo
 */
@Component
@Configuration
public class DataTransferExecutorRegister {

    private IRegisteredTaskExecutor registeredTaskExecutor;

    @Autowired
    public DataTransferExecutorRegister(IRegisteredTaskExecutor registeredTaskExecutor) {
        this.registeredTaskExecutor = registeredTaskExecutor;
    }

    @Bean(name = "terminus-data-transfer-executer-bean-registry-post-processor")
    public BeanPostProcessor beanPostProcessorForItemService() {
        return new BeanPostProcessor() {
            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) {
                return bean;
            }

            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                var importerAnnotation = getAnnotation(bean, DataImporter.class);
                var exporterAnnotation = getAnnotation(bean, DataExporter.class);

                if(null != importerAnnotation) {
                    String importerName = importerAnnotation.name();
                    if(!(bean instanceof IDataImporter)) {
                        throw new TypeMismatchException(bean, IDataImporter.class);
                    }
                    registeredTaskExecutor.registerImporter(importerName, (IDataImporter) bean);
                }

                if(null != exporterAnnotation) {
                    String exporterName = exporterAnnotation.name();
                    if(!(bean instanceof IDataExporter)) {
                        throw new TypeMismatchException(bean, IDataExporter.class);
                    }
                    registeredTaskExecutor.registerExporter(exporterName, (IDataExporter) bean);
                }

                return bean;
            }

            private <A extends Annotation> A getAnnotation(Object object, Class<A> annotationClass) {
                Class clazz = object.getClass();
                while(clazz != null) {
                    //noinspection unchecked
                    A annotation = (A) clazz.getAnnotation(annotationClass);
                    if (null != annotation) {
                        return annotation;
                    } else {
                        clazz = clazz.getSuperclass();
                    }
                }
                return null;
            }
        };
    }

}
