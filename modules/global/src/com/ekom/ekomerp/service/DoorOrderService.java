package com.ekom.ekomerp.service;
import com.ekom.ekomerp.entity.Gauging;

public interface DoorOrderService {
    String NAME = "ekomerp_DoorOrderService";
    public void createDoorOrderFromGauging(Gauging gauging);
}