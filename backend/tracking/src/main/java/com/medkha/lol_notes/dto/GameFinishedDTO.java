package com.medkha.lol_notes.dto;

import java.util.List;
import java.util.stream.Collectors;

public class GameFinishedDTO {
    public MetaDataGameDTO metadata;
    public GameFinishedInfoDTO info;
    public List<String> toCsvFormat() {
        String gameGeneralAttributes =
                this.metadata.dataVersion + ","
                        + this.metadata.matchId + ","
                        + this.info.gameCreation + ","
                        + this.info.gameDuration + ","
                        + this.info.gameEndTimestamp + ","
                        + this.info.gameId + ","
                        + this.info.gameMode + ","
                        + this.info.gameName + ","
                        + this.info.gameStartTimestamp + ","
                        + this.info.gameType + ","
                        + this.info.gameVersion + ","
                        + this.info.mapId + ",";
        return info.participants.stream().map(p ->
                gameGeneralAttributes +
                    p.allInPings + "," +
                    p.assistMePings + "," +
                    p.assists + "," +
                    p.baitPings + "," +
                    p.baronKills + "," +
                    p.basicPings + "," +
                    p.bountyLevel + "," +
                    p.champExperience + "," +
                    p.champLevel + "," +
                    p.championId + "," +
                    p.championName + "," +
                    p.commandPings + "," +
                    p.consumablesPurchased + "," +
                    p.damageDealtToBuildings + "," +
                    p.damageDealtToObjectives + "," +
                    p.damageDealtToTurrets + "," +
                    p.damageSelfMitigated + "," +
                    p.dangerPings + "," +
                    p.deaths + "," +
                    p.detectorWardsPlaced + "," +
                    p.doubleKills + "," +
                    p.dragonKills + "," +
                    p.enemyMissingPings + "," +
                    p.enemyVisionPings + "," +
                    p.firstBloodAssist + "," +
                    p.firstBloodKill + "," +
                    p.firstTowerAssist + "," +
                    p.firstTowerKill + "," +
                    p.gameEndedInEarlySurrender + "," +
                    p.gameEndedInSurrender + "," +
                    p.getBackPings + "," +
                    p.goldEarned + "," +
                    p.goldSpent + "," +
                    p.holdPings + "," +
                    p.individualPosition + "," +
                    p.inhibitorKills + "," +
                    p.inhibitorTakedowns + "," +
                    p.inhibitorsLost + "," +
                    p.item0 + "," +
                    p.item1 + "," +
                    p.item2 + "," +
                    p.item3 + "," +
                    p.item4 + "," +
                    p.item5 + "," +
                    p.item6 + "," +
                    p.itemsPurchased + "," +
                    p.killingSprees + "," +
                    p.kills + "," +
                    p.lane + "," +
                    p.largestCriticalStrike + "," +
                    p.largestKillingSpree + "," +
                    p.largestMultiKill + "," +
                    p.longestTimeSpentLiving + "," +
                    p.magicDamageDealt + "," +
                    p.magicDamageDealtToChampions + "," +
                    p.magicDamageTaken + "," +
                    p.needVisionPings + "," +
                    p.neutralMinionsKilled + "," +
                    p.nexusKills + "," +
                    p.nexusLost + "," +
                    p.nexusTakedowns + "," +
                    p.objectivesStolen + "," +
                    p.objectivesStolenAssists + "," +
                    p.onMyWayPings + "," +
                    p.participantId + "," +
                    p.pentaKills + "," +
                    p.physicalDamageDealt + "," +
                    p.physicalDamageDealtToChampions + "," +
                    p.physicalDamageTaken + "," +
                    p.pushPings + "," +
                    p.puuid + "," +
                    p.role + "," +
                    p.sightWardsBoughtInGame + "," +
                    p.spell1Casts + "," +
                    p.spell2Casts + "," +
                    p.spell3Casts + "," +
                    p.spell4Casts + "," +
                    p.summoner1Casts + "," +
                    p.summoner1Id + "," +
                    p.summoner2Casts + "," +
                    p.summoner2Id + "," +
                    p.summonerId + "," +
                    p.summonerLevel + "," +
                    p.summonerName + "," +
                    p.teamEarlySurrendered + "," +
                    p.teamId + "," +
                    p.teamPosition + "," +
                    p.timeCCingOthers + "," +
                    p.timePlayed + "," +
                    p.totalAllyJungleMinionsKilled + "," +
                    p.totalDamageDealt + "," +
                    p.totalDamageDealtToChampions + "," +
                    p.totalDamageShieldedOnTeammates + "," +
                    p.totalDamageTaken + "," +
                    p.totalEnemyJungleMinionsKilled + "," +
                    p.totalHeal + "," +
                    p.totalHealsOnTeammates + "," +
                    p.totalMinionsKilled + "," +
                    p.totalTimeCCDealt + "," +
                    p.totalTimeSpentDead + "," +
                    p.totalUnitsHealed + "," +
                    p.tripleKills + "," +
                    p.trueDamageDealt + "," +
                    p.trueDamageDealtToChampions + "," +
                    p.trueDamageTaken + "," +
                    p.turretKills + "," +
                    p.turretTakedowns + "," +
                    p.turretsLost + "," +
                    p.unrealKills + "," +
                    p.visionClearedPings + "," +
                    p.visionScore + "," +
                    p.visionWardsBoughtInGame + "," +
                    p.wardsKilled + "," +
                    p.wardsPlaced + "," +
                    p.win
        ).collect(Collectors.toList());
    }
    //TODO: too much writing, Use Reflexion next Time!!!
    public static String toCsvFormatHeader(){
        return
                "metadata_dataVersion,metadata_matchId,info_gameCreation,info_gameDuration,info_gameEndTimestamp,"+
                        "info_gameId,info_gameMode,info_gameName,info_gameStartTimestamp,info_gameType,info_gameVersion,info_mapId,"+
                        "player_allInPings,player_assistMePings,player_assists,player_baitPings,player_baronKills,player_bountyLevel,player_champExperience,"+
                        "player_champLevel,player_championId,player_championName,player_commandPings,player_consumablesPurchased,player_damageDealtToBuildings,"+
                        "player_damageDealtToObjectives,player_damageDealtToTurrets,player_damageDealtToTurrets,player_damageSelfMitigated,"+
                        "player_dangerPings,player_deaths,player_detectorWardsPlaced,player_doubleKills,player_dragonKills,"+
                        "player_enemyMissingPings,player_enemyVisionPings,player_firstBloodAssist,player_firstBloodKill,"+
                        "player_firstTowerAssist,player_firstTowerKill,player_gameEndedInEarlySurrender,player_gameEndedInSurrender,"+
                        "player_getBackPings,player_goldEarned,player_goldSpent,player_holdPings,player_individualPosition,"+
                        "player_inhibitorKills,player_inhibitorTakedowns,player_inhibitorsLost,player_item0,"+
                        "player_item1,player_item2,player_item3,player_item4,player_item5,player_item6,player_itemsPurchased,"+
                        "player_killingSprees,player_kills,player_lane,player_largestCriticalStrike,"+
                        "player_largestKillingSpree,player_largestMultiKill,player_longestTimeSpentLiving,"+
                        "player_magicDamageDealt,player_magicDamageDealtToChampions,player_magicDamageTaken,"+
                        "player_needVisionPings,player_neutralMinionsKilled,player_nexusKills,player_nexusLost,"+
                        "player_nexusTakedowns,player_objectivesStolen,player_objectivesStolenAssists,player_onMyWayPings,"+
                        "player_participantId,player_pentaKills,player_physicalDamageDealt,player_physicalDamageDealtToChampions,"+
                        "player_physicalDamageTaken,player_pushPings,player_puuid,player_role,player_sightWardsBoughtInGame,"+
                        "player_spell1Casts,player_spell2Casts,player_spell3Casts,player_spell4Casts,player_summoner1Casts,"+
                        "player_summoner1Id,player_summoner2Casts,player_summoner2Id,player_summonerId,player_summonerLevel,"+
                        "player_summonerName,player_teamEarlySurrendered,player_teamId,player_teamPosition,player_timeCCingOthers,"+
                        "player_timePlayed,player_totalAllyJungleMinionsKilled,player_totalDamageDealt,"+
                        "player_totalDamageDealtToChampions,player_totalDamageShieldedOnTeammates,"+
                        "player_totalDamageTaken,player_totalEnemyJungleMinionsKilled,player_totalHeal,"+
                        "player_totalHealsOnTeammates,player_totalMinionsKilled,player_totalTimeCCDealt,"+
                        "player_totalTimeSpentDead,player_totalUnitsHealed,player_tripleKills,"+
                        "player_trueDamageDealt,player_trueDamageDealtToChampions,player_trueDamageTaken,"+
                        "player_turretKills,player_turretTakedowns,player_turretsLost,player_unrealKills,"+
                        "player_visionClearedPings,player_visionScore,player_visionWardsBoughtInGame,"+
                        "player_wardsKilled,player_wardsPlaced,win";


    }

}
