package com.medkha.lol_notes.repositories.impl;

import com.medkha.lol_notes.dto.GameFinishedDTO;
import com.medkha.lol_notes.repositories.MatchHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

@Repository
public class MatchHistoryCsvRepository implements MatchHistoryRepository {
    private static final Logger log = LoggerFactory.getLogger(MatchHistoryCsvRepository.class);
    @Override
    public void exportMatchHistory(Collection<GameFinishedDTO> games) {
        try(PrintWriter pw = new PrintWriter("lol.csv", "UTF-8")){
            pw.println(GameFinishedDTO.toCsvFormatHeader());
            games.stream().flatMap(g -> g.toCsvFormat().stream()).forEach(pw::println);
        } catch (FileNotFoundException e) {
            log.error("MatchHistoryCsvRepository::exportMatchHistory : Couldn't find the file to export to.");
        } catch (UnsupportedEncodingException e) {
            log.error("MatchHistoryCsvRepository::exportMatchHistory : Unsupported Encoding, exception message -> " + e.getMessage());
        }

    }

}
