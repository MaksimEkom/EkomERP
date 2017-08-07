package com.ekom.ekomerp.service;
import com.ekom.ekomerp.entity.DoorOrder;

public interface GaugingService {
    String NAME = "ekomerp_GaugingService";
    public void createGaugingFromDoorOrder(DoorOrder doorOrder);
}