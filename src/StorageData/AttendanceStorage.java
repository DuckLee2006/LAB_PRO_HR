package StorageData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Model.AttendanceRecord;
import Model.AttendanceStatus;

public class AttendanceStorage {
    private final String attendanceFileName;

    public AttendanceStorage(String attendanceFileName) {
        this.attendanceFileName = attendanceFileName;
    }

    public void saveAttendanceToFile(List<AttendanceRecord> records) {
        if (records == null || records.isEmpty()) {
            System.out.println("No attendance data to save.");
            return;
        }
       
        File tempFile = new File(attendanceFileName + ".tmp");
        File realFile = new File(attendanceFileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile, false))) {
            for (AttendanceRecord record : records) {
                if (record == null) continue; // bỏ qua null

                String line = String.join("|",
                    String.valueOf(record.getEmployeeID()),
                    String.valueOf(record.getDate()),
                    String.valueOf(record.getOT()),
                    String.valueOf(record.getStatus())
                );
                //id|date|OT|status
                writer.write(line);
                writer.newLine();
            }
           
        } catch (IOException e) {
            System.out.println("Error saving attendance data: " + e.getMessage());
        }

        // now the writer is closed; attempt to replace the real file with the temp file
        try {
            Files.move(
                tempFile.toPath(),
                realFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );
            System.out.println("Attendance records saved successfully.");
        } catch (IOException e) {
            System.out.println("Move failed: " + e.getMessage());
        }

    }
    //load attendance data from file
     public List<AttendanceRecord> loadAttendanceFromFile() {
        List<AttendanceRecord> records = new ArrayList<>();
        File file = new File(attendanceFileName);

        if (!file.exists()){
            System.out.println("Attendance file not found: " + attendanceFileName);
            return records;
        } 

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                try {
                    String[] parts = line.split("\\|");
                    if (parts.length != 4) {
                        System.out.println("Invalid format at line " + lineNumber);
                        continue;
                    }

                    String empID = parts[0];
                    LocalDate date = LocalDate.parse(parts[1]);
                    int OT = Integer.parseInt(parts[2]);
                    AttendanceStatus status = AttendanceStatus.valueOf(parts[3]);

                    records.add(new AttendanceRecord(empID, date, OT, status));

                } catch (Exception parseError) {
                    System.out.println("Skipped line " + lineNumber + ": " + line);
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading attendance data: " + e.getMessage());
        }

        return records;
    }

}
