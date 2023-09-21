package com.example.diploma.service.impl;

import com.example.diploma.entity.Cart;
import com.example.diploma.entity.Master;
import com.example.diploma.entity.Record;
import com.example.diploma.repository.CartRepository;
import com.example.diploma.repository.RecordRepository;
import com.example.diploma.service.MasterService;
import com.example.diploma.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordRepository recordRepository;
    @Autowired
    private MasterService masterService;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public List<Record> getAll(String master, String date, String service) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Record> records = recordRepository.findAll();
        if(records != null) {
            if (master == null && date == null && service == null) {
                records = records.stream().filter(record -> !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (master != null && date.isBlank() && service.isBlank()) {
                records = records.stream().filter(record -> record.getMaster().getName().equals(master)
                        && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (service != null && date.isBlank() && master.isBlank()) {
                records = records.stream().filter(record -> record.getMaster().getItem()
                                .getName().equals(service)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (date != null && master.isBlank() && service.isBlank()) {
                records = records.stream().filter(record -> record.getRecordingDate().format(formatter).
                                equals(date)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (date != null && master != null && service.isBlank()) {
                records = records.stream().filter(record -> record.getRecordingDate().format(formatter).
                                equals(date) && record.getMaster().getName().equals(master)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (service != null && master != null && date.isBlank()) {
                records = records.stream().filter(record -> record.getMaster().getItem().getName()
                                .equals(service) && record.getMaster().getName().equals(master)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (service != null && date != null && master.isBlank()) {
                records = records.stream().filter(record -> record.getRecordingDate().format(formatter).
                                equals(date) && record.getMaster().getItem().getName().equals(service)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            } else if (service != null && date != null && master != null) {
                records = records.stream().filter(record -> record.getRecordingDate().format(formatter).
                                equals(date) && record.getMaster().getItem().getName().equals(service)
                                && record.getMaster().getName().equals(master)
                                && !record.getRecordingDate().isBefore(LocalDateTime.now()))
                        .collect(Collectors.toList());
            }

        }
        return records;

        }


    @Override
    public Record getById(int id) {
        return recordRepository.findById(id).get();
    }

    @Override
    public void saveRecord(Record record) {
        if(record.getMaster()!= null){
            Master master = record.getMaster();
            if(masterService.getByName(master.getName()) == null){
                masterService.saveMaster(master);
            }
            record.setMaster(masterService.getByName(master.getName()));
            record.setFree(true);
            recordRepository.save(record);
        }

    }
    public void updateRecordToBusy(Record record) {
        if(record.getMaster()!= null){
            Master master = record.getMaster();
            if(masterService.getByName(master.getName()) == null){
                masterService.saveMaster(master);
            }
            record.setMaster(masterService.getByName(master.getName()));
            record.setFree(false);
            recordRepository.save(record);
        }

    }

    @Override
    public void deleteRecord(int id) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        List<Cart> carts = cartRepository.findAll();
        Record deletingRecord = getById(id);
        for (Cart cart : carts) {
            if (cart.getRecords().contains(deletingRecord)) {

                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(cart.getUser().getEmail());
                mailMessage.setSubject("Record delete!");
                mailMessage.setFrom("getvikcom@gmail.com");
                mailMessage.setText("Unfortunately, the record for a " +
                        deletingRecord.getMaster().getItem().getName() + " on the date " +
                        deletingRecord.getRecordingDate().format(formatter) + " to master " +
                        deletingRecord.getMaster().getName() + " has been deleted.");

                emailService.sendEmail(mailMessage);
                cart.getRecords().remove(deletingRecord);
            }
        }


        recordRepository.deleteById(id);
    }
}
