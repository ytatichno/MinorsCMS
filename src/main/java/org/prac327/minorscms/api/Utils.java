package org.prac327.minorscms.api;

import org.prac327.minorscms.models.Schedule;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytati
 * on 26.05.2024.
 */
public class Utils {
    public static final String MONDAY = "Понедельник";
    public static final String TUESDAY = "Вторник";
    public static final String WEDNESDAY = "Среда";
    public static final String THURSDAY = "Четверг";
    public static final String FRIDAY = "Пятница";
    public static final String SATURDAY = "Суббота";
    public static final String SUNDAY = "Воскресенье";
    public static void splitSchedulesToModel(List<Schedule> schedules, Model model){
        schedules.sort((s1,s2)->s1.getStart_time().compareTo(s2.getStart_time()));
//        Map<String, List<Schedule>> mapped = new HashMap<>(); 
        model.addAttribute(MONDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(MONDAY)).toList());
        model.addAttribute(TUESDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(TUESDAY)).toList());
        model.addAttribute(WEDNESDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(WEDNESDAY)).toList());
        model.addAttribute(THURSDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(THURSDAY)).toList());
        model.addAttribute(FRIDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(FRIDAY)).toList());
        model.addAttribute(SATURDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(SATURDAY)).toList());
        model.addAttribute(SUNDAY, schedules.stream().filter(s->s.getDayOfWeek().equals(SUNDAY)).toList());
        
        
    }
}
