package com.medkha.lol_notes.mapper.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.dto.factories.DeathFilterOptionFactory;

@Service
public class MapperServiceImpl extends MapperBaseService{

    private DeathFilterOptionFactory deathFilterOptionFactory;

    /**
     * DI by setter because of a circular dependency between DeathFilterOptionFactory and MapperService
     * `One possible solution is to edit the source code of some classes to be configured by setters rather than constructors.
     * Alternatively, avoid constructor injection and use setter injection only. In other words, although it is not recommended,
     * you can configure circular dependencies with setter injection.`
     * source: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#spring-core
     * UPDATE 04/06/2023: While this works if we run the code with mvn spring plugin (mvn spring-boot:run), running
     * it as a jar shows this circular dependency problem, so to fix it we make a lazy initializtion of the dependency
     * DeathFilterOptionFactory
     * @param deathFilterOptionFactory
     */
    @Autowired
    @Lazy
    public void setDeathFilterOptionFactory(DeathFilterOptionFactory deathFilterOptionFactory) {
        this.deathFilterOptionFactory = deathFilterOptionFactory;
    }

    @Override
    public Set<DeathFilterOption> convertFilterSearchRequestToDeathFilterOptions(FilterSearchRequest filterDeathRequest) {
        Set<DeathFilterOption> deathFilterOptions = new HashSet<>();
        Set<String> paramNames =
                this.convertInterfaceImplementationsToParamsByInterfaceAndSingleClassMapperFunction(
                    DeathFilterOption.class,
                    this::mapClassDtoToParamName);
        paramNames.stream().forEach(
                (param) -> {
                    try{
                        String value = filterDeathRequest.getParams().get(param);
                        if(value != null) {
                            DeathFilterOption deathFilterOption =
                                    this.deathFilterOptionFactory.createDeathFilterOptionByParamAndItsValue(param, value);
                            deathFilterOptions.add(deathFilterOption);
                        }
                    } catch (NullPointerException err) {
                        // TODO: Log it. Or Remove the nullpointerException catch as it is not needed.
                    }
                }
        );
        return deathFilterOptions;
    }


}


