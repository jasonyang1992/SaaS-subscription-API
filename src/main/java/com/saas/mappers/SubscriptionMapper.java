package com.saas.mappers;

import com.saas.model.DTO.SubscriptionDTO;
import com.saas.model.domain.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    SubscriptionDTO toDto(Subscription subscription);
    Subscription toEntity(SubscriptionDTO subscriptionDTO);
}
