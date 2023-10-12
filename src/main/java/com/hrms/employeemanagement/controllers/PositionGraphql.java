package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class PositionGraphql {
    PositionService positionService;

    @Autowired
    public PositionGraphql(PositionService positionService) {
        this.positionService = positionService;
    }

    @QueryMapping(name = "positions")
    public List<Position> findAllPositions() {
        return positionService.findAll(Specification.allOf());
    }
}
