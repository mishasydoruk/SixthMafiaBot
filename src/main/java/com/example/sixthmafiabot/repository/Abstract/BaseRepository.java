package com.example.sixthmafiabot.repository.Abstract;

import org.modelmapper.ModelMapper;

public abstract class BaseRepository {

    protected final ModelMapper modelMapper =  new ModelMapper();
}
