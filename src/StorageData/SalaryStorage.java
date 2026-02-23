package StorageData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import Model.SalaryRecord;

public class SalaryStorage {
    private final String salaryFileName;


    public SalaryStorage(String salaryFileName) {
        this.salaryFileName = salaryFileName;
    }


    //save salary data to file
    public void saveSalariesToFile(List<SalaryRecord> salaryRecords) {
        if (salaryRecords == null || salaryRecords.isEmpty()) {
            System.out.println("No salary data to save.");
            return;
        }
        File tempFile = new File(salaryFileName + ".tmp");
        File realFile = new File(salaryFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, false))) {
            for (SalaryRecord record : salaryRecords) {
                if (record == null) continue; // bỏ qua null
                String line = String.join("|",
                    record.getEmployeeID(),
                    String.valueOf(record.getMonth()),
                    String.valueOf(record.getYear()),
                    String.valueOf(record.getWorkingDay()),
                    String.valueOf(record.getOT()),
                    String.valueOf(record.getAbsentDay()),
                    String.valueOf(record.getTotalSalary())
                );
                writer.write(line);
                writer.newLine();
            }
            // replace file thật bằng file temp
            
       
        }catch(IOException ie) {
            System.out.println("IO Error saving salary data: " + ie.getMessage());
        } catch (Exception e) {
            System.out.println("Error saving salary data: " + e.getMessage());

        }
        try {
            Files.move(
                tempFile.toPath(),
                realFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("Salary records saved successfully.");
        } catch (IOException e) {
            System.out.println("Move failed: " + e.getMessage());
        }
    }

    public List<SalaryRecord> loadSalariesFromFile() {
        List<SalaryRecord> salaryRecords = new ArrayList<>();
        File file = new File(salaryFileName);
        if (!file.exists()) {
            System.out.println("Salary file not found: " + salaryFileName);
            return salaryRecords;
        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line;
            while ((line=reader.readLine())!=null) {
                try {
                    String[] parts = line.split("\\|");
                    if(parts.length != 7) {
                        System.out.println("Skipped invalid line (wrong number of fields): " + line);
                        continue;
                    }
                    String employeeID = parts[0];
                    int month = Integer.parseInt(parts[1]);
                    int year = Integer.parseInt(parts[2]);
                    int workingDay = Integer.parseInt(parts[3]);
                    int OT = Integer.parseInt(parts[4]);
                    int absentDay = Integer.parseInt(parts[5]);
                    double totalSalary = Double.parseDouble(parts[6]);
                    SalaryRecord record = new SalaryRecord(employeeID, month, year, workingDay, OT, absentDay, totalSalary);
                    salaryRecords.add(record);
                } catch (NumberFormatException nfe) {
                    System.out.println("Skipped invalid line (number format error): " + line);
                } catch (Exception e) {
                    System.out.println("Skipped invalid line: " + line);

                }
            }
        } catch (IOException ie ) {
            System.out.println("Error reading salary file(io exception): " + ie.getMessage());
        }catch (Exception e) {
            System.out.println("Error loading salary data(unknown error): " + e.getMessage());
        }


        
        return salaryRecords;
    }
}
