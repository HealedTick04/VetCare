

package VetCare.Utils;

import java.time.LocalDateTime;

import VetCare.Entities.User;

/**
 *
 * @author dary_
 */
public record  RecordAction (User user, LocalDateTime RecordTime, String actionRecord){}