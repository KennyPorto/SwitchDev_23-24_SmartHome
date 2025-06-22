package SmartHome.domain.device;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
   private final String _time;
   private final String _message;

   protected Log(LocalDateTime time, String message) {
      if (message == null || message.trim().isEmpty())
         throw new IllegalArgumentException("Message cannot be null or empty");
      if (time == null)
         throw new IllegalArgumentException("Time cannot be null");

      DateTimeFormatter logTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
      this._time = time.format(logTime);
      this._message = message;
   }

   public String getLog() {
      return _time + " - " + _message + "\n";
   }
}
