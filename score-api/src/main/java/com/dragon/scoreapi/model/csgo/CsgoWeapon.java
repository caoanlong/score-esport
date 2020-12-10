package com.dragon.scoreapi.model.csgo;

import lombok.Data;

/**
 * CS:GO 武器
 */
@Data
public class CsgoWeapon {
    private Integer weaponId;
    private String weaponName;
    private String weaponLogo;
}
