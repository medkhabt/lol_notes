package com.medkha.lol_notes.dto.factories.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.dto.factories.DeathFilterOptionFactory;
import com.medkha.lol_notes.mapper.MapperService;

@Component
public class DefaultDeathFilterFactory implements DeathFilterOptionFactory {

    private final MapperService mapperService;

    public DefaultDeathFilterFactory(MapperService mapperService){
        this.mapperService = mapperService;
    }

    @Override
    public DeathFilterOption createDeathFilterOptionByParamAndItsValue(String param, String value) {
        try{
            Function<String, DeathFilterOption> constructorFunction = getImplOfDeathFilterOptionWithCorrespondingParam().get(param);
            return constructorFunction.apply(value);
        }catch (NullPointerException err){
            throw new IllegalArgumentException("Can't create DeathFilterOptionByParam " + param + ".", err);
        }
    }

    private Map<String, Function<String, DeathFilterOption>> getImplOfDeathFilterOptionWithCorrespondingParam() {
        Map<String, Function<String, DeathFilterOption>>  mapOfParamsAndImpl = new HashMap<>();
        Reflections reflections = new Reflections("com.medkha.lol_notes");
        Set<Class<? extends DeathFilterOption>> classes = reflections.getSubTypesOf(DeathFilterOption.class);
        classes.stream().forEach(c -> {
            String key = this.mapperService.mapClassDtoToParamName(c);
            mapOfParamsAndImpl.put(key, (s) -> {
                try {
                    return c.getConstructor(String.class).newInstance(s);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                // TODO : exception handling if necessary or at least log info here.
                return null;
            });
        });
        return mapOfParamsAndImpl;
    }
}
