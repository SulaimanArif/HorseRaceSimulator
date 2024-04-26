import java.util.*;

public interface ControllerInterface {
    public List<Object> horseData = new ArrayList<>();

    void setHorseData(String name, String assetPath);
    List<Object> getHorseData();

    String getTrackOrHorse();
    void setTrackOrHorse(String trackHorse);

    void setTrackData(String track);
    String getTrackData();
}