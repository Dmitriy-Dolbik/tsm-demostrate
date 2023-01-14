package ru.karod.tsm.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.util.ErrorUtil;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController
{
    private final UserService tsmUserServiceImpl;
    private final ModelMapper modelMapper;
    private final ErrorUtil tsmErrorUtilImpl;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId)
    {
        User user = tsmUserServiceImpl.getUserById(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal)
    {
        if (bindingResult.hasErrors())
        {
            String errorMsg = tsmErrorUtilImpl.createErrorMessageToClient(bindingResult);
            throw new InvalidRequestValuesException(errorMsg);
        }
        User user = tsmUserServiceImpl.updateUser(userDTO, principal);

        UserDTO userUpdated = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }


}
