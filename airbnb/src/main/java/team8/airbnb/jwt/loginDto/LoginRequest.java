package team8.airbnb.jwt.loginDto;

public record LoginRequest(
    String username,
    String password
) {

}