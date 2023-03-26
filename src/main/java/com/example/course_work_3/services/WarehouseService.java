package com.example.course_work_3.services;

import com.example.course_work_3.model.Socks;

import java.util.List;

public interface WarehouseService {

    void addSetSocks(Socks socks);


    void deleteSetSocks(Socks socks);


    void editSocks(Socks socks);

    List<Socks> getSocks(String color, int value, int value2, int size);

}
