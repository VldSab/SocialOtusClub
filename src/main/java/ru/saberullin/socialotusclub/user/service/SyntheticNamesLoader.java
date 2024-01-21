package ru.saberullin.socialotusclub.user.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

@Component
public class SyntheticNamesLoader implements Iterator<String[]>{

    private static final String PATH = "src/main/resources/data/people.csv";
    private static Iterator<String> namesIterator;

    @SneakyThrows
    public SyntheticNamesLoader() {
        BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(PATH)));
        namesIterator = file.lines().iterator();
    }

    @Override
    public boolean hasNext() {
        return namesIterator.hasNext();
    }

    @Override
    public String[] next() {
        String currentLine = namesIterator.next();
        return currentLine.split(",")[0].split(" ");
    }
}
