package com.example.sixthmafiabot.DTO;

import com.example.sixthmafiabot.DTO.Abstract.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlayerDTO implements BaseDTO {

    private boolean isAlive;
}
