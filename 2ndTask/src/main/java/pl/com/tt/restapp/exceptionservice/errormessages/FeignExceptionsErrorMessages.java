package pl.com.tt.restapp.exceptionservice.errormessages;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeignExceptionsErrorMessages {
    ERROR_GET_MESSAGE("Your task haven't found! Please, make sure, you give proper request"),
    ERROR_POST_MESSAGE("Your Task haven't been created! Please, make sure, you give proper request"),
    ERROR_PUT_MESSAGE("Your Task haven't been updated! Please, make sure, you give proper request"),
    ERROR_DELETE_MESSAGE("Your Task haven't been deleted! Please, make sure, you give proper request");

    private final String message;
}
