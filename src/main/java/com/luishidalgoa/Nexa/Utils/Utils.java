package com.luishidalgoa.Nexa.Utils;


import com.luishidalgoa.Nexa.Model.DTO.PublicationDTO;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Metodo que devolvera la diferencia en segundos, horas, o fecha respecto a la actual
     * @param timestamp
     * @return
     */
    public static String calculateDifference(Timestamp timestamp) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        long difference = now.getTime() - timestamp.getTime();
        Duration duration = Duration.ofMillis(difference);
        if (duration.compareTo(Duration.ofHours(1)) < 0) {
            // If the duration is less than 1 hour, return in minutes or seconds
            long seconds = duration.getSeconds();
            if (seconds < 60) {
                return seconds + "s";
            } else {
                long minutes = seconds / 60;
                long remainingSeconds = seconds % 60;
                return minutes + "min";
            }
        } else if (duration.compareTo(Duration.ofDays(1)) < 0) {
            // If the duration is less than 1 day, return in hours
            long hours = duration.toHours();
            return hours + "h";
        } else {
            // If the duration is greater than or equal to 1 day, return the full date
            LocalDateTime date = timestamp.toLocalDateTime();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return date.format(format);
        }
    }

    /**
     * En este metodo hacemos uso de Stream con el objetivo de ordenar la coleccion de Publicaciones por hora
     * @param publications
     * @return
     */
    public static Set<PublicationDTO> orderByTime(Set<PublicationDTO> publications) {
        if(publications!=null){
            Set<PublicationDTO> sortedPublications = publications.stream()
                    .sorted(Comparator.comparing(p -> p.getPublication().getPublication_date(), Comparator.reverseOrder()))
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            return sortedPublications;
        }
        return null;
    }

}
