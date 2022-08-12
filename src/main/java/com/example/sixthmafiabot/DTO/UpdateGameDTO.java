package com.example.sixthmafiabot.DTO;

import com.example.sixthmafiabot.DTO.Abstract.BaseDTO;
import com.example.sixthmafiabot.enums.GameStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateGameDTO implements BaseDTO {

    private GameStatus gameStatus;
}
