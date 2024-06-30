package codesquad.team05.domain.jwt.constants;

public abstract class JwtConstants {

    public static final String SECRET_KEY = "This-Is-Server-SecretKey-for-air-dnb-project-in-2024-ExtraCharactersToMakeItLongEnough";
    public static final long ACCESS_EXP_TIME = 60000 * 30;   // 30 분 설정
    public static final long REFRESH_EXP_TIME = 1209600;  // 2주로 설정

    public static final String JWT_HEADER = "Authorization";
    public static final String JWT_TYPE = "Bearer ";

    public static final String ACCESS = "AccessToken";
    public static final String REFRESH = "RefreshToken";

}
