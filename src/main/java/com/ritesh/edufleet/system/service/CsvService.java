package com.ritesh.edufleet.system.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.ritesh.edufleet.department.entity.Department;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CsvService {
    public List<Department> parseCsv(InputStream inputStream) throws CsvValidationException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        CSVReader csvReader = new CSVReader(reader);
        List<Department> departments = new ArrayList<>();
        String[] line;
        csvReader.skip(1);
        while ((line = csvReader.readNext()) != null) {
            log.info("lineeee" + line[0]);
            Department department = new Department();
            department.setName(line[0]);
            departments.add(department);
        }
        return departments;
    }
}
