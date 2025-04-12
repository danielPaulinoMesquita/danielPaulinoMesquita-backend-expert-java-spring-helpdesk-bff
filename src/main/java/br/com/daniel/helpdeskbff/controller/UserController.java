package br.com.daniel.helpdeskbff.controller;

import br.com.userservice.commonslib.model.exceptions.StandardError;
import br.com.userservice.commonslib.model.requests.CreateUserRequest;
import br.com.userservice.commonslib.model.requests.UpdateUserRequest;
import br.com.userservice.commonslib.model.responses.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Tag(name = "UserController", description = "Controller responsible for user descriptions")
@RequestMapping("/api/users")
public interface UserController {

    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "user not found",
                         content = @Content( mediaType = APPLICATION_JSON_VALUE,
                         schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server Error",
                         content = @Content( mediaType = APPLICATION_JSON_VALUE,
                         schema = @Schema(implementation = StandardError.class)))
    })
    @GetMapping("/{id}")
    ResponseEntity<UserResponse> findById(
            @Parameter(description = "User id", required = true, example = "66e750f173ae7632a1e46193")
            @PathVariable String id);


    @Operation(summary = "Save new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content( mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server Error",
                    content = @Content( mediaType =  APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
    })
    @PostMapping
    ResponseEntity<Void> save(
            @Valid @RequestBody final CreateUserRequest createUserRequest
    );

    @Operation(summary = "Find all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    array = @ArraySchema(
                    schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server Error",
                    content = @Content( mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class)))
    })
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    ResponseEntity<List<UserResponse>> findAll();
    // If you use hasAnyRole, just has role in the credentials will be able,
    // if was a hasAuthority, you need to pass the exactly name of AUTHORITY

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UserResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = StandardError.class))),
    })
    ResponseEntity<UserResponse> update(
            @Parameter(description = "User id", required = true, example = "6137f7d4b0b1c65d18a3a5a2")
            @PathVariable(name = "id") final String id,
            @Valid @RequestBody final UpdateUserRequest updateUserRequest
    );


}
