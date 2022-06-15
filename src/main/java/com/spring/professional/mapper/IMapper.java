package com.spring.professional.mapper;

public interface IMapper <I, O>{
    public O map(I in);
}
