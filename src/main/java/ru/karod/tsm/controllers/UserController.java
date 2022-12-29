package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karod.tsm.dto.UserDTO;
import ru.karod.tsm.exceptions.InvalidRequestValuesException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.UUID;

import static ru.karod.tsm.util.ErrorUtil.createErrorMessageToClient;

@RestController
@RequestMapping("/user")
@CrossOrigin
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("userId") String userId){
        User user = userService.getUserById(userId);
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }
    @PostMapping ("/update")
    public ResponseEntity<Object> updateUser(@Valid @RequestBody UserDTO userDTO,
                                             BindingResult bindingResult,
                                             Principal principal){
        if (bindingResult.hasErrors()){
            String errorMsg = createErrorMessageToClient(bindingResult);
            throw new InvalidRequestValuesException(errorMsg);
        }
        User user = userService.updateUser(userDTO, principal);

        UserDTO userUpdated = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(userUpdated, HttpStatus.OK);
    }


}
