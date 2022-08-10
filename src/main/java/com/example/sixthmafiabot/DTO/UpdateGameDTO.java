package com.example.sixthmafiabot.DTO;

import com.example.sixthmafiabot.DTO.Abstract.BaseDTO;
import com.example.sixthmafiabot.enums.GameStatus;

import javax.validation.constraints.NotNull;

public class UpdateGameDTO implements BaseDTO {


    private GameStatus gameStatus;
}
