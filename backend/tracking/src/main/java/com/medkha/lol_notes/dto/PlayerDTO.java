package com.medkha.lol_notes.dto;

import com.medkha.lol_notes.dto.enums.PlayerGameStatus;

public class PlayerDTO {
    public String id;
    public String summonerName;
    public String level;
    public String championName;
    public PlayerGameStatus playerGameStatus;

    public PlayerDTO() {
        playerGameStatus = PlayerGameStatus.IDLE;
    }


    @Override
    public String toString() {
        return "PlayerDTO{" +
                "summonerName='" + summonerName + '\'' +
                ", level='" + level + '\'' +
                ", championName='" + championName + '\'' +
                '}';
    }
}

//{
//        "abilities": {
//        "E": {
//        "abilityLevel": 0,
//        "displayName": "Stand Aside",
//        "id": "DravenDoubleShot",
//        "rawDescription": "GeneratedTip_Spell_DravenDoubleShot_Description",
//        "rawDisplayName": "GeneratedTip_Spell_DravenDoubleShot_DisplayName"
//        },
//        "Passive": {
//        "displayName": "League of Draven",
//        "id": "DravenPassive",
//        "rawDescription": "GeneratedTip_Passive_DravenPassive_Description",
//        "rawDisplayName": "GeneratedTip_Passive_DravenPassive_DisplayName"
//        },
//        "Q": {
//        "abilityLevel": 0,
//        "displayName": "Spinning Axe",
//        "id": "DravenSpinning",
//        "rawDescription": "GeneratedTip_Spell_DravenSpinning_Description",
//        "rawDisplayName": "GeneratedTip_Spell_DravenSpinning_DisplayName"
//        },
//        "R": {
//        "abilityLevel": 0,
//        "displayName": "Whirling Death",
//        "id": "DravenRCast",
//        "rawDescription": "GeneratedTip_Spell_DravenRCast_Description",
//        "rawDisplayName": "GeneratedTip_Spell_DravenRCast_DisplayName"
//        },
//        "W": {
//        "abilityLevel": 0,
//        "displayName": "Blood Rush",
//        "id": "DravenFury",
//        "rawDescription": "GeneratedTip_Spell_DravenFury_Description",
//        "rawDisplayName": "GeneratedTip_Spell_DravenFury_DisplayName"
//        }
//        },
//        "championStats": {
//        "abilityHaste": 0.0,
//        "abilityPower": 0.0,
//        "armor": 29.0,
//        "armorPenetrationFlat": 0.0,
//        "armorPenetrationPercent": 1.0,
//        "attackDamage": 62.0,
//        "attackRange": 550.0,
//        "attackSpeed": 0.7469000220298767,
//        "bonusArmorPenetrationPercent": 1.0,
//        "bonusMagicPenetrationPercent": 1.0,
//        "critChance": 0.0,
//        "critDamage": 175.0,
//        "currentHealth": 690.0,
//        "healShieldPower": 0.0,
//        "healthRegenRate": 0.75,
//        "lifeSteal": 0.0,
//        "magicLethality": 0.0,
//        "magicPenetrationFlat": 0.0,
//        "magicPenetrationPercent": 1.0,
//        "magicResist": 38.0,
//        "maxHealth": 690.0,
//        "moveSpeed": 330.0,
//        "omnivamp": 0.0,
//        "physicalLethality": 0.0,
//        "physicalVamp": 0.0,
//        "resourceMax": 361.0,
//        "resourceRegenRate": 1.6100000143051148,
//        "resourceType": "MANA",
//        "resourceValue": 361.0,
//        "spellVamp": 0.0,
//        "tenacity": 5.0
//        },
//        "currentGold": 500.0,
//        "fullRunes": {
//        "generalRunes": [
//        {
//        "displayName": "Summon Aery",
//        "id": 8214,
//        "rawDescription": "perk_tooltip_SummonAery",
//        "rawDisplayName": "perk_displayname_SummonAery"
//        },
//        {
//        "displayName": "Manaflow Band",
//        "id": 8226,
//        "rawDescription": "perk_tooltip_8226",
//        "rawDisplayName": "perk_displayname_8226"
//        },
//        {
//        "displayName": "Transcendence",
//        "id": 8210,
//        "rawDescription": "perk_tooltip_Transcendence",
//        "rawDisplayName": "perk_displayname_Transcendence"
//        },
//        {
//        "displayName": "Scorch",
//        "id": 8237,
//        "rawDescription": "perk_tooltip_Scorch",
//        "rawDisplayName": "perk_displayname_Scorch"
//        },
//        {
//        "displayName": "Bone Plating",
//        "id": 8473,
//        "rawDescription": "perk_tooltip_BonePlatingTooltip",
//        "rawDisplayName": "perk_displayname_BonePlating"
//        },
//        {
//        "displayName": "Revitalize",
//        "id": 8453,
//        "rawDescription": "perk_tooltip_Revitalize",
//        "rawDisplayName": "perk_displayname_Revitalize"
//        }
//        ],
//        "keystone": {
//        "displayName": "Summon Aery",
//        "id": 8214,
//        "rawDescription": "perk_tooltip_SummonAery",
//        "rawDisplayName": "perk_displayname_SummonAery"
//        },
//        "primaryRuneTree": {
//        "displayName": "Sorcery",
//        "id": 8200,
//        "rawDescription": "perkstyle_tooltip_7202",
//        "rawDisplayName": "perkstyle_displayname_7202"
//        },
//        "secondaryRuneTree": {
//        "displayName": "Resolve",
//        "id": 8400,
//        "rawDescription": "perkstyle_tooltip_7204",
//        "rawDisplayName": "perkstyle_displayname_7204"
//        },
//        "statRunes": [
//        {
//        "id": 5005,
//        "rawDescription": "perk_tooltip_StatModAttackSpeed"
//        },
//        {
//        "id": 5003,
//        "rawDescription": "perk_tooltip_StatModMagicResist"
//        },
//        {
//        "id": 5001,
//        "rawDescription": "perk_tooltip_StatModHealthScaling"
//        }
//        ]
//        },
//        "level": 1,
//        "summonerName": "atay ch3ra",
//        "teamRelativeColors": true
//        }
