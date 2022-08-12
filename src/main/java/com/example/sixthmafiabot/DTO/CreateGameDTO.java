package com.example.sixthmafiabot.DTO;

import com.example.sixthmafiabot.DTO.Abstract.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateGameDTO implements BaseDTO {

    @NotNull
    private Long environmentId;

    @NotNull
    private LocalDateTime registrationStartDate;
}
