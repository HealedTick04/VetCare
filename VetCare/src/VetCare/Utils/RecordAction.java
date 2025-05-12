/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package VetCare.Utils;
import VetCare.Entities.User;
import java.time.LocalDateTime;
/**
 *
 * @author dary_
 */
public record  RecordAction (User user, LocalDateTime RecordTime, String actionRecord){}