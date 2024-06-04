package team10.airdnb.Member.constant;

public enum Role {

    MEMBER, ADMIN;

    public static Role from(String role) {
        return Role.valueOf(role);
    }

}
