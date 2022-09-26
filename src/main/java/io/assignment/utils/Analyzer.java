package io.assignment.utils;

import io.assignment.model.Pair;
import io.assignment.model.Record;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Analyzer {

    private Map<Integer, List<Record>> recordMap = new HashMap<>();
    private List<Record> recordList = new ArrayList<>();

    // read the file
    public static ObservableList<Pair> analyzeFile(File file) {

        List<Pair> pairs = new ArrayList<>();

        List<Record> recordList = new ArrayList<>();
        Map<Integer, List<Record>> recordMap = new HashMap<>();

        // read the file
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
            int counter = 0;
            while ((line = br.readLine()) != null) {

                if (counter == 0) {
                    counter += 1;
                    continue;
                }

                line = line.replace(" ", "");

                String[] values = line.split(",");

                for (int i = 2; i < values.length; i++) {
                    if (values[i].equals("NULL"))
                        values[i] = LocalDate.now().toString();
                }

                Record record = new Record(
                        Integer.parseInt(values[0]),
                        //Integer.parseInt(values[1]),
                        LocalDate.parse(values[2]),
                        LocalDate.parse(values[3])
                );

                if (recordMap.containsKey(Integer.parseInt(values[1]))) {

                    List<Record> list = recordMap.get(Integer.parseInt(values[1]));
                    list.add(record);
                    recordMap.put(Integer.parseInt(values[1]), list);
                } else {
                    List<Record> list = new ArrayList();
                    list.add(record);
                    recordMap.put(Integer.parseInt(values[1]), list);
                }
                recordList.add(record);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //recordList.forEach(e -> System.out.println(e.toString()));
//        recordList.sort(Comparator.comparing(Record::getProjectId));
//        recordList.forEach(e -> System.out.println(e.toString()));

        for (Map.Entry<Integer, List<Record>> entry : recordMap.entrySet()) {

            if (entry.getValue().size() < 2)
                continue;

            entry.getValue().sort(Comparator.comparing(Record::getStartDate));

            //System.out.println("Project: " + entry.getKey() + "\tHistory: " + entry.getValue());

            LocalDate lowerBound = null;
            LocalDate upperBound = null;
            Integer maxDaysWorkedWith = 0;
            Integer longestWorkingPersonnel1 = null;
            Integer longestWorkingPersonnel2 = null;

            for (int i = 0; i < entry.getValue().size(); i++) {

                Record target1 = entry.getValue().get(i);
                Record target2 = null;

                for (int z = i; z < entry.getValue().size(); z++) {
                    target2 = entry.getValue().get(z);
                    if (target1 == target2)
                        continue;

                    if (target1.getEndDate().isBefore(target2.getStartDate())) {
                        continue;

                    } else if (target2.getEndDate().isBefore(target1.getEndDate())) {

                        lowerBound = target2.getStartDate();
                        upperBound = target2.getEndDate();
                    } else {

                        lowerBound = target2.getStartDate();
                        upperBound = target1.getEndDate();
                    }

                    //System.out.println(target1.getPersonnelId() + " <-> " + target2.getPersonnelId() + " Days Worked With: " +   lowerBound.until(upperBound, ChronoUnit.DAYS));
                    if (lowerBound.until(upperBound, ChronoUnit.DAYS) > maxDaysWorkedWith) {
                        maxDaysWorkedWith = Math.toIntExact(lowerBound.until(upperBound, ChronoUnit.DAYS));
                        longestWorkingPersonnel1 = target1.getPersonnelId();
                        longestWorkingPersonnel2 = target2.getPersonnelId();
                    }
                }


            }
            if (lowerBound == null || upperBound == null)
                //throw new Exception("There's a bug in your logic. What can it be?");
                continue;

            pairs.add(new Pair(longestWorkingPersonnel1, longestWorkingPersonnel2, entry.getKey(), maxDaysWorkedWith));
            System.out.println("The longest working pair of personnel for project " + entry.getKey() + " are " + longestWorkingPersonnel1 + " <-> " + longestWorkingPersonnel2 + " Days Worked With: " + maxDaysWorkedWith);

        }
        ObservableList<Pair> observableList = FXCollections.<Pair>observableArrayList(pairs);

        return observableList;
    }
}
