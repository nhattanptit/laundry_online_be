package com.laundy.laundrybackend.controller.impl;

import com.laundy.laundrybackend.controller.api.ServiceInterface;
import com.laundy.laundrybackend.models.Service;
import com.laundy.laundrybackend.models.response.GeneralResponse;
import com.laundy.laundrybackend.models.response.ResponseFactory;
import com.laundy.laundrybackend.repository.ServicePriceRepository;
import com.laundy.laundrybackend.service.ServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
public class ServiceController implements ServiceInterface {
    private final ServicesService servicesService;

    @Autowired
    public ServiceController(ServicesService servicesService) {
        this.servicesService = servicesService;
    }

    @Override
    public GeneralResponse<?> getAllService() {
        List<Service> services = servicesService.getAllService();;
        return ResponseFactory.sucessRepsonse(services);
    }
}
