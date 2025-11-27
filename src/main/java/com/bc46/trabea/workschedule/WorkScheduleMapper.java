package com.bc46.trabea.workschedule;

import com.bc46.trabea.parttimeemployee.PartTimeEmployeeMapper;
import com.bc46.trabea.workschedule.dto.WorkScheduleResponse;
import com.bc46.trabea.workschedule.dto.WorkScheduleReviewResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PartTimeEmployeeMapper.class)
public interface WorkScheduleMapper {
    @Mapping(target = "workScheduleId", source = "entity.id")
    @Mapping(target = "startDate", source = "workDate")
    @Mapping(target = "partTimeEmployee", source = "partTimeEmployee")
    WorkScheduleResponse toWorkScheduleResponse(WorkSchedule entity);

    @Mapping(target = "proposedDate", source = "entity.workDate")
    @Mapping(target = "fullName", expression = "java(entity.getPartTimeEmployee().getFirstName() + \" \" + entity.getPartTimeEmployee().getLastName())")
    WorkScheduleReviewResponse toWorkScheduleReviewResponse(WorkSchedule entity);
}
