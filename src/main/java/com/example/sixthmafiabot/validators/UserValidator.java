package com.example.sixthmafiabot.validators;

import com.example.sixthmafiabot.DTO.CreateUserDTO;
import com.example.sixthmafiabot.DTO.UpdateUserDTO;
import com.example.sixthmafiabot.exceptions.AlreadyExistsException;
import com.example.sixthmafiabot.exceptions.ServiceValidationError;
import com.example.sixthmafiabot.repository.UserRepository;
import com.example.sixthmafiabot.validators.Abstract.BaseValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserValidator extends BaseValidator {

    @Autowired
    UserRepository userRepository;


    public void validateAlreadyExists(CreateUserDTO user) throws AlreadyExistsException {

        boolean alreadyExists =  userRepository
                .getUserByTelegramId(user.getTelegramId()) != null;

        if(alreadyExists){

            throw new AlreadyExistsException("telegramId",
                    "User with identifier = %s already exists"
                    .formatted(user.getTelegramId())
            );
        }
    }

    public CreateUserDTO validateCreate(CreateUserDTO createUserDTO) throws ServiceValidationError {

        try{
            validateAlreadyExists(createUserDTO);
        }
        catch (AlreadyExistsException ex){
            throw new ServiceValidationError(ex.getField(), ex.getErrorMessage());

        }

        validateDTO(createUserDTO);

        return createUserDTO;
    }


    public UpdateUserDTO validateUpdate(UpdateUserDTO updateUserDTO) throws ServiceValidationError {

        validateDTO(updateUserDTO);

        return updateUserDTO;
    }
}
