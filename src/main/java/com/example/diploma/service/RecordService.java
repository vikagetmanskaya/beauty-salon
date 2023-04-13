package com.example.diploma.service;

import com.example.diploma.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAll(String master, String date, String service);

    Record getById(int id);

    void saveRecord(Record record);

    void deleteRecord(int id);

    void updateRecordToBusy(Record record);
}
