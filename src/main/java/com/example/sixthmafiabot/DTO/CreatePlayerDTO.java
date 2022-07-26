package com.example.sixthmafiabot.DTO;

import com.example.sixthmafiabot.DTO.Abstract.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreatePlayerDTO implements BaseDTO {

    @NotNull
    private Long telegramId;

    @NotNull
    private Long environmentId;

}
