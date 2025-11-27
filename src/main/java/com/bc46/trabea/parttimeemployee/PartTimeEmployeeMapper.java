package com.bc46.trabea.parttimeemployee;

import com.bc46.trabea.parttimeemployee.dto.*;
import com.bc46.trabea.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PartTimeEmployeeMapper {

    PartTimeEmployee toPartTimeEmployee(PartTimeEmployeeInsertRequest request);

    PartTimeEmployeeUpsertResponse toPartTimeRegisterResponse(User user, PartTimeEmployee partTimeEmployee);

    @Mapping(target = "workEmail", source = "user.workEmail")
    @Mapping(target = "fullName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    PartTimeEmployeeResponse toPartTimeEmployeeResponse(PartTimeEmployee entity);

    @Mapping(target = "workEmail", source = "user.workEmail")
    PartTimeEmployeeDetailResponse toPartTimeEmployeeDetailResponse(PartTimeEmployee entity);

    @Mapping(target = "workEmail", source = "user.workEmail")
    PartTimeEmployeeUpsertResponse toPartTimeEmployeeUpsertResponse(PartTimeEmployee entity);

    @Mapping(target = "workEmail", source = "user.workEmail")
    @Mapping(target = "fullName", expression = "java(entity.getFirstName() + \" \" + entity.getLastName())")
    PartTimeEmployeeSummaryResponse toPartTimeEmployeeSummaryResponse(PartTimeEmployee entity);
}
