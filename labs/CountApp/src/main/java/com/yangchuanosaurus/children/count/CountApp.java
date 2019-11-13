package com.yangchuanosaurus.children.count;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class CountApp implements CommandLineRunner {

    public static void main(String args[]) throws Exception {
        SpringApplication.run(CountApp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AdditionCount additionCount = CountFactory.createAdditionCount(10);
        SubtractionCount subtractionCount = CountFactory.createSubtractionCount(10);
        List<Equation> combinedEquations = new ArrayList<>();
        combinedEquations.addAll(additionCount.getEquations());
        combinedEquations.addAll(subtractionCount.getEquations());

        PdfFactory.generatePdf("count_10_addition.pdf", combinedEquations);
    }

}
