/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package spotfifai.models;

/**
 *
 * @author admin
 */
public enum Role {
    GENERAL(0),
    ADMIN(1);

    private final int code;

    Role(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Role fromCode(int code) {
        for (Role r : Role.values()) {
            if (r.code == code) {
                return r;
            }
        }
        return null; 
    }
}

