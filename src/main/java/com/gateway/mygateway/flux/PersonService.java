package com.gateway.mygateway.flux;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PersonService {

    private static ConcurrentHashMap<String, Person> persons = new ConcurrentHashMap();
    static {
        for (int i = 0; i < 10; i++) {
            Person per = new Person();
            per.setAge(i+"");
            per.setName(i+"-name");
            persons.put(i+"", per);
        }
    }

    public Person getPerson() {
        return persons.get("0");
    }

    public Flux<Person> getPersons(){
        return Flux.fromIterable(persons.values());
    }
}
