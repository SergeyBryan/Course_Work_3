package com.example.course_work_3.services.impl;

import com.example.course_work_3.model.Socks;
import com.example.course_work_3.services.WarehouseService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WarehouseServiceImpl implements WarehouseService {


    Set<Socks> socksIntegerSet = new HashSet<>();


    @Override
    public void addSetSocks(Socks socks) {
        if (socksIntegerSet.contains(socks)) {
            for (Socks newSocks : socksIntegerSet) {
                if (newSocks.equals(socks)) {
                    if (newSocks.getAmount() + socks.getAmount() < 0) {
                        throw new IllegalArgumentException("Проверьте правильность приемки, количество на складе не может быть отрицательным");
                    } else {
                        newSocks.setAmount(newSocks.getAmount() + socks.getAmount());
                    }
                    System.out.println("Добавлено " + socks.getAmount() + ", и стало " + newSocks.getAmount());
                }
            }
        } else {
            socksIntegerSet.add(socks);
            System.out.println("Модель " + socks + " добавлена");
        }
    }


    @Override
    public void deleteSetSocks(Socks socks) {
        if (socksIntegerSet.contains(socks)) {
            for (Socks newSocks : socksIntegerSet) {
                if (newSocks.equals(socks)) {
                    if ((newSocks.getAmount() - socks.getAmount()) < 0) {
                        throw new IllegalArgumentException("Нельзя списать носков больше, чем есть на складе");
                    } else {
                        newSocks.setAmount(newSocks.getAmount() - socks.getAmount());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Такой модели не существует");
        }
    }

    @Override
    public void editSocks(Socks socks) {
        if (socksIntegerSet.contains(socks)) {
            for (Socks newSocks : socksIntegerSet) {
                if (newSocks.equals(socks)) {
                    if (newSocks.getAmount() - socks.getAmount() < 0) {
                        throw new IllegalArgumentException("Нельзя отгрузить носков больше, чем есть на складе");
                    } else {
                        newSocks.setAmount(newSocks.getAmount() - socks.getAmount());
                        System.out.println("Поставка в количестве " + socks.getAmount() + " была отправлена и составляет на складе " + newSocks.getAmount());
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Такой модели не существует");
        }
    }


    @Override
    public List<Socks> getSocks(String color, int value, int value2, int size) {
        List<Socks> list = new ArrayList<>();
        for (Socks newSocks : socksIntegerSet) {
            if (size == 0) {
                if ((Objects.equals(color, newSocks.getColor().toString()) & newSocks.getCotton() >= value & newSocks.getCotton() <= value2)) {
                    System.out.println(newSocks);
                    list.add(newSocks);
                }
            } else {
                if ((Objects.equals(color, newSocks.getColor().toString()) & newSocks.getCotton() >= value & newSocks.getCotton() <= value2) & newSocks.getSize().getValue() == size) {
                    System.out.println(newSocks);
                    list.add(newSocks);
                }
            }
        }
        return list;
    }
}
