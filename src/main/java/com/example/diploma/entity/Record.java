package com.example.diploma.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "records")
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "master_id")
    private Master master;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "recording_date")
    private LocalDateTime recordingDate;
    @Column(name = "free")
    private boolean free;
    @Column(name = "price")
    private BigDecimal price;

    public Record() {
    }

    public Record(int id, Master master, LocalDateTime recordingDate) {
        this.id = id;
        this.master = master;
        this.recordingDate = recordingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Master getMaster() {
        return master;
    }

    public void setMaster(Master master) {
        this.master = master;
    }

    public LocalDateTime getRecordingDate() {
        return recordingDate;
    }

    public void setRecordingDate(LocalDateTime recordingDate) {
        this.recordingDate = recordingDate;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
