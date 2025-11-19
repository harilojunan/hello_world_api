package com.example.hello.controller;

import com.example.hello.dto.ApiErrorResponse;
import com.example.hello.dto.ApiMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/hello-api")
public class ApiController {

    @GetMapping("hello-world")
    public ResponseEntity<?> helloWorld(@RequestParam(value = "name", required = false) String name) {
        if(!StringUtils.hasText(name)) {
            return invalidInput();
        }

//     trim the whitespace
        String trimmedName = name.trim();
        if(trimmedName.isEmpty()) {
            return invalidInput();
        }

//        select the first char of name
        char first = trimmedName.charAt(0);

//        validate the first latter as Ascii
        if(!isAsciiLetter(first)) {
            return invalidInput();
        }

//        Changed the first letter as UniformCharacter
        char uniformCharacter = Character.toUpperCase(first);
//        Checked the first letter as required character
        if(uniformCharacter >= 'A' && uniformCharacter <= 'M') {
            String formatted = capitalizeName(trimmedName);
            return ResponseEntity.ok(new ApiMessageResponse("Hello " + formatted));
        }else {
            return invalidInput();
        }

    }

//    Validate Name have only Ascii Letters
    private boolean isAsciiLetter(char c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

//    Error message if requirement fail
    private ResponseEntity<ApiErrorResponse> invalidInput() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse("Invalid Input"));
    }

//    Capitalize the first letter of the name and balance letter changed as small
    private String capitalizeName(String raw) {
        if (raw.isEmpty())
            return raw;
        String lowerCharacter = raw.toLowerCase();
        return Character.toUpperCase(lowerCharacter.charAt(0)) + (lowerCharacter.length() > 1 ? lowerCharacter.substring(1) : "");
    }
}
