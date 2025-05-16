package com.rra.template.platenumbers.mappers;

import com.rra.template.platenumbers.PlateNumber;
import com.rra.template.platenumbers.dto.PlateResponseDTO;
import com.rra.template.platenumbers.dto.RegisterPlateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlateMapper {
    @Mapping(source = "issuedDate", target = "issuedDate", dateFormat = "yyyy-MM-dd")
    PlateResponseDTO toResponse(PlateNumber plate);

    @Mapping(target = "issuedDate", source = "issueDate")
    PlateNumber toEntity(RegisterPlateRequest request);
}